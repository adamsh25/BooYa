package com.example.booya.UI.Views;

import com.example.booya.BL.GameLevel;
import com.example.booya.BL.MazeObstacles;

import android.content.Context;
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
				
				
				
				if( curMazeObstacle == MazeObstacles.START)
				{
					canvas.drawRect(this.m_level.GetMazeObstacleLeft(j),
									this.m_level.GetMazeObstacleTop(i),
									this.m_level.GetMazeObstacleRight(j),
									this.m_level.GetMazeObstacleBottom(i),
									this.getStartPaint());
					
				}
				
				if(curMazeObstacle == MazeObstacles.WALL)
				{
					canvas.drawRect(this.m_level.GetMazeObstacleLeft(j),
									this.m_level.GetMazeObstacleTop(i),
									this.m_level.GetMazeObstacleRight(j),
									this.m_level.GetMazeObstacleBottom(i),
									this.getWallPaint());
					
				}
				
				if(curMazeObstacle == MazeObstacles.FIN)
				{
					canvas.drawRect(this.m_level.GetMazeObstacleLeft(j),
							this.m_level.GetMazeObstacleTop(i),
							this.m_level.GetMazeObstacleRight(j),
							this.m_level.GetMazeObstacleBottom(i),
							this.getFinishPaint());
				}
			}
			
		}
	}
	
	public void setGameLevel(GameLevel level) 
	{
		this.m_level = level;
	}
	
	protected Paint getWallPaint()
	{
		if(this.m_wallPaint == null)
		{
			this.m_wallPaint = new Paint();
			this.m_wallPaint.setStrokeWidth(10);
			this.m_wallPaint.setColor(Color.RED);
		}
		this.m_wallPaint.setColor(Color.RED);
		return (this.m_wallPaint);
	}
	
	protected Paint getFinishPaint()
	{
		if(this.m_wallPaint == null)
		{
			this.m_wallPaint = new Paint();
			this.m_wallPaint.setStrokeWidth(10);
			this.m_wallPaint.setColor(Color.BLUE);
		}
		this.m_wallPaint.setColor(Color.BLUE);
		return (this.m_wallPaint);
	}
	
	protected Paint getStartPaint()
	{
		if(this.m_wallPaint == null)
		{
			this.m_wallPaint = new Paint();
			this.m_wallPaint.setStrokeWidth(10);
			this.m_wallPaint.setColor(Color.WHITE);
		}
		this.m_wallPaint.setColor(Color.WHITE);
		return (this.m_wallPaint);
	}
	
    //endregion
	
	
}
