package com.example.booya.BL;

public enum MazeObstacles 
{
	
	SAFE(0),
	START(1),
    WALL(2),
    FIN(3),
	BOOYA(4),
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
	            return SAFE;
	        case 1:
	            return START;
	        case 2:
	            return WALL;
	        case 3:
	            return FIN;
	        case 4:
	        	return BOOYA;
	        case 5:
	        	return BOUNTY;
	        }
	        return null;
	}
	
	public int GetObstacle()
	{
		return this.obstacleValue;
		
	}
}
