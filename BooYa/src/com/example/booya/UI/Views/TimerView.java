/*package com.example.booya.UI.Views;

import com.example.booya.BL.Monster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.drawable.shapes.PathShape;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

@SuppressLint("ViewConstructor")
public class TimerView  extends View
{
	
	//region members
	private Paint m_circlePaint; // The Wall Design Member

    private int layout_height = 0;
    private int layout_width = 0;
    private int fullRadius = 100;
    private int circleRadius = 80;
    private int barLength = 60;
	
	private PointF pntf_StartPos;
	
	//endregion
	
	//region Properties
	
	public static PointF CircleCenter = null;
	
	//endregion
	
	//region C'tors
	
	public TimerView(Context context, PointF pntf_StartPos)
	{
		super(context);
		this.pntf_StartPos = new PointF(pntf_StartPos.x + (Monster.MONSTER_SIZE/2),
				pntf_StartPos.y - (Monster.MONSTER_SIZE/2));
		CircleCenter = this.pntf_StartPos;
	}
	
	//endregion

	//region Events
	
	 (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
				ProgressBar p = new ProgressBar(this.getContext());
				
				p.draw(canvas);
				
		
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

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		 layout_width = w;
		 layout_height = h;
	}
	
	
	 private void setupBounds() 
	 {
	 

    // Width should equal to Height, find the min value to steup the circle
     int minValue = Math.min(layout_width, layout_height);
     
     // Calc the Offset if needed
     int xOffset = layout_width - minValue;
     int yOffset = layout_height - minValue;
     
     // Add the offset
         paddingTop = this.getPaddingTop() + (yOffset / 2);
         paddingBottom = this.getPaddingBottom() + (yOffset / 2);
         paddingLeft = this.getPaddingLeft() + (xOffset / 2);
         paddingRight = this.getPaddingRight() + (xOffset / 2);
         
         rectBounds = new RectF(paddingLeft,
                 paddingTop,

                          this.getLayoutParams().width - paddingRight,
                            this.getLayoutParams().height - paddingBottom);

         circleBounds = new RectF(paddingLeft + barWidth,
              paddingTop + barWidth,
                        this.getLayoutParams().width - paddingRight - barWidth,
                         this.getLayoutParams().height - paddingBottom - barWidth);
         
         fullRadius = (this.getLayoutParams().width - paddingRight - barWidth)/2;

                circleRadius = (fullRadius - barWidth) + 1;
	 }
	
	
	protected Paint getCirclePaint() 
	{
	
		if(m_circlePaint == null)
		{
			m_circlePaint = new Paint();
			m_circlePaint.setColor(Color.RED);
			m_circlePaint.setStrokeWidth(5);

			//m_circlePaint.set
			m_circlePaint.setStyle(Style.FILL_AND_STROKE);
			
				
			m_circlePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));

		}
		
		return (m_circlePaint);
		
	}
	

	
	//endregion
	
	//region Methods
	
	public boolean IsTouchCircle(int x, int y)
	{
		double r = Math.sqrt(Math.pow((x-this.pntf_StartPos.x),2) + Math.pow((y-this.pntf_StartPos.y),2));
		if( r <= (1.2*Monster.RADIUS))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//endregion
	
	
	

}
*/