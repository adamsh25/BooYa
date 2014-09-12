package com.example.booya.UI.Views;

import android.view.MotionEvent;
import com.example.booya.BL.MazeLevel;
import com.example.booya.BL.Monster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.view.View;
import com.example.booya.MazeGameActivity;

@SuppressLint("ViewConstructor")
public class StartOffsetCircleView  extends View
{
    public final float RADIUS = Math.min(MazeGameActivity.screenHeight,
            MazeGameActivity.screenWidth) * 0.17f;

	//region members
	private Paint m_circlePaint; // The Wall Design Member

	private PointF _circleCenter;

    private Monster _monster;
	
	private boolean _shouldDraw;

    private float _xOffset = 0;
    private float _yOffset = 0;
	//endregion
	
	//region Properties
	
	public static PointF CircleCenter = null; //todo: delete
	
	//endregion
	
	//region C'tors
	
	public StartOffsetCircleView(Context context, MazeLevel level, Monster monster)
	{
		super(context);
        _monster = monster;
		_circleCenter = GetCircleCenter(level.getStartPosition());
		CircleCenter = _circleCenter;
        _shouldDraw = true;
	}
	
	//endregion

	//region Events
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
        super.onDraw(canvas);

		if(_shouldDraw)
		{
				try //todo: why?
				{
					canvas.drawCircle(
							this._circleCenter.x , this._circleCenter.y ,
							Math.abs(RADIUS), getCirclePaint());
					
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

			//m_circlePaint.set
			m_circlePaint.setStyle(Style.STROKE);
			m_circlePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));

		}
		
		return (m_circlePaint);
		
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!_shouldDraw) {
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        if (!IsTouchCircle(x, y)) {
            return true; // We tell we handled the touch event to prevent event bubbling up the view hierarchy
        }

        _yOffset = _circleCenter.y - y;
        _xOffset = _circleCenter.x - x;

        _shouldDraw = false;
        invalidate();
        return true;
    }

    //endregion
	
	//region Methods

    public void Redraw(MazeLevel level) {
        _circleCenter = GetCircleCenter(level.getStartPosition());
        CircleCenter = this._circleCenter;
        _shouldDraw = true;
        _xOffset = _yOffset = 0;
        invalidate();
    }
	
	public boolean IsTouchCircle(float x, float y)
	{
		double r = Math.sqrt(Math.pow((x - _circleCenter.x),2) + Math.pow((y - _circleCenter.y),2));
		if( r <= (1.2 * RADIUS)) //todo: what is this magic number?
		{
			return true;
		}
		else
		{
			return false;
		}
	}

    private PointF GetCircleCenter(PointF point) {
        return new PointF(point.x + (_monster.getSIZE() /2),
                point.y - (_monster.getSIZE() /2));
    }

    public float get_xOffset() {
        return _xOffset;
    }

    public float get_yOffset() {
        return _yOffset;
    }
	
	//endregion
}
