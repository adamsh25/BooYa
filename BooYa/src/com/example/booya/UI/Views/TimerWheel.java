package com.example.booya.UI.Views;
import com.example.booya.BL.MazeLevel;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class TimerWheel extends View 
{
	
	//Sizes (with defaults)
	private int layout_height = 0;
	private int layout_width = 0;
    private int _screenSize;
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
	
	//Animation
	//The amount of pixels to move the bar by on each draw
	

	float progress = 0;


    /**
	 * The constructor for the ProgressWheel
	 * @param context
	 * @param attrs
	 */
	public TimerWheel(Context context, float screenSize)
	{
		super(context);
        this.barLength = (int) (screenSize * 0.0001);
        this.barWidth = (int)  (screenSize * 0.08);
        this.paddingTop = (int)  (screenSize * 0.05);
        this.paddingLeft = (int)  (screenSize * 0.8);
        this.layout_height = this.layout_width = (int)screenSize;
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
		
		paddingTop = this.getPaddingTop() + (yOffset / 2);
	    	paddingBottom = this.getPaddingBottom() + (yOffset / 2);
	    	paddingLeft = this.getPaddingLeft() + (xOffset / 2);
	    	paddingRight = this.getPaddingRight() + (xOffset / 2);
		
		
		circleBounds = new RectF(paddingLeft,
				paddingTop,
				paddingLeft + barWidth,
				paddingTop + barWidth);
		
	}


	
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
            
		setupBounds();
		setupPaints();
		canvas.drawText("score : " + ((int)(1000-(progress * (1000f/360f)))), paddingLeft * 0.2f, paddingTop, textPaint);
		
		if(progress <=180)
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

	
	public float getProgress()
	{
		return (progress);
	}

	public void incrementProgress() 
	{
		progress = progress + 6;
	}


	public void setProgress(int i) 
	{
	    progress = i;
	}
	
	public int getBarLength()
	{
		return barLength;
	}

	public void setBarLength(int barLength)
	{
		this.barLength = barLength;
	}

	public int getBarWidth()
	{
		return barWidth;
	}

	public void setBarWidth(int barWidth) 
	{
		this.barWidth = barWidth;
	}


	public int getPaddingTop()
	{
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) 
	{
		this.paddingTop = paddingTop;
	}

	public int getPaddingBottom()
	{
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) 
	{
		this.paddingBottom = paddingBottom;
	}

	public int getPaddingLeft() 
	{
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft)
	{
		this.paddingLeft = paddingLeft;
	}

	public int getPaddingRight()
	{
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight)
	{
		this.paddingRight = paddingRight;
	}

	public int getBarColor()
	{
		return barColor;
	}

	public void setBarColor(int barColor)
	{
		this.barColor = barColor;
	}

	public int getCircleColor()
	{
		return circleColor;
	}

	public void setCircleColor(int circleColor) 
	{
		this.circleColor = circleColor;
	}

}
