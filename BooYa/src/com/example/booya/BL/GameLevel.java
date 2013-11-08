package com.example.booya.BL;

import android.graphics.PointF;

import com.example.booya.MazeGameActivity;

/**
 * @author adam
 *
 */
public abstract class GameLevel 
{
	
	//region members
	
    // Space from the top screen from which the Walls will be drawn 
    public final float TOP_PADDING = ((0.125f) * MazeGameActivity.screenHeight);
 
    // Wall Width and Height - Preferred To Be A Square
    public final float MazeObstacleSize = ((0.1f) * MazeGameActivity.screenWidth);

    // Max number of Walls rows and columns
    public final int MaxRows = 12;
    public final int MaxCols = 10;
    
    protected abstract int GetStartPosX();
    protected abstract int GetStartPosY();
    
    protected int startPosY;

    // The Maze Obstacles matrix.
    protected MazeObstacles[][] level;
    
    //endregion
    
    //region C'tor
    
    /**
     * Empty C'tor
     * Creates An Empty Array Of MazeObstacles
     */
    public GameLevel()
    {
        this.level = new MazeObstacles[this.MaxRows][this.MaxCols];
    }
    
    //endregion

    //region Properties
    
    
    /**
     * Get The Maze Obstacle Top Place By Row Place In Pixels
     * @param row - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
    public float GetMazeObstacleTop(int row)
    {
    	return ((this.MazeObstacleSize * row) + TOP_PADDING);
    }
    
    /**
     * Get The Maze Obstacle Bottom Place By Row Place In Pixels
     * @param row - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
	public float GetMazeObstacleBottom(int row)
    {
		// To Get The Bottom Place We Need To Add The Obstacle Size
		//  	And Not To Subtract It, Because We Get From The Screen 
		//											The Y Pixel Place Reversed. 
    	return (GetMazeObstacleTop(row) + this.MazeObstacleSize);    	
    }
    
    /**
     * Get The Maze Obstacle Left Place By Column Place In Pixels
     * @param col - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
    public float GetMazeObstacleLeft(int col)
    {
    	return (this.MazeObstacleSize * col);    	
    }
    
    /**
     * Get The Maze Obstacle Right Place By Column Place In Pixels
     * @param col - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
    public float GetMazeObstacleRight(int col)
    {
    	return ((this.MazeObstacleSize * col) + this.MazeObstacleSize);
    }
    
    //endregion
    
    //region Methods
    
    // We Give An Array Of Numbers To Draw Each Level Easily
    // But For Easy Work We Will Use An Enumerable Array Of Maze Obstacles
    public void UpdateMazeObstaclesMatrix(int[][] obstacles)
    {
    	// runs through matrix rows
    	for(int row = 0; row < this.MaxRows; row++)
    	{
    		// runs through matrix columns
    		for(int col = 0; col < this.MaxCols; col++)
    		{
    			// Construct Level Obstacle Type Matrix By Level Integer Matrix. 
    			this.level[row][col] = MazeObstacles.GetMazeObstacleByNumber(obstacles[row][col]);
    		}
    	}
    	
    }

    // 
    /**
     * Get The Maze Obstacle That The Monster Has Touched.
     * @param monster - To Get Is Place In The Screen.
     * @return The Touched Maze Obstacle Name.
     */
    public MazeObstacles TouchedMazeObstacle(Monster monster)
    {

    	MazeObstacles x = MazeObstacles.SAFE;
    	
    	
/*    	for(int row = 0; row < this.MaxRows; row++)
    	{
    		for(int col = 0; col < this.MaxCols; col++)
    		{
    			// checks if the monster is inside an obstacle
    			if(isMonsterInsideObstacle(row, col, monster))
    			{
    				// returns in witch obstacle the monster is.
    				x = MazeObstacleAt(row, col);
    				return (x);
				}

    		}
    	}*/
    	
    	// only if the monster is between different boundaries.
    	for(int row = 0; row < this.MaxRows; row++)
    	{
    		for(int col = 0; col < this.MaxCols; col++)
    		{
    			// checks if monster touches the edge of a boundary
    			if(isMonsterTouchedObstacle(row, col, monster))
    			{
    					x = MazeObstacleAt(row, col);
    					// if the monster touches the edge of a boundary
    					return (x);
    			}

    		}
    	}
    	
    	return (x);
    }
    

