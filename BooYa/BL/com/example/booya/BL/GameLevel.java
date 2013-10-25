package com.example.booya.BL;

public abstract class GameLevel 
{
    // Space from the top screen from which the bricks will be drawn

    public final int TopPadding = 25;
 
    // Space between each Wall
    public final int MazeObstaclePadding = 0;
 
    // Brick Width and Height
    public final int MazeObstacleSize = 15;

    // Max number of Walls rows and columns
    public final int MaxRows = 10;

    public final int MaxCols = 10;

    // The bricks array that we will be working on.
    protected MazeObstacles[][] level;
    // Constructor just creates an empty array of bricks
    public GameLevel()
    {
        this.level = new MazeObstacles[this.MaxRows][this.MaxCols];
    }
    
    public void UpdateMazeObstaclesMatrix(int[][] obstacles)
    {
    	for(int row = 0; row < this.level.length; row++)
    	{
    		for(int col = 0; col < this.level[0].length; col++)
    		{
    			this.level[row][col] = MazeObstacles.GetMazeObstacleByValue(obstacles[row][col]);
    		}
    	}
    	
    }
    
    
    
    /*
    private void InitMazeObstacles()
    {
    	for(int row = 0; row < this.level.length; row++)
    	{
    		for(int col = 0; col < this.level[0].length; col++)
    		{
    			this.level[row][col] = MazeObstacles.FUTURE_SAFE;
    		}
    	}
    }
    */
    
    public int GetMazeObstacleTop(int row)
    {
    	return (this.MazeObstacleSize * row);
    }
    
	public int GetMazeObstacleBottom(int row)
    {
    	return ((this.MazeObstacleSize * row) + this.MazeObstacleSize);    	
    }
    
    public int GetMazeObstacleLeft(int col)
    {
    	return (this.MazeObstacleSize * col);    	
    }
    
    public int GetMazeObstacleRight(int col)
    {
    	return ((this.MazeObstacleSize * col) + this.MazeObstacleSize);
    }
    
    // Receive a position and returns 
    // True if in that position there is a wall,
    // else returns False.
    public boolean IsWallAt(int row, int col)
    {
    	return (this.level[row][col] == MazeObstacles.WALL);
    }
    
    // Receive a position and 
    // returns MazeObstacle at that position.
    public MazeObstacles MazeObstacleAt(int row, int col)
    {
    	return this.level[row][col];
    }
    
    public boolean CheckMonsterTouch(int row, int col, Monster monster)
    {
    	
    	if(
			IsTouchTop(row, col, monster) || IsTouchBottom(row, col, monster)
			||
			IsTouchLeft(row, col, monster) || IsTouchRight(row, col, monster)
		  )
    	{
    		
    		return true;
    	}
    	return false;
    }
    
    private boolean IsTouchTop(int row, int col, Monster monster)
    {
    	
     return
    		 (
    		    (
        		monster.getLeft()  <=  this.GetMazeObstacleRight(col)
        		&&
        		monster.getLeft()  >=  this.GetMazeObstacleLeft(col)
        		||
        		monster.getRight() <=  this.GetMazeObstacleRight(col)
        		&&
        		monster.getRight() >=  this.GetMazeObstacleLeft(col)
        		)
        		&&
        		(
        		monster.getTop()   == this.GetMazeObstacleBottom(row)	
    			)
    		  );
    }
    
    private boolean IsTouchBottom(int row, int col, Monster monster)
    {
    	
     return
    		 (
    		    (
        		monster.getLeft()  <=  this.GetMazeObstacleRight(col)
        		&&
        		monster.getLeft()  >=  this.GetMazeObstacleLeft(col)
        		||
        		monster.getRight() <=  this.GetMazeObstacleRight(col)
        		&&
        		monster.getRight() >=  this.GetMazeObstacleLeft(col)
        		)
        		&&
        		(
        		monster.getBottom() == this.GetMazeObstacleTop(row)	
    			)
    		  );
    }
    
    private boolean IsTouchLeft(int row, int col, Monster monster)
    {
    	return
    		 (
    		    (
        		monster.getBottom()  <=  this.GetMazeObstacleBottom(row)
        		&&
        		monster.getBottom()  >=  this.GetMazeObstacleTop(row)
        		||
        		monster.getTop() <=  this.GetMazeObstacleBottom(row)
        		&&
        		monster.getTop() >=  this.GetMazeObstacleTop(row)
        		)
        		&&
        		(
        		monster.getLeft()   == this.GetMazeObstacleRight(col)
    			)
    		  );
    }

    private boolean IsTouchRight(int row, int col, Monster monster)
    {
        return
       		 (
       		    (
           		monster.getBottom()  <=  this.GetMazeObstacleBottom(row)
           		&&
           		monster.getBottom()  >=  this.GetMazeObstacleTop(row)
           		||
           		monster.getTop() <=  this.GetMazeObstacleBottom(row)
           		&&
           		monster.getTop() >=  this.GetMazeObstacleTop(row)
           		)
           		&&
           		(
           		monster.getRight()   == this.GetMazeObstacleLeft(col)
       			)
       		  );
    }

}
