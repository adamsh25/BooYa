package com.example.booya.UI.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MazeView extends FrameLayout
{
	
	//region members
	
	private MonsterView m_monsterView;
	private MazeLevelView m_levelView;
	private StartOffsetCircleView m_circleView;
	private TimerWheelView m_progressWheelView;
	SurfaceView m_dummyView;
	
	//endregion
	
	//region C'tor
	
	public MazeView(Context context, MazeLevelView levelView, MonsterView monsterView,
                    StartOffsetCircleView circleView, TimerWheelView progressWheelView)
	{
		super(context);

        m_levelView = levelView;
        m_monsterView = monsterView;
        m_circleView = circleView;
        m_progressWheelView = progressWheelView;

        addView(m_levelView);
        addView(m_monsterView);
        addView(m_circleView);
        addView(m_progressWheelView);

        //setFocusableInTouchMode(true);
	}

    //todo: delete
    public void setViews(MazeLevelView levelView, MonsterView monsterView,
                         StartOffsetCircleView circleView, TimerWheelView progressWheelView)
    {
        m_levelView = levelView;
        m_monsterView = monsterView;
        m_circleView = circleView;
        m_progressWheelView = progressWheelView;
    }
	
	//endregion

	//region Methods
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK); //todo: extract to configuration
		m_levelView.draw(canvas);
		m_monsterView.draw(canvas);
		m_circleView.draw(canvas);
		m_progressWheelView.draw(canvas);
		//setDrawingCacheEnabled(true);
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //endregion
}
