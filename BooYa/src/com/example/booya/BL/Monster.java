package com.example.booya.BL;

import com.example.booya.MazeGameActivity;





public class Monster extends MovableObject
{

	//region members
	
	//region finals
	

	public static final float MONSTER_SIZE = (float)((0.85f) * GameLevel.MazeObstacleSize);
	
	
	//endregion
	
	
	private int m_nScreenWidth = MazeGameActivity.screenWidth;
	private int m_nScreenHeight = MazeGameActivity.screenHeight;
	
	
	//endregion
	
	//region Properties
	
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
