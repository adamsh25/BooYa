package com.example.booya.BL;

import android.graphics.Point;

public class Monster extends MovableObject
{

	//region members
	
	//region finals
	

	private final int HEIGHT = 15;
	private final int WIDTH = 15;
	
	
	//endregion
	
	private int m_screenWidth = 500;
	private int m_screenHeight = 500;
	private float m_monsterDestination;
	
	
	//endregion
	
	//region Properties
	
	public float getMonsterDestination()
	{
		return this.m_monsterDestination;
	}
	
	public void setMonsterDestinantion(float monsterDestination)
	{
		this.m_monsterDestination = monsterDestination;
	}
	
	//endregion
		
	//region C'tor
	
	public Monster(int startX, int startY, int screenWidth, int screenHeight)
	{
		this.x = startX;
		this.y = startY;
		this.m_screenWidth = screenWidth;
		this.m_screenHeight = screenHeight;
		this.m_monsterDestination = 1;
	}
	
	//endregion
	
	//region Methods
	
	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getRight() {
		// TODO Auto-generated method stub
		return x + WIDTH;
	}

	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public int getBottom() {
		// TODO Auto-generated method stub
		return y + HEIGHT;
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
	public boolean move(Point p) 
	{
		this.x = p.x;
		this.y = p.y;
		return checkMove();
	}

	//endregion
	
}
