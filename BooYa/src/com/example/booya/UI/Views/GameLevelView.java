package com.example.booya.UI.Views;

import com.example.booya.BL.GameLevel;
import com.example.booya.BL.MazeObstacles;

import android.content.Context;
import android.database.CursorWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GameLevelView extends View{

	//region members
	private GameLevel m_level; // The Wall Array
	private Paint m_wallPaint;
	//endregion


	//region C'tor
	public GameLevelView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	//endregion
	
	
	//region Methods

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		for (int i = 0; i < this.m_level.MaxRows; i++) 
		{
			for (int j = 0; j < this.m_level.MaxCols; j++) 
			{
				MazeObstacles curMazeObstacle = this.m_level.MazeObstacleAt(i, j);
				
				if(curMazeObstacle == MazeObstacles.FIN 
				   || curMazeObstacle == MazeObstacles.START
				   || curMazeObstacle == MazeObstacles.WALL)
				{
					canvas.drawRect(this.m_level.GetMazeObstacleLeft(j),
							this.m_level.GetMazeObstacleTop(i),
							this.m_level.GetMazeObstacleRight(j),
							this.m_level.GetMazeObstacleBottom(i),
							this.getObstaclePaint(curMazeObstacle));
				}
			}
			
		}
	}
	
	public void setGameLevel(GameLevel level) 
	{
		this.m_level = level;
	}
	
	protected Paint getObstaclePaint(MazeObstacles obstacle)
	{
		if(this.m_wallPaint == null)
		{
			this.m_wallPaint = new Paint();
			this.m_wallPaint.setStrokeWidth(10);
		}
		
		switch(obstacle)
		{
		case BOOYA:
			break;
		case BOUNTY:
			break;
		case FIN: this.m_wallPaint.setColor(Color.BLUE);
			break;
		case SAFE:
			break;
		case START:this.m_wallPaint.setColor(Color.WHITE);
			break;
		case WALL: this.m_wallPaint.setColor(Color.RED);
			break;
		default:
			break;
		
		}

		return (this.m_wallPaint);
	}
	
	
    //endregion
	
	
}
