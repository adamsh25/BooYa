package com.example.booya.BL;

public enum MazeObstacles 
{
	
	FUTURE_SAFE(0),
	SAFE(1),
    WALL(2),
	BOOYA(3),
	BOUNTY(15);
	
	
	private int obstacleValue;
	private MazeObstacles(int obstacle)
	{
		this.obstacleValue = obstacle;
		
	}
	
	public static MazeObstacles GetMazeObstacleByValue(int x)
	{
        switch(x) {
	        case 0:
	            return FUTURE_SAFE;
	        case 1:
	            return SAFE;
	        case 2:
	            return BOOYA;
	        case 3:
	            return BOUNTY;
	        }
	        return null;
	}
	
	public int GetObstacle()
	{
		return this.obstacleValue;
		
	}
}
