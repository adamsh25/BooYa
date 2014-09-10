package com.example.booya.UI.Views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class TimerWheelView extends View
{
	//Sizes (with defaults)
	private int layout_height = 0;
	private int layout_width = 0;
    //private int _screenSize;
	private int barLength;
	private int barWidth;
	
	//Padding (with defaults)
	private int paddingTop;
	private int paddingBottom = 0;
	private int paddingLeft;
	private int paddingRight = 0;
	
	//Colors (with defaults)
	private int barColor = 0xAA000000;
	private int circleColor = 0x00000000;

	//Paints
	private Paint barPaint = new Paint();
	private Paint circlePaint = new Paint();
	private Paint textPaint = new Paint();

	//Rectangles
	private RectF circleBounds = new RectF();

	float progress = 0;

	public TimerWheelView(Context context, float screenSize)
	{
		super(context);
        barLength = (int) (screenSize * 0.0001);
        barWidth = (int)  (screenSize * 0.08);
        paddingTop = (int)  (screenSize * 0.05);
        paddingLeft = (int)  (screenSize * 0.8);
        layout_height = this.layout_width = (int)screenSize;
        setupBounds();
        setupPaints();
	}
	
	private void setupPaints() 
	{
		barPaint.setColor(Color.RED);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Style.FILL_AND_STROKE);
        barPaint.setStrokeWidth(barWidth);
        
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL);
        
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
	}


	private void setupBounds() 
	{
		int minValue = Math.min(layout_width, layout_height);
		
		int xOffset = layout_width - minValue;
		int yOffset = layout_height - minValue;
		
		paddingTop += (yOffset / 2);
        paddingBottom += (yOffset / 2);
        paddingLeft += (xOffset / 2);
        paddingRight += (xOffset / 2);
		
		
		circleBounds = new RectF(paddingLeft,
				paddingTop,
				paddingLeft + barWidth,
				paddingTop + barWidth);
	}
	
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);

		canvas.drawText("score : " + ((int)(1000-(progress * (1000f/360f)))), paddingLeft * 0.2f, paddingTop, textPaint);
		
		if(progress <= 180)
		{
			canvas.drawArc(circleBounds, -90, progress, false, barPaint);
		}
		else
		{
			if(progress-180+1 <=180)
			{
				canvas.drawArc(circleBounds, -90, 184, false, barPaint);
				canvas.drawArc(circleBounds, -270, progress-180, false, barPaint);
			}
			else
			{
				canvas.drawArc(circleBounds, -90, 360, false, barPaint);
			}
		}
	}

    /**
	 * Reset the count (in increment mode)
	 */
	public void resetCount()
	{
		progress = 0;
		invalidate();
	}

	public boolean incrementProgress()
	{
		progress += 6;
        invalidate();
        if (progress < 360) {
            return true;
        }
        else {
            return false;
        }
	}
}
