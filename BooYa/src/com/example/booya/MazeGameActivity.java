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
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;

public class MazeGameActivity extends Activity {

	//region members
	private MazeView m_mazeView;
	private Monster m_monster;
	private GameLevel m_currentLevel;
	GameLevelView gameLevelView;
	MonsterView monsterView;
	public static int screenWidth, screenHeight;
	public static boolean b_canMove = true;
	//endregion
	
	//region Methods
	
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
		
		
		m_currentLevel = new GameLevel1();
	    gameLevelView = new GameLevelView(this);
		gameLevelView.setGameLevel(m_currentLevel);
		
		m_monster = new Monster(5, (0.8f)*screenHeight, screenWidth, screenHeight);
		monsterView = new MonsterView(this, m_monster); 
		
		m_mazeView = new MazeView(this);
		m_mazeView.setViews(gameLevelView, monsterView);
		
		//endregion
		setContentView(m_mazeView);
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{

		if(!b_canMove)
		{
			try 
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b_canMove = true;
		}
		

		boolean isLegalMove = m_monster.move(event.getX(), event.getY());
		try 
		{
			Thread.sleep(20);
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!isLegalMove)
		{
			
			try 
			{
				Thread.sleep(5000);
			} 
			catch (InterruptedException e)
			{


				e.printStackTrace();
			}
			
		}

		MazeObstacles touchedMazeObstacle = m_currentLevel.TouchedMazeObstacle(m_monster);
		
		switch(touchedMazeObstacle)
		{
		case START:
			break;
		case WALL: 
			b_canMove = false;
			m_monster.move(5, (0.6f)*screenHeight);
			break;
		case FIN:  
			
			try 
			{
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_monster.move(0, (0.8f)*screenHeight);
			break;
		case BOOYA:
			break;
		case SAFE:
			break;
		case BOUNTY:
			break;
		default:
				break;
		}

		m_mazeView.setViews(gameLevelView, monsterView);
		setContentView(m_mazeView);
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze_game, menu);
		return true;
	}

	//endregion
}