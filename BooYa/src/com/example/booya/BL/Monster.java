package com.example.booya.BL;

import android.graphics.PointF;
import com.example.booya.MazeGameActivity;

public class Monster extends MovableObject
{

	//region members
	
	//region finals

	private final float SIZE;
	
	//endregion


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
	
	public Monster(float startX, float startY, float obstacleSize)
	{
		x = startX;
		y = startY;
        SIZE = ((0.70f) * obstacleSize);
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
		return x + SIZE;
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return y - SIZE;
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
		x = xPos;
		y = yPos;
	}

    public float getSIZE() {
        return SIZE;
    }

    //endregion
	
}
