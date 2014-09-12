package com.example.booya;

import java.util.Timer;
import java.util.TimerTask;

import android.os.*;
import android.os.Process;
import android.util.Log;
import android.view.*;
import com.example.booya.BL.*;
import com.example.booya.BL.MazeLevel;
import com.example.booya.UI.Views.*;
import com.example.booya.UI.Views.TimerWheelView;
import com.example.booya.video.recording.CameraHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.booya.video.recording.RecordingIntentService;

public class MazeGameActivity extends Activity
{
    private String TAG = "MazeGameActivity";

	class timerTask extends TimerTask
	{
		public void run() 
		{
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (!progressWheelView.incrementProgress()) {
                        onTimeEnd();
                    }
                }
            });
        }
	}
	
	// region members
	
	private MazeView mazeView;
	
	// Monster members
	private Monster monster;
	private MonsterView monsterView;
	
	// Game Level members
	private MazeLevel currentLevel;
	private MazeLevelView mazeLevelView;
	
	// Offset Circle members 
	private StartOffsetCircleView circleView;
	
	// Timer members
	private TimerWheelView progressWheelView;
	private Timer timerWheelThread = new Timer();
	private final long SECONDS_TO_GAME_OVER = 30;

	private Vibrator gameVibrator; //todo: use
	
	// Design members 
	public static int mazeViewWidth, mazeViewHeight;
	private SurfaceView camSurface;
	private boolean n_Start_Rec =false;
	public static boolean n_Surface_Gone =false;
	
	// flag - true if the player has touched a wall.
	public static boolean b_playerHasTouchedWall = false;

	// flag - true if the player can move
	public static boolean b_canMove = false;

    public boolean b_gotToBooya = false;

	// current level
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

		Display display = getWindowManager().getDefaultDisplay(); //todo: extract to a special class

		mazeViewHeight = display.getHeight() - 1;
		mazeViewWidth = display.getWidth();

		// Get instance of Vibrator from current Context
		gameVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		b_playerHasTouchedWall = false;
		
		b_canMove = false;
        b_gotToBooya = false;

		//initLevels();
		n_gameLevel = getIntent().getIntExtra("levelId", 0);
		currentLevel = MazeLevelFactory.BuildMazeLevel(n_gameLevel);
		mazeLevelView = new MazeLevelView(this, currentLevel);
		
		progressWheelView = new TimerWheelView(this, currentLevel.getSCREEN_SIZE());
		
		monster = new Monster(currentLevel.getStartPosition().x,
				currentLevel.getStartPosition().y, currentLevel.MAZE_OBSTACLE_SIZE);
		monsterView = new MonsterView(this, monster);
		
		//StartOffsetCircleView._shouldDraw = true;
		circleView = new StartOffsetCircleView(this, currentLevel, monster);
		
		mazeView = new MazeView(this, mazeLevelView, monsterView, circleView, progressWheelView);
		
		// endregion

		setContentView(R.layout.activity_maze_game);

        RelativeLayout mazeGameView = ((RelativeLayout)findViewById(R.id.relative));
        mazeGameView.addView(mazeView, ViewGroup.LayoutParams.MATCH_PARENT);

        timerWheelThread = new Timer();
        long timerGameOverMinutes = SECONDS_TO_GAME_OVER / 60;
        long timerGameOverInMilliseconds = 1000 * timerGameOverMinutes;
        timerWheelThread.schedule(new timerTask(), 0, timerGameOverInMilliseconds); //todo: start when first touched?

		camSurface = (SurfaceView) findViewById(R.id.dummySurface);

        //camSurface.setVisibility(SurfaceView.GONE);//todo: remove all games with gone and not gone... that's not our performance problem probably.
		//addContentView(camSurface, new LayoutParams(LayoutParams.WRAP_CONTENT));
	}

    @Override
    protected void onResume() {
        super.onResume();

        if (CameraHelper.getInstance().HasFrontFacingCamera()) {
            Intent i = new Intent(this, RecordingIntentService.class);
            i.setAction(RecordingIntentService.ACTION_OPEN_CAMERA);
            startService(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!b_gotToBooya && CameraHelper.getInstance().HasFrontFacingCamera()) { //todo: not really has but also want to use camera (tester)
            RecordingIntentService.setShouldRecord(false);
            Intent i = new Intent(this, RecordingIntentService.class);
            i.setAction(RecordingIntentService.ACTION_STOP_RECORDING);
            i.putExtra(RecordingIntentService.ACTION_RELEASE_CAMERA, true);

            startService(i);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if (n_Start_Rec) 
		{
            CameraHelper.getInstance().SetSurfaceView(camSurface);
            Intent i = new Intent(this, RecordingIntentService.class);
            i.setAction(RecordingIntentService.ACTION_START_RECORDING);
            i.putExtra(RecordingIntentService.EXTRA_THREAD_PRIORITY, Process.THREAD_PRIORITY_BACKGROUND);
            startService(i);
            n_Start_Rec = false;
	    }

        if (!n_Surface_Gone && CameraHelper.getInstance().isRecording) {
            //camSurface.setVisibility(SurfaceView.GONE);
            ((RelativeLayout)findViewById(R.id.relative)).removeView(camSurface); //TODO: maybe only change to gone
            n_Surface_Gone = true;
        }

//		if(StartOffsetCircleView._shouldDraw == true)
//		{
//			if(!initOffsetCircle(event))
//			{
//				return false;
//			}
//
//		}
//		StartOffsetCircleView._shouldDraw = false;
		
		if (b_playerHasTouchedWall) 
		{
			return false;
		}

		final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
                onPlayerStopTouchingMonster();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return false;
        }

		
		// Moves The Monster On Screen
        monsterView.MoveMonster(event.getX() + circleView.get_xOffset(), event.getY() + circleView.get_yOffset());
		//monster.move( + Monster.get_xOffset(), event.getY() + Monster.get_yOffset());
        //monsterView.invalidate(); //todo: move both lines to mazeview

		// Gets The Maze Obstacle The Monster Has Touched.
		MazeObstacles touchedMazeObstacle = currentLevel
				.touchedMazeObstacle(monster);

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
		//mazeView.setViews(mazeLevelView, monsterView, circleView,progressWheelView);
        //camSurface.draw(canvas);
		//setContentView(dynamicView);
		
//		if(!n_Surface_Gone)
//		{
//		setContentView(dynamicView);
//		}
//		else
//		{
//			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.activity_maze_game, null);
//			
//			LinearLayout ll = (LinearLayout) root.findViewById(R.id.testing);
//			
//			((ViewGroup)mazeView.getParent()).removeView(mazeView);
//			((ViewGroup)ll.getParent()).removeView(ll);
//
//
//			setContentView(mazeView);
//		}

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
	private void onTouchWall(MotionEvent event) 
	{
		// Gets The Motion Action Type.
		final int action = event.getAction();
		
		// Stops the timer wheel thread
		timerWheelThread.cancel();
		
		// Safe Check - The New Intent Will Be Created Only If The Player
		// Touches
		// The Wall While Moving Is Finger - On Slide Only.
		if (action == MotionEvent.ACTION_MOVE) {
            Log.d(TAG, "Touched wall - GAME OVER");

			// Setting Touched Wall Flag To True.
			b_playerHasTouchedWall = true;
			
			// Vibrate

			// Output yes if can vibrate, no otherwise
//			if (gameVibrator.hasVibrator()) 
//			{
//				gameVibrator.vibrate(400);
//			}



			// Check if have front camera
			if (CameraHelper.getInstance().HasFrontFacingCamera() && CameraHelper.getInstance().isRecording) { //todo: not really has but also want to use camera (tester)
				// // Stopping the service, which stops the video recording
				// Intent intentService = new Intent(this,
				// RecorderService.class);
				// intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// stopService(intentService);
				//  cameraHelper.StopRecording();
                RecordingIntentService.setShouldRecord(false);
                Intent i = new Intent(this, RecordingIntentService.class);
                i.setAction(RecordingIntentService.ACTION_STOP_RECORDING);
                //i.putExtra(RecordingIntentService.THREAD_PRIORITY, Process.THREAD_PRIORITY_BACKGROUND);
                startService(i);
			}

			// Making An Intent Of The Maze Game Start Menu Activity.
			Intent intent = new Intent(this, GenericGameActivity.class);
			Intent myIntent = getIntent(); // gets the previously created intent
			PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
			intent.putExtra("curPrankMethod",curPrankMethod);
			// Starting The Activity.
			startActivity(intent);

            //returnToFirstLevel(); //TODO: is it ok? I changed it to stop reloading the activity every time
		}

	}

//    private void returnToFirstLevel() {
//        // The Player C'ant Move Till He Touches The Monster In Start Position.
//        b_canMove = false;
//        StartOffsetCircleView._shouldDraw = true;
//        // The Monster Return To Start Position
//        monster.move(levels[0].getStartPosition().x,
//                levels[0].getStartPosition().y);
//        circleView = new StartOffsetCircleView(this,levels[n_gameLevel].getStartPosition(), (int) monster.getSIZE());
//        // Set The Views To Paint The Monster In Start Position
//        mazeView.setViews(mazeLevelView, monsterView, circleView, progressWheelView);
//        setContentView(dynamicView);
//    }

    private void onTouchBooya(MotionEvent event)
	{
        Log.d(TAG, "Finished game succesfully");

		// Gets The Motion Action Type.
		final int action = event.getAction();
		
		// Stops the timer wheel thread
		timerWheelThread.cancel();
		
		// Safe Check - The New Intent Will Be Created Only If The Player
		// Touches
		// The Wall While Moving Is Finger - On Slide Only.
		if (action == MotionEvent.ACTION_MOVE) {
			// Setting Touched Wall Flag To True.
			b_playerHasTouchedWall = true;
            b_gotToBooya = true;
			
			if(!TesterActivity.bIsDUBUG)
			BooyaUser.IncreaseNumberOfVictims();

            if (CameraHelper.getInstance().HasFrontFacingCamera()) { //todo: not really has but also want to use camera (tester)
                RecordingIntentService.setShouldRecord(false);
                Intent i = new Intent(this, RecordingIntentService.class);
                i.setAction(RecordingIntentService.ACTION_STOP_RECORDING);
                i.putExtra(RecordingIntentService.ACTION_RELEASE_CAMERA, true);
                i.putExtra(RecordingIntentService.EXTRA_DELAY_SECONDS, 3);
                //i.putExtra(RecordingIntentService.THREAD_PRIORITY, android.os.Process.THREAD_PRIORITY_BACKGROUND); TODO: correct?
                i.putExtra(RecordingIntentService.EXTRA_START_FFMPEG, true);
                i.putExtra(RecordingIntentService.EXTRA_WRITE_TO_DB, true);
            }

			// SCARYYY FIGURE APPEARS
			Intent intent = new Intent(this, ScaryFigureActivity.class);
			Intent myIntent = getIntent(); // gets the previously created intent
			PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
			intent.putExtra("curPrankMethod",curPrankMethod);
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
		//StartOffsetCircleView._shouldDraw = true;
		// The Monster Return To Start Position
        monsterView.MoveMonster(currentLevel.getStartPosition().x, currentLevel.getStartPosition().y);
		//monster.move(currentLevel.getStartPosition().x,
		//		currentLevel.getStartPosition().y);
		circleView.Redraw(currentLevel);
		// Set The Views To Paint The Monster In Start Position
		//mazeView.setViews(mazeLevelView, monsterView, circleView, progressWheelView); //todo: delete
		//setContentView(dynamicView); //todo: delete
	}
	
	@SuppressLint("NewApi")
	private void onFinishLevel() {
        n_gameLevel++;

        Log.d(TAG, "Finished level " + n_gameLevel);

		if(n_gameLevel < MazeLevelFactory.NumberOfLevels())
		{
			currentLevel = MazeLevelFactory.BuildMazeLevel(n_gameLevel);
			mazeLevelView.SetLevel(currentLevel);
		}

        if (n_gameLevel == (MazeLevelFactory.NumberOfLevels() - 1) && CameraHelper.getInstance().HasFrontFacingCamera()) { //todo: not really has but also want to use camera (tester)
            camSurface.setVisibility(SurfaceView.VISIBLE);
            n_Start_Rec = true;
//            ((RelativeLayout)findViewById(R.id.relative)).removeView(camSurface);
//            setContentView(dynamicView);
        }
		
//		if(n_gameLevel == 1)
//		{
//			CameraHelper c = CameraHelper.getInstance();
//			c.SetSurfaceView(camSurface);
//			Intent i = new Intent(this, RecordingService.class);
//			startService(i);
//		}
//		if (n_gameLevel == 2)
//		{
//			Intent i = new Intent(this, RecordingService.class);
//			stopService(i);
//		}
	}

//	private void initLevels() //todo: consider MazeLevelFactory (factory)
//	{
//		levels = new MazeLevel[3];
//		levels[0] = new MazeLevel1();
//		levels[1] = new MazeLevel2();
//		levels[2] = new MazeLevel3();
//
//	}

//	private boolean initOffsetCircle(MotionEvent event)
//	{
//
//	        int x = (int)event.getX();
//	        int y = (int)event.getY();
//
//
//	        try
//	        {
//			        if(circleView.IsTouchCircle(x, y))
//			        {
//			        	PointF circleCenter = StartOffsetCircleView.CircleCenter;
//			        	Monster.set_yOffset(-(y - (circleCenter.y)));
//			        	Monster.set_xOffset(-(x - (circleCenter.x)));
//			    		StartOffsetCircleView._shouldDraw = false;
//			        	return true;
//			        }
//			        else
//			        {
//			        	return false;
//			        }
//
//			        }
//			        catch(Exception ex)
//			        {
//
//			        }
//	        return false;
//		}
		
		
	public void onTimeEnd()
	{
		// Making An Intent Of The Maze Game Start Menu Activity.
		Intent intent = new Intent(this, GenericGameActivity.class);
		timerWheelThread.cancel();
		Intent myIntent = getIntent(); // gets the previously created intent
		PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
		intent.putExtra("curPrankMethod", curPrankMethod);
		// Starting The Activity.
		startActivity(intent);
		
	}

	
	// endregion
}
