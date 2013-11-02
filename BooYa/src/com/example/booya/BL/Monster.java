package com.example.booya.BL;

public class Monster extends MovableObject
{

	//region members
	
	//region finals
	

	public static final int MONSTER_HEIGHT = 10;
	public static final int MONSTER_WIDTH = 10;
	
	
	//endregion
	
	private int m_screenWidth = 500;
	private int m_screenHeight = 500;
	
	
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
	
	public Monster(int startX, int startY, int screenWidth, int screenHeight)
	{
		this.x = startX;
		this.y = startY;
		this.m_screenWidth = screenWidth;
		this.m_screenHeight = screenHeight;
	}
	
	//endregion
	
	//region Methods
	
	@Override
	public float getLeft() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public float getRight() {
		// TODO Auto-generated method stub
		return x + MONSTER_WIDTH;
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public float getBottom() {
		// TODO Auto-generated method stub
		return y + MONSTER_HEIGHT;
	}
	

    public boolean canMoveLeft()
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
       
    public boolean canMoveRight()
    {
    	if(getRight() < m_screenWidth)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    	
    }
    
    public boolean canMoveUp()
    {
    	if(getTop() < m_screenHeight)
    	{
			return true;
    	}
    	else
    	{
    		//game over - the user touches the Wall
    		return false;
    	}
    }
    
    public boolean canMoveDown()
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
    public boolean checkMove()
    {
    	return(canMoveDown() && canMoveUp() && canMoveLeft() && canMoveRight());
    }
	@Override
	public boolean move(float xPos, float yPos) 
	{
		this.x = xPos;
		this.y = yPos;
		return checkMove();
	}

	//endregion
	
}
