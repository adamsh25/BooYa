package com.example.booya.BL;
import android.graphics.Point;
import android.graphics.PointF;

public class GameLevel1 extends GameLevel 
{
	
	
	// Inits A Level Matrix
	int[][] newLevel = 
		{
			{2,2,2,2,2,2,2,2,3,2},
			{2,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,2},
			{2,0,2,2,2,2,2,2,2,2},
			{2,0,0,0,0,0,0,0,0,2},
			{2,2,2,2,2,2,2,2,0,2},
			{0,0,0,2,2,0,0,2,0,2},
			{0,2,0,2,2,0,2,2,0,2},
			{0,2,0,2,2,0,0,0,0,2},
			{0,2,0,0,0,0,2,2,0,2},
			{1,2,2,2,2,2,2,2,2,2}
			
		};
	

	
	public GameLevel1()
	{
		// Uses The Number Matrix To Create An Obstacles Matrix.
		UpdateMazeObstaclesMatrix(newLevel);
	}



	@Override
	protected int GetStartPosX() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	protected int GetStartPosY() {
		// TODO Auto-generated method stub
		return 11;
	}

}
