package com.example.booya.BL;

import com.example.booya.UI.Activities.MazeGameActivity;

public abstract class GameLevel 
{
	//region members
	
    // Space from the top screen from which the Walls will be drawn 
    public final float TOP_PADDING = ((0.2f) * MazeGameActivity.screenHeight);
 
    // Wall Width and Height
    public final float MazeObstacleSize = ((0.1f) * MazeGameActivity.screenWidth);

    // Max number of Walls rows and columns
    public final int MaxRows = 10;

    public final int MaxCols = 10;

    // The Maze Obstacles array.
    protected MazeObstacles[][] level;
    
    //endregion
    
    //region C'tor
    
    // Constructor just creates an empty array of MazeObstacles
    public GameLevel()
    {
        this.level = new MazeObstacles[this.MaxRows][this.MaxCols];
    }
    
    //endregion

    //region Properties
    
    // Get The Maze Obstacle Top Place  
    public float GetMazeObstacleTop(int row)
    {
    	return ((this.MazeObstacleSize * row) + TOP_PADDING);
    }
    
    // Get The Maze Obstacle Bottom Place
	public float GetMazeObstacleBottom(int row)
    {
    	return (GetMazeObstacleTop(row) - this.MazeObstacleSize);    	
    }
    
	// Get The Maze Obstacle Left Place
    public float GetMazeObstacleLeft(int col)
    {
    	return (this.MazeObstacleSize * col);    	
    }
    
    // Get The Maze Obstacle Right Place
    public float GetMazeObstacleRight(int col)
    {
    	return ((this.MazeObstacleSize * col) + this.MazeObstacleSize);
    }
    
    //endregion
    
    //region Methods
    
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

    // 
    public synchronized MazeObstacles TouchedMazeObstacle(Monster monster)
    {

    	MazeObstacles x = MazeObstacles.SAFE;
    	for(int row = 0; row < this.level.length; row++)
    	{
    		for(int col = 0; col < this.level[0].length; col++)
    		{
    			if(IsMonsterInsideBoundaries(row, col, monster))
    			{
    				x = MazeObstacleAt(row, col);
    				if(x!= MazeObstacles.SAFE)
    				{
    					return (x);
    				}
				}
/*    			if(isMonsterTouchedObstacle(row, col, monster))
    			{
    					return MazeObstacleAt(row, col);
    			}*/
    		}
    	}
    	return (x);
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
    	return (this.level[row][col]);
    }
    
    // Return True If The Monster Has Touched An Obstacle
    public synchronized boolean isMonsterTouchedObstacle(int row, int col, Monster monster)
    {
    	if(
			IsTouchTop(row, col, monster) || IsTouchBottom(row, col, monster)
			||
			IsTouchLeft(row, col, monster) || IsTouchRight(row, col, monster)
		  )
    	{
    		return (true);
    	}
    	else
    	{
    		return (false);
    	}
    }
    
    // Return True If The Monster Has Touched The Obstacle From His Top
    private boolean IsTouchTop(int row, int col, Monster monster)
    {
    		 if
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
    		  )
    		 {
    			 return (true);
    		 }
    		 else
    		 {
    			 return (false);
    		 }
    }
    
    // Return True If The Monster Has Touched The Obstacle From His Bottom
    private boolean IsTouchBottom(int row, int col, Monster monster)
    {
    	if
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
    		  )
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    // Return True If The Monster Has Touched The Obstacle From His Left
    private boolean IsTouchLeft(int row, int col, Monster monster)
    {
    	if
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
    		  )
    	{
    		return (true);
    	}
    	else
    	{
    		return (false);
    	}
    }

    // Return True If The Monster Has Touched The Obstacle From His Right
    private boolean IsTouchRight(int row, int col, Monster monster)
    {
        if
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
       		  )
        {
        	return (true);
        }
        else
        {
        	return (false);
        }
    }
    
    // Return True If The Monster 
    private boolean IsMonsterInsideBoundaries(int row, int col, Monster monster)
    {
        if
       		 (
           		monster.getLeft()    >  this.GetMazeObstacleLeft(col)
           	    &&
           		monster.getRight()   <  this.GetMazeObstacleRight(col)
       			&&
           		monster.getBottom()  >  this.GetMazeObstacleBottom(row)
           		&&
           		monster.getTop()     <  this.GetMazeObstacleTop(row)
           		
       		  )
        {
        	return (true);
        	
        }
        else
        {
        	return (false);
        }
    }

//endregion
}
