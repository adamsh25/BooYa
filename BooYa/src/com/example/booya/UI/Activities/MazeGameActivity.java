package com.example.booya.UI.Activities;



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
	//endregion
	
	//region Methods
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//region initialise members
		
		int screenWidth, screenHeight;
		Display display = getWindowManager().getDefaultDisplay();

		/*	api 13 
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
		
		m_monster = new Monster(0, 0, screenWidth, screenHeight);
		monsterView = new MonsterView(this, m_monster); 
		
		m_mazeView = new MazeView(this);
		m_mazeView.setViews(gameLevelView, monsterView);
		
		//endregion
		setContentView(m_mazeView);
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		
		boolean canMove = m_monster.move(event.getX(), event.getY());
		if(!canMove)
		{
			m_monster.move(0, 0);
		}
		m_mazeView.setViews(gameLevelView, monsterView);
		setContentView(m_mazeView);
		if(m_currentLevel.TouchedMazeObstacle(m_monster) == MazeObstacles.WALL)
		{
			
			String s = "Game Over - Start Again"; 
			@SuppressWarnings("unused")
			String s2 = s + "what?";
			m_monster.move(0, 0);
					 
		}
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
