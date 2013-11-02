package com.example.booya.BL;

public abstract class GameLevel 
{
    // Space from the top screen from which the Walls will be drawn
    public final int TopPadding = 0;
 
    // Space between each Wall
    public final int MazeObstaclePadding = 50;
 
    // Wall Width and Height
    public final int MazeObstacleSize = 25;

    // Max number of Walls rows and columns
    public final int MaxRows = 10;

    public final int MaxCols = 10;

    // The Maze Obstacles array.
    protected MazeObstacles[][] level;
    
    // Constructor just creates an empty array of MazeObstacles
    public GameLevel()
    {
        this.level = new MazeObstacles[this.MaxRows][this.MaxCols];
    }
    
    // We Give An Array Of Numbers To Init Each Level Easily
    // But For Easy Work We Will Use An Enumerable Array Of Maze Obstacles
    public void UpdateMazeObstaclesMatrix(int[][] obstacles)
    {
    	for(int row = 0; row < this.level.length; row++)
    	{
    		for(int col = 0; col < this.level[0].length; col++)
    		{
    			this.level[row][col] = MazeObstacles.GetMazeObstacleByNumber(obstacles[row][col]);
    		}
    	}
    	
    }
    
    public MazeObstacles TouchedMazeObstacle(Monster monster)
    {
    	for(int row = 0; row < this.level.length; row++)
    	{
    		for(int col = 0; col < this.level[0].length; col++)
    		{
    			if(CheckMonsterTouch(row, col, monster))
    			{
    				if(MazeObstacleAt(row, col) == MazeObstacles.WALL)
    				{
    					return MazeObstacleAt(row, col);
    				}
    			}
    		}
    	}
    	return (MazeObstacles.SAFE);
    }
    
    // Get The Maze Obstacle Top Place  
    public int GetMazeObstacleTop(int row)
    {
    	return (this.MazeObstacleSize * row);
    }
    
    // Get The Maze Obstacle Bottom Place
	public int GetMazeObstacleBottom(int row)
    {
    	return ((this.MazeObstacleSize * row) + this.MazeObstacleSize);    	
    }
    
	// Get The Maze Obstacle Left Place
    public int GetMazeObstacleLeft(int col)
    {
    	return (this.MazeObstacleSize * col);    	
    }
    
    // Get The Maze Obstacle Right Place
    public int GetMazeObstacleRight(int col)
    {
    	return ((this.MazeObstacleSize * col) + this.MazeObstacleSize);
    }
    
    // Receive A Position And Returns 
    // True If In That Position There Is An Wall,
    // Else Returns False.
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
    
    // Return True If The Monster Has Touched An Obstacle
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
    
    // Return True If The Monster Has Touched The Obstacle From His Top
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
    
    // Return True If The Monster Has Touched The Obstacle From His Bottom
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
    
    // Return True If The Monster Has Touched The Obstacle From His Left
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

    // Return True If The Monster Has Touched The Obstacle From His Right
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
