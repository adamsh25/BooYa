package com.example.booya.BL;

public enum MazeObstacles
{
	SAFE(0),
	START(1),
    WALL(2),
    FIN(3),
	BOOYA(4),
	BOUNDARIES(5);

	private int mValue;
	private MazeObstacles(int obstacleValue)
	{
        mValue = obstacleValue;
	}
	
	// Get The Maze Enumerated Obstacle Name By A Given Indexed Number
	public static MazeObstacles GetMazeObstacleByNumber(int x)
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
	        	return BOUNDARIES;
	        }
	        return null;
	}
}
