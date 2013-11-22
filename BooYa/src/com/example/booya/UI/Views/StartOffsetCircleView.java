package com.example.booya.UI.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.DragEvent;
import android.view.View;

@SuppressLint("ViewConstructor")
public class StartOffsetCircleView  extends View
{

	//region members
	
	private PointF pntf_StartPos;
	
	//endregion
	
	//region C'tors
	
	public StartOffsetCircleView(Context context, PointF pntf_StartPos)
	{
		super(context);
		this.pntf_StartPos = pntf_StartPos;
	}
	
	//endregion

	//region Events
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		
		
		
	}
	
	//endregion
	
	//region Methods
	
	
	
	//endregion
	
	
	

}
