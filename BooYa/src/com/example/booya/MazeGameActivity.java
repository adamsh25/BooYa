package com.example.booya;


import java.util.EventListener;

import com.example.booya.R;
import com.example.booya.BL.GameLevel;
import com.example.booya.BL.GameLevel1;
import com.example.booya.BL.GameLevel2;
import com.example.booya.BL.GameLevel3;
import com.example.booya.BL.MazeObstacles;
import com.example.booya.BL.Monster;
import com.example.booya.UI.Views.GameLevelView;
import com.example.booya.UI.Views.MazeView;
import com.example.booya.UI.Views.MonsterView;
import com.example.booya.UI.Views.ProgressWheel;
import com.example.booya.UI.Views.StartOffsetCircleView;
import com.example.booya.video.recording.CameraHelper;

import android.os.Bundle;
import android.os.Vibrator;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.example.booya.video.recording.RecordingService;

public class MazeGameActivity extends Activity
{

	// region members

	/**
	 * Initialize members
	 */
	private MazeView m_mazeView;
	private GameLevel[] levels;

	private Monster m_monster;
	private GameLevel m_currentLevel;
	private GameLevelView gameLevelView;
	private MonsterView monsterView;
	private StartOffsetCircleView circleView;
	private ProgressWheel progressWheelView;
	private CameraHelper cameraHelper;
	private Vibrator gameVibrator;
	public static int screenWidth, screenHeight;
	private SurfaceView camSurface;
	private View dynamicView;

	// flag - true if the player has touched a wall.
	public static boolean b_playerHasTouchedWall = false;

	// flag - true if the player can move
	public static boolean b_canMove = false;

	//
	public static int n_gameLevel = 0;

	// endregion

	// region Events

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		// region initialise members

		Display display = getWindowManager().getDefaultDisplay();

		/*
		 * api 13+ Point size = new Point(); display.getSize(size); screenWidth
		 * = size.x; screenHeight = size.y;
		 */

		screenHeight = display.getHeight() - 1;
		screenWidth = display.getWidth();
		
		// Get instance of Vibrator from current Context
		gameVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		b_playerHasTouchedWall = false;
		n_gameLevel = 0;
		b_canMove = false;
		initLevels();
		m_currentLevel = new GameLevel1();
		gameLevelView = new GameLevelView(this);
		gameLevelView.setGameLevel(m_currentLevel);
		progressWheelView = new ProgressWheel(this);
		progressWheelView.spin();
		progressWheelView.setText("20");
		progressWheelView.incrementProgress();
		m_monster = new Monster(m_currentLevel.getStartPosition().x,
				m_currentLevel.getStartPosition().y);
		monsterView = new MonsterView(this, m_monster);
		StartOffsetCircleView.Draw = true;
		circleView = new StartOffsetCircleView(this,m_currentLevel.getStartPosition());
		m_mazeView = new MazeView(this);
		m_mazeView.setViews(gameLevelView, monsterView, circleView,progressWheelView);

		
		
		// endregion
		
		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicView = vi.inflate(R.layout.activity_maze_game, null);
		
