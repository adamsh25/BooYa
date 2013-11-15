package com.example.booya.BL;

import android.graphics.Point;

/*
 * This base class defines an abstract object which has central location (x,y),
 * some dimensions which can be determined by calling the methods
 * getLeft(), getRight(), getTop(),getBottom()
 */
public abstract class MovableObject 
{
		protected float x; //current horizontal position of the center of the object
	    protected float y; //current vertical position of the center of the object
	   
	    // Return the center horizontal position of the object
	    public float getX()
	    {
	    	return x;
	    }

	    // Return the center vertical position of the object
	    public float getY()
	    {
	        return y;
	    }

	    //should return the left border of the object
	    public abstract float getLeft();
	    
	    //should return the right border of the object
	    public abstract float getRight();
	    
	    //should return the top border of the object
	    public abstract float getTop();
	    
	    //should return the bottom border of the object
	    public abstract float getBottom();
	    
	    //This method should move the object
	    public abstract void move(float xPos, float yPos);
	   

	    
	   

}
