package com.example.booya;



import com.example.booya.R;
import com.example.booya.BL.GameLevel;
import com.example.booya.BL.GameLevel1;
import com.example.booya.BL.MazeObstacles;
import com.example.booya.BL.Monster;
import com.example.booya.UI.Views.GameLevelView;
import com.example.booya.UI.Views.MazeView;
import com.example.booya.UI.Views.MonsterView;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;

public class MazeGameActivity extends Activity
{

	//region members
	
	/**
	 * Initialise members
	 */
	private MazeView m_mazeView;
	private Monster m_monster;
	private GameLevel m_currentLevel;
	GameLevelView gameLevelView;
	MonsterView monsterView;
	public static int screenWidth, screenHeight;
	
	
	// flag - true if the player has touched a wall.
	public static boolean b_playerHasTouchedWall = false;
	
	// flag - true  if the player can move
	public static boolean b_canMove = false;
	
	//endregion
	

	//region Events
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//region initialise members
		
		
		Display display = getWindowManager().getDefaultDisplay();

		/*	api 13+
		 * Point size = new Point();	
		 *  display.getSize(size);
			screenWidth = size.x;
			screenHeight = size.y;
		*/
		
		screenHeight = display.getHeight();
		screenWidth = display.getWidth();
		
		b_playerHasTouchedWall = false;
		b_canMove = false;
		
		m_currentLevel = new GameLevel1();
	    gameLevelView = new GameLevelView(this);
		gameLevelView.setGameLevel(m_currentLevel);
		
		m_monster = new Monster(m_currentLevel.getStartPosition().x, m_currentLevel.getStartPosition().y);
		monsterView = new MonsterView(this, m_monster); 
		
		m_mazeView = new MazeView(this);
		m_mazeView.setViews(gameLevelView, monsterView);
		
		
		//endregion
		setContentView(m_mazeView);
		
	}


    
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{

		if(b_playerHasTouchedWall)
		{
			return super.onTouchEvent(event);
		}
       
	   final int action = event.getAction();
	   
	   switch(action)
	   {
	   case MotionEvent.ACTION_UP:
		   onPlayerStopTouchingMonster();
		   return super.onTouchEvent(event);
	   case MotionEvent.ACTION_MOVE :
		   break;
	   }
	   


	    // Moves The Monster On Screen
		m_monster.move(event.getX(), event.getY());

		
		// Gets The Maze Obstacle The Monster Has Touched.
		MazeObstacles touchedMazeObstacle = m_currentLevel.touchedMazeObstacle(m_monster);
		
		if(!b_canMove)// If The Monster Has Left The Monster - Stopped Touching Him.
		{
			
			if(touchedMazeObstacle != MazeObstacles.START) // If The Player Didn't Touched The Monster Placed On Start Position.
			{
				// Check Next Player Touch (Move).
				return (super.onTouchEvent(event));			
			}
			else
			{
				// The Monster Can Now Move - The Game Start Again. (Still Same Intent - Same Activity)
				b_canMove = true;
			}
		}
		
		// Do Different Actions For Each Touched Obstacle
		switch(touchedMazeObstacle)
		{
			case START:
				break;
			case WALL: 
				// Game Over - Start New Intent.
				onTouchWall(event);
				break;
			case FIN:  
				break;
			case BOOYA:
				break;
			case SAFE:
				break;
			case BOUNDARIES:// If The Monster Move Is Outside Of Boundaries,
						    //   Act Like The Player Has Stopped Touching The Monster, Start Again (Same Intent).
				   onPlayerStopTouchingMonster();
				   return super.onTouchEvent(event);
			default:
					break;
		}


		// Paint The Game With The New Monster Position.
		m_mazeView.setViews(gameLevelView, monsterView);
		setContentView(m_mazeView);
		
		return (super.onTouchEvent(event));

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze_game, menu);
		return true;
	}

	//endregion
	
	
	//region Methods
	
	
	/**
	 * Called When The Player Touches A WAll - Game Over.
	 * @param event
	 */
	private void onTouchWall(MotionEvent event)
	{
	   // Gets The Motion Action Type.
	   final int action = event.getAction();

	   // Safe Check - The New Intent Will Be Created Only If The Player Touches
	   // The Wall While Moving Is Finger - On Slide Only.
	   if(action == MotionEvent.ACTION_MOVE) 
	   {
		    // Setting Touched Wall Flag To True. 
		    b_playerHasTouchedWall  = true;
		    
		    // Making An Intent Of The Maze Game Start Menu Activity.
			Intent intent = new Intent(this, GenericGameActivity.class);
			
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
		
		// The Monster Return To Start Position
		m_monster.move(m_currentLevel.getStartPosition().x, m_currentLevel.getStartPosition().y);
		
		// Set The Views To Paint The Monster In Start Position
		m_mazeView.setViews(gameLevelView, monsterView);
		setContentView(m_mazeView);
	}
	
	
	//endregion
}
