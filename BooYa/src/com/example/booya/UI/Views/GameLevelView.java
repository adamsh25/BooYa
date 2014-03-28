package com.example.booya.UI.Views;

import com.example.booya.BL.GameLevel;
import com.example.booya.BL.MazeObstacles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GameLevelView extends View
{

	//region members
	
	
	/**
	 * Declare members
	 */
	private GameLevel m_level; // The Game Level Logic
	private Paint m_wallPaint; // The Wall Design Member 
	
	//endregion


	//region C'tor
	
	/**
	 * Empty C'tor
	 * @param context
	 */
	public GameLevelView(Context context) 
	{
		super(context);
	}
	
	//endregion
	
	
	//region Methods

	//region Events

	/* 
	 * Draws The Game Maze
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		
		// runs through all the wall matrix rows
		for (int row = 0; row < GameLevel.MAX_ROWS; row++) 
		{
			// runs throught all the wall columns
			for (int col = 0; col < GameLevel.MAX_COLS; col++) 
			{
				// gets the maze obstacle type by the current row an column.
				MazeObstacles curMazeObstacle = this.m_level.mazeObstacleAt(row, col);
				

					// Draws A Rectangle By His Left, Top, Right And Bottom Pixel Place.
					//  Designed By A Paint Member For Each Obstacle Type.
					canvas.drawRect(this.m_level.GetMazeObstacleLeft(col),
							this.m_level.GetMazeObstacleTop(row),
							this.m_level.GetMazeObstacleRight(col),
							this.m_level.GetMazeObstacleBottom(row),
							this.getObstaclePaint(curMazeObstacle));
				
			}
			
		}
	}
	
	//endregion
	
	
	/**
	 * Set The Level Of The Game To Draw View By A Specific Level.
	 * @param level
	 */
	public void setGameLevel(GameLevel level)
	{
	
		this.m_level = level;
	}
	

	
	
	/**
	 * The Method Is Used To Uniquely Paint Each Obstacle For Drawing.
	 * Creates And Gets The Paint From An Obstacle Type.
	 * @param obstacle - The Obstacle To Draw For.
	 * @return A Paint Object
	 */
	protected Paint getObstaclePaint(MazeObstacles obstacle)
	{
		
		// Creates A Paint Member Only Ones (Singleton)
		if(this.m_wallPaint == null)
		{
			this.m_wallPaint = new Paint();
			this.m_wallPaint.setStrokeWidth(10);
		}
		
		switch(obstacle)
		{
			case BOOYA:
				this.m_wallPaint.setColor(Color.CYAN);
				break;
			case BOUNDARIES:
				this.m_wallPaint.setColor(Color.BLACK);
				break;
			// Set Fin Obstacle Color 	
			case FIN: 
				this.m_wallPaint.setColor(Color.RED);
				break;
			case SAFE:
				this.m_wallPaint.setColor(Color.CYAN);
				break;
			// Set Start Obstacle Color
			case START:
				this.m_wallPaint.setColor(Color.BLUE);
				break;
			// Set Wall Obstacle Color
			case WALL: 
				this.m_wallPaint.setColor(Color.BLACK);
				break;
			default:
				this.m_wallPaint.setColor(Color.BLACK);
				break;
		}
	
		return (this.m_wallPaint);
		
	}
	
	
    //endregion
	
	
}