    /**
     * Checks If There Is A Wall At The row, column Position
     * @param row - Y Position
     * @param col - X Position
     * @return boolean True/False
     */
    public boolean IsWallAt(int row, int col)
    {
    	return (this.level[row][col] == MazeObstacles.WALL);
    }
    

    /**
     * Receive a position and 
     * returns MazeObstacle at that position.
     * @param row
     * @param col
     * @return
     */
    public MazeObstacles MazeObstacleAt(int row, int col)
    {
    	return (this.level[row][col]);
    }
    
    
    /**
     * Checks If The Monster Has Touched The Obstacle.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    public boolean isMonsterTouchedObstacle(int row, int col, Monster monster)
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
    
    
    /**
     * Checks If The Monster Has Touched The Obstacle Top Side.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    private boolean IsTouchTop(int row, int col, Monster monster)
    {
    		 if
    		 (
    		    (
	        		(monster.getLeft()  <=  this.GetMazeObstacleRight(col)
	        		&&
	          		 monster.getLeft()  >=  this.GetMazeObstacleLeft(col))
				||
	        		(monster.getRight() <=  this.GetMazeObstacleRight(col)
	        		&&
	        		 monster.getRight() >=  this.GetMazeObstacleLeft(col))
        		)
        		&&
        		(
        				monster.getBottom()         == this.GetMazeObstacleTop(row)
    	        		&& MazeObstacleAt(row, col) == MazeObstacles.WALL 
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
    
    
    /**
     * Checks If The Monster Has Touched The Obstacle Bottom Side.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    private boolean IsTouchBottom(int row, int col, Monster monster)
    {
    	if
    		 (
    		    (
	        		(monster.getLeft()  <=  this.GetMazeObstacleRight(col)
	        		&&
	        		 monster.getLeft()  >=  this.GetMazeObstacleLeft(col))
        		||
	        		(monster.getRight() <=  this.GetMazeObstacleRight(col)
	        		&&
        			 monster.getRight() >=  this.GetMazeObstacleLeft(col))
        		)
        		&&
        		(
	        		monster.getTop() == this.GetMazeObstacleBottom(row)	
	        		&& MazeObstacleAt(row, col) == MazeObstacles.WALL
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
    
    
    /**
     * Checks If The Monster Has Touched The Obstacle Right Side.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    private boolean IsTouchRight(int row, int col, Monster monster)
    {
    	if
    		 (
    		    (
	        		(monster.getBottom()  <=  this.GetMazeObstacleBottom(row)
	        		&&
	        		 monster.getBottom()  >=  this.GetMazeObstacleTop(row))
        		||
	        		(monster.getTop()     <=  this.GetMazeObstacleBottom(row)
	        		&&
	        		 monster.getTop()     >=  this.GetMazeObstacleTop(row))
        		)
        		&&
        		(
	        		monster.getLeft()           == this.GetMazeObstacleRight(col)
	        		&& MazeObstacleAt(row, col) == MazeObstacles.WALL
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

    
    /**
     * Checks If The Monster Has Touched The Obstacle Left Side.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    private boolean IsTouchLeft(int row, int col, Monster monster)
    {
        if
       		 (
       		    (
	           		(monster.getBottom()  <=  this.GetMazeObstacleBottom(row)
	           		&&
	           		 monster.getBottom()  >=  this.GetMazeObstacleTop(row))
           		||
	           		(monster.getTop() 	 <=  this.GetMazeObstacleBottom(row)
	           		&&
	           		 monster.getTop()     >=  this.GetMazeObstacleTop(row))
           		)
           		&&
           		(
	           		monster.getRight()   ==  this.GetMazeObstacleLeft(col)
	           		&& MazeObstacleAt(row, col) == MazeObstacles.WALL
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
    
    
    /**
     * Checks If The Monster is Inside The Obstacle.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    public boolean isMonsterInsideObstacle(int row, int col, Monster monster)
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
    public PointF GetStartPosition()
    {
    	float x = (this.GetMazeObstacleLeft(GetStartPosX()) + this.GetMazeObstacleRight(GetStartPosX()))/2;
    	float y = (this.GetMazeObstacleTop(GetStartPosY()) + this.GetMazeObstacleBottom(GetStartPosY()))/2;
    	return (new PointF(x,y));
    }
    
    
		

//endregion

}
