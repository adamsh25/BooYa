package com.example.booya.BL;

import com.example.booya.MazeGameActivity;

public class Monster extends MovableObject
{

	//region members
	
	//region finals
	

	public static final float MONSTER_SIZE = (float)((0.70f) * GameLevel.MAZE_OBSTACLE_SIZE);
	
	
	//endregion
	
	public static float X_OFFSET = 0;
	public static float Y_OFFSET = 0;
	public static final float RADIUS = Math.min(MazeGameActivity.screenHeight, 
			MazeGameActivity.screenWidth) * 0.15f;
										
	
	//endregion
	
	//region Properties
	
/*	public float getXOffset()
	{
		return(this.f_XOffset);
	}
	
	public void setXOffset(float xOffset)
	{
		this.f_XOffset = xOffset;
	}
	
	public float getYOffset()
	{
		return(this.f_YOffset);
	}
	
	
	public void setYOffset(float yOffset)
	{
		this.f_YOffset = yOffset;
	}*/
	//endregion
		
	//region C'tor
	
	public Monster(float startX, float startY)
	{
		this.x = startX;
		this.y = startY;
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
		return x + MONSTER_SIZE;
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return y - MONSTER_SIZE;
	}

	@Override
	public float getBottom() 
	{
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public synchronized void move(float xPos, float yPos) 
	{
		this.x = xPos;
		this.y = yPos;
	}

	//endregion
	
}
