package com.example.booya.BL;

public enum MazeObstacles 
{
	
	FUTURE_SAFE(0),
	SAFE(1),
    WALL(3),
	BOOYA(4),
	BOUNTY(15);
	
	
	private int obstacleValue;
	private MazeObstacles(int obstacle)
	{
		this.obstacleValue = obstacle;
		
	}
	
	public MazeObstacles GetMazeObstacleByValue(int value)
	{
		return MazeObstacles.values()[value];
	}
	
	public int GetObstacle()
	{
		return this.obstacleValue;
		
	}
}