		LinearLayout ll = (LinearLayout) dynamicView.findViewById(R.id.testing);
		// insert into main view
		ll.addView(m_mazeView);
		setContentView(dynamicView);
		camSurface = (SurfaceView) findViewById(R.id.dummySurface);
		//addContentView(camSurface, new LayoutParams(LayoutParams.WRAP_CONTENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(StartOffsetCircleView.Draw == true)
		{
			if(!initOffsetCircle(event))
			{
				return super.onTouchEvent(event);
			}
			
		}
		StartOffsetCircleView.Draw = false;
		if (b_playerHasTouchedWall) 
		{
			return super.onTouchEvent(event);
		}

		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_UP:
			onPlayerStopTouchingMonster();
			return super.onTouchEvent(event);
		case MotionEvent.ACTION_MOVE:
			break;
		}

		
		// Moves The Monster On Screen
		m_monster.move(event.getX() + Monster.X_OFFSET, event.getY() + Monster.Y_OFFSET);

		// Gets The Maze Obstacle The Monster Has Touched.
		MazeObstacles touchedMazeObstacle = m_currentLevel
				.touchedMazeObstacle(m_monster);

		if (!b_canMove)// If The Monster Has Left The Monster - Stopped Touching
						// Him.
		{

			if (touchedMazeObstacle != MazeObstacles.START) // If The Player
															// Didn't Touched
															// The Monster
															// Placed On Start
															// Position.
			{
				// Check Next Player Touch (Move).
				return (super.onTouchEvent(event));
			} else {
				// The Monster Can Now Move - The Game Start Again. (Still Same
				// Intent - Same Activity)
				b_canMove = true;
			}
		}

		// Do Different Actions For Each Touched Obstacle
		switch (touchedMazeObstacle) {
		case START:
			break;
		case WALL:
			// Game Over - Start New Intent.
			onTouchWall(event);
			break;
		case FIN:
			onFinishLevel();
			break;
		case BOOYA:
			// Touched BooYa area
			onTouchBooya(event);
			break;
		case SAFE:
			break;
		case BOUNDARIES:// If The Monster Move Is Outside Of Boundaries,
						// Act Like The Player Has Stopped Touching The Monster,
						// Start Again (Same Intent).
			onPlayerStopTouchingMonster();
			return super.onTouchEvent(event);
		default:
			break;
		}

		// Paint The Game With The New Monster Position.
		m_mazeView.setViews(gameLevelView, monsterView, circleView,progressWheelView);
		setContentView(dynamicView);

		return (super.onTouchEvent(event));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze_game, menu);
		return true;
	}

	// endregion

	// region Methods

	/**
	 * Called When The Player Touches A WAll - Game Over.
	 * 
	 * @param event
	 */
	@SuppressLint("NewApi")
	private void onTouchWall(MotionEvent event) {
		// Gets The Motion Action Type.
		final int action = event.getAction();

		// Safe Check - The New Intent Will Be Created Only If The Player
		// Touches
		// The Wall While Moving Is Finger - On Slide Only.
		if (action == MotionEvent.ACTION_MOVE) {
			// Setting Touched Wall Flag To True.
			b_playerHasTouchedWall = true;
			
			// Vibrate

			// Output yes if can vibrate, no otherwise
			if (gameVibrator.hasVibrator()) 
			{
				gameVibrator.vibrate(400);
			}

			// Check if have front camera
			if (CameraHelper.getInstance().isRecording) {
				// // Stopping the service, which stops the video recording
				// Intent intentService = new Intent(this,
				// RecorderService.class);
				// intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// stopService(intentService);
				   cameraHelper.StopRecording();
			}

			// Making An Intent Of The Maze Game Start Menu Activity.
			Intent intent = new Intent(this, GenericGameActivity.class);

			// Starting The Activity.
			startActivity(intent);

		}

	}

	private void onTouchBooya(MotionEvent event) 
	{
		// Gets The Motion Action Type.
		final int action = event.getAction();
		
		// Safe Check - The New Intent Will Be Created Only If The Player
		// Touches
		// The Wall While Moving Is Finger - On Slide Only.
		if (action == MotionEvent.ACTION_MOVE) {
			// Setting Touched Wall Flag To True.
			b_playerHasTouchedWall = true;
			
			BooyaUser.IncreaseNumberOfVictims();

			// SCARYYY FIGURE APPEARS
			Intent intent = new Intent(this, ScaryFigureActivity.class);
			// intent.putExtra("CameraHelper", cameraHelper);

			// Starting The Activity.
			startActivity(intent);

		}

	}

	/**
	 * Called When The Player Stops Touching The Monster.
	 */
	private void onPlayerStopTouchingMonster() 
	{
		// The Player C'ant Move Till He Touches The Monster In Start Position.
		b_canMove = false;
		StartOffsetCircleView.Draw = true;
		// The Monster Return To Start Position
		m_monster.move(levels[n_gameLevel].getStartPosition().x,
				levels[n_gameLevel].getStartPosition().y);
		circleView = new StartOffsetCircleView(this,levels[n_gameLevel].getStartPosition());
		// Set The Views To Paint The Monster In Start Position
		m_mazeView.setViews(gameLevelView, monsterView, circleView, progressWheelView);
		setContentView(m_mazeView);
	}
	
	@SuppressLint("NewApi")
	private void onFinishLevel() {
		
		if(n_gameLevel < levels.length)
		{
			n_gameLevel++;
			m_currentLevel = levels[n_gameLevel];
			gameLevelView = new GameLevelView(this);
			gameLevelView.setGameLevel(m_currentLevel);
		}
		
		if(n_gameLevel == 1)
		{
			CameraHelper c = CameraHelper.getInstance();
			c.SetSurfaceView(camSurface);
			Intent i = new Intent(this, RecordingService.class);
			startService(i);
		}
		if (n_gameLevel == 2)
		{
			Intent i = new Intent(this, RecordingService.class);
			stopService(i);
		}
	}

	private void initLevels() {
		levels = new GameLevel[3];
		levels[0] = new GameLevel1();
		levels[1] = new GameLevel2();
		levels[2] = new GameLevel3();
		
	}

	private boolean initOffsetCircle(MotionEvent event)
	{

		
			
	        int x = (int)event.getX();
	        int y = (int)event.getY();
	        
	        
	        try
	        {

			        if(circleView.IsTouchCircle(x,y))
			        {
			        	
						progressWheelView.spin();
						progressWheelView.setText("11");
						for(int k=0;k<20;k++)
						progressWheelView.incrementProgress();
						m_mazeView.setViews(gameLevelView, monsterView, circleView,progressWheelView);
			        	
			        	
			        	
			        	PointF circleCenter = StartOffsetCircleView.CircleCenter;
			        	float radius = Monster.RADIUS;
			        	
			        	Monster.Y_OFFSET =  -(y - (circleCenter.y));
			        	Monster.X_OFFSET =  -(x - (circleCenter.x));
			    		StartOffsetCircleView.Draw = false;
			        	return true;
			        }
			        else
			        {
			        	return false;
			        }
			        
			        }
			        catch(Exception ex)
			        {
			        	
			        }
	        return false;
		}
		
		
		
	
	
	// endregion
}
