package com.example.booya.UI.Views;

import android.view.MotionEvent;
import com.example.booya.BL.MazeLevel;
import com.example.booya.BL.MazeObstacles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MazeLevelView extends View
{
    //region members

	/**
	 * Declare members
	 */
	private MazeLevel _level; // The Game Level Logic
	private Paint m_wallPaint; // The Wall Design Member 
	
	//endregion


	//region C'tor
	
	/**
	 * Empty C'tor
	 * @param context
	 */
	public MazeLevelView(Context context, MazeLevel level)
	{
		super(context);
        _level = level;
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
		//super.onDraw(canvas);
		
		// runs through all the wall matrix rows
		for (int row = 0; row < _level.getLevelMatrixRowsNum(); row++)
		{
			// runs throught all the wall columns
			for (int col = 0; col < _level.getLevelMatrixColsNum(); col++)
			{
				// gets the maze obstacle type by the current row an column.
				MazeObstacles curMazeObstacle = _level.mazeObstacleAt(row, col);
				

					// Draws A Rectangle By His Left, Top, Right And Bottom Pixel Place.
					//  Designed By A Paint Member For Each Obstacle Type.
					canvas.drawRect(this._level.GetMazeObstacleLeft(col),
							this._level.GetMazeObstacleTop(row),
							this._level.GetMazeObstacleRight(col),
							this._level.GetMazeObstacleBottom(row),
							this.getObstaclePaint(curMazeObstacle));
				
			}
			
		}
	}
	
	//endregion
	
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

        //todo: invalidate when moved
    }

    public void SetLevel(MazeLevel level) {
        _level = level;
        invalidate();
    }
	
	
    //endregion
	
	
}
