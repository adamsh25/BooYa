package com.example.booya.BL;

public class GameLevel1 extends GameLevel 
{
	int[][] newLevel = 
		{
			{2,2,2,2,2,2,2,2,2,3},
			{2,0,0,0,0,0,0,0,0,0},
			{2,0,2,2,2,2,2,2,2,2},
			{2,0,0,0,0,0,0,0,0,2},
			{2,2,2,2,2,2,2,2,0,2},
			{0,0,0,2,2,2,2,2,0,2},
			{0,2,0,2,2,2,2,2,0,2},
			{0,2,0,2,2,2,2,2,0,2},
			{0,2,0,0,0,0,2,2,0,2},
			{1,2,2,2,2,0,0,0,0,2}
		};
	public GameLevel1()
	{
		UpdateMazeObstaclesMatrix(newLevel);
		
	}

}
