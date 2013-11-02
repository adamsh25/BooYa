package com.example.booya.BL;

import com.example.booya.MazeGameActivity;





public class Monster extends MovableObject
{

	//region members
	
	//region finals
	

	public static final int MONSTER_HEIGHT = (int)((0.035) * MazeGameActivity.screenHeight);
	public static final int MONSTER_WIDTH = (int)((0.045) * MazeGameActivity.screenWidth);
	
	
	//endregion
	
	
	private int m_nScreenWidth = MazeGameActivity.screenWidth;
	private int m_nScreenHeight = MazeGameActivity.screenHeight;
	
	
	//endregion
	
	//region Properties
	
/*	public  get()

	{
		return (this.);
	}
	
	public void set(float )
	{
		this. = 
	}
	*/
	
	//endregion
		
	//region C'tor
	
	public Monster(float startX, float startY, int screenWidth, int screenHeight)
	{
		this.x = startX;
		this.y = startY;
		this.m_nScreenWidth = screenWidth;
		this.m_nScreenHeight = screenHeight;
	}
	
	//endregion
	
	//region Methods
	
	@Override
	public synchronized float getLeft() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public synchronized float getRight() {
		// TODO Auto-generated method stub
		return x + MONSTER_WIDTH;
	}

	@Override
	public synchronized float getTop() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public synchronized float getBottom() {
		// TODO Auto-generated method stub
		return y - MONSTER_HEIGHT;
	}
	

    public synchronized boolean canMoveLeft()
    {
    	if(getLeft() > 0)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    }
       
    public synchronized boolean canMoveRight()
    {
    	if(getRight() < m_nScreenWidth)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    	
    }
    
    public synchronized boolean canMoveUp()
    {
    	if(getTop() < m_nScreenHeight)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    }
    
    public synchronized boolean canMoveDown()
    {
    	if(getBottom() > 0)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    }
    public synchronized boolean checkMove()
    {
    	return(canMoveDown() && canMoveUp() && canMoveLeft() && canMoveRight());
    }
	@Override
	public synchronized boolean move(float xPos, float yPos) 
	{
		this.x = xPos;
		this.y = yPos;
		return checkMove();
	}

	//endregion
	
}
