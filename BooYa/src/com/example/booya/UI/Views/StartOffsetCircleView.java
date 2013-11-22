package com.example.booya.UI.Views;

import com.example.booya.BL.Monster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ViewConstructor")
public class StartOffsetCircleView  extends View
{

	//region members
	private Paint m_circlePaint; // The Wall Design Member

	private PointF pntf_StartPos;
	
	public static boolean Draw = true;
	//endregion
	
	
	//region Properties
	
	public static PointF CircleCenter = null;
	
	//endregion
	
	//region C'tors
	
	public StartOffsetCircleView(Context context, PointF pntf_StartPos)
	{
		super(context);
		this.pntf_StartPos = new PointF(pntf_StartPos.x + (Monster.MONSTER_SIZE/2),
				pntf_StartPos.y - (Monster.MONSTER_SIZE/2));
		CircleCenter = this.pntf_StartPos;
	}
	
	//endregion

	//region Events
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		if(Draw)
		{
				super.onDraw(canvas);
		
				try
				{
		
					canvas.drawCircle(
							this.pntf_StartPos.x , this.pntf_StartPos.y ,
							Math.abs(Monster.RADIUS), getCirclePaint());
					
				} 
				catch (Exception e)
				{
					
					e.printStackTrace();
				}
		}
	}

	protected Paint getCirclePaint() 
	{
	
		if(m_circlePaint == null)
		{
			m_circlePaint = new Paint();
			m_circlePaint.setColor(Color.WHITE);
			m_circlePaint.setStrokeWidth(5);
			m_circlePaint.setStyle(Style.STROKE);
			m_circlePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));

		}
		
		return (m_circlePaint);
		
	}
	
	//endregion
	
	//region Methods
	
	
	
	//endregion
	
	
	

}
