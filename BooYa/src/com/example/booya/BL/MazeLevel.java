package com.example.booya.BL;

import android.graphics.Point;
import android.graphics.PointF;

import com.example.booya.MazeGameActivity;

/**
 * @author adam
 *
 */
public class MazeLevel {
	//region members

    private Point _startPoint;

    // The Maze Obstacles matrix.
    protected MazeObstacles[][] _levelMatrix;
    
    // Wall Width and Height - Preferred To Be A Square
    private final float SCREEN_SIZE = Math.min(MazeGameActivity.mazeViewWidth, MazeGameActivity.mazeViewHeight); //todo: what does it mean?
    private float MIN_COL_ROW;
    public  float MAZE_OBSTACLE_SIZE;
    
    // Space from the top screen from which the Walls will be drawn 
    public final float TOP_PADDING = ((0.5f) *
    		Math.max((MazeGameActivity.mazeViewHeight - MazeGameActivity.mazeViewWidth), 0));
 
    // Space from the top screen from which the Walls will be drawn 
    public final float LEFT_PADDING =
    		Math.max((MazeGameActivity.mazeViewWidth - MazeGameActivity.mazeViewHeight), 0);

    public int getLevelMatrixRowsNum() {
        return _levelMatrix.length;
    }

    public int getLevelMatrixColsNum() {
        return _levelMatrix[0].length;
    }
    


    
    //endregion
    
    //region C'tor
    
    /**
     * Empty C'tor
     * Creates An Empty Array Of MazeObstacles
     */
    public MazeLevel(MazeObstacles[][] level, Point startingPos)
    {
        _levelMatrix = level;
        _startPoint = startingPos;
        MIN_COL_ROW = Math.min(getLevelMatrixRowsNum(), getLevelMatrixColsNum());
        MAZE_OBSTACLE_SIZE = SCREEN_SIZE / MIN_COL_ROW;
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
    	return ((MAZE_OBSTACLE_SIZE * row) + TOP_PADDING);
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
    	return (GetMazeObstacleTop(row) + MAZE_OBSTACLE_SIZE);
    }
    
    /**
     * Get The Maze Obstacle Left Place By Column Place In Pixels
     * @param col - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
    public float GetMazeObstacleLeft(int col)
    {
    	return (MAZE_OBSTACLE_SIZE * col) + LEFT_PADDING;
    }
    
    /**
     * Get The Maze Obstacle Right Place By Column Place In Pixels
     * @param col - The Row Place To Calculate The Obstacle Place.
     * @return The Top Place On The Screen In Pixel.
     */
    public float GetMazeObstacleRight(int col)
    {
    	return (GetMazeObstacleLeft(col) + MAZE_OBSTACLE_SIZE);
    }
    
    //endregion
    
    //region Methods

    // 
    /**
     * Get The Maze Obstacle That The Monster Has Touched.
     * @param monster - To Get Is Place In The Screen.
     * @return The Touched Maze Obstacle Name.
     */
    public MazeObstacles touchedMazeObstacle(Monster monster)
    {
    	// return SAFE Obstacle By Default.
    	MazeObstacles x = MazeObstacles.SAFE;
    	
    	
    	// Runs Through All The Maze Level Matrix
    	for(int row = 0; row < getLevelMatrixRowsNum(); row++)
    	{
    		for(int col = 0; col < getLevelMatrixColsNum(); col++)
    		{
    			// If The Monster Touches The Edge Of A "Dangerous Obstacle".
    			if(isMonsterTouchedObstacle(row, col, monster))
    			{
    					x = mazeObstacleAt(row, col);
    					if(x != MazeObstacles.SAFE)// Only If We Touched A Different
    											   //   Obstacle From The Safe Obstacle
    					{
    						// If The Monster Touches The Edge Of A "Dangerous Obstacle".
    						return (x);
    					}
    			}

    		}
    	}
    	
    	// Return Safe Obstacle Because No Other "Dangerous Obstacle" Has Been Touched.
    	return (x);
    }
    

    /**
     * Receive a position and 
     * returns MazeObstacle at that position.
     * @param row
     * @param col
     * @return
     */
    public MazeObstacles mazeObstacleAt(int row, int col)
    {
    	return (this._levelMatrix[row][col]);
    }
    
    
    /**
     * Checks If The Monster Has Touched The Obstacle.
     * @param row - the Y place.
     * @param col - the X place.
     * @param monster
     * @return boolean True\False
     */
    public  boolean isMonsterTouchedObstacle(int row, int col, Monster monster)
    {
    	
    	if(
			isTouchTop(row, col, monster) || isTouchBottom(row, col, monster)
			||
			isTouchLeft(row, col, monster) || isTouchRight(row, col, monster)
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
    private boolean isTouchTop(int row, int col, Monster monster)
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
    	        		monster.getBottom()  <= (this.GetMazeObstacleBottom(row))
    	        		&&
    	        		monster.getBottom()  >= (this.GetMazeObstacleTop(row))
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
    private boolean isTouchBottom(int row, int col, Monster monster)
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
	        		monster.getTop() >= (this.GetMazeObstacleBottom(row))
	        		&&
	        		monster.getTop() <= this.GetMazeObstacleTop(row)
	        		
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
    private boolean isTouchRight(int row, int col, Monster monster)
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
    	           		monster.getLeft()    >=  this.GetMazeObstacleLeft(col)
    	           		&& monster.getLeft() <=  (this.GetMazeObstacleRight(col))
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
    private boolean isTouchLeft(int row, int col, Monster monster)
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
           				
    	           		monster.getRight()    >=  (this.GetMazeObstacleLeft(col))
    	           		&& monster.getRight() <=  (this.GetMazeObstacleRight(col))
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
     * 
     * @return
     */
    public PointF getStartPosition() //todo: change to Point?
    {
    	float x = (this.GetMazeObstacleLeft(_startPoint.x) + this.GetMazeObstacleRight(_startPoint.x))/2;
    	float y = (this.GetMazeObstacleTop(_startPoint.y) + this.GetMazeObstacleBottom(_startPoint.y))/2;
    	return (new PointF(x,y));
    }

    public float getSCREEN_SIZE() {
        return SCREEN_SIZE;
    }


//endregion

    
    
    //region Not Yet In Use - Maybe We Will Need This Code  
    
    /**
        * Checks If The Monster is Inside The Obstacle.
        * @param row - the Y place.
        * @param col - the X place.
        * @param monster
        * @return boolean True\False
        */
    /*   public boolean isMonsterInsideObstacle(int row, int col, Monster monster)
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
       }*/
       
       //endregion
    
    
    
    
}
