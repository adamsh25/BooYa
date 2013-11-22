package com.example.booya.UI.Views;

import org.apache.http.client.CircularRedirectException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceView;
import android.view.View;

public class MazeView extends View 
{
	
	//region members
	
	private MonsterView m_monsterView;
	private GameLevelView m_levelView;
	private StartOffsetCircleView m_circleView;
	SurfaceView m_dummyView;
	
	//endregion
	
	//region C'tor
	
	public MazeView(Context context)
	{
		super(context);
	}
	
	
	//endregion

	//region Methods
	
	public void setViews(GameLevelView levelView, MonsterView monsterView,
					StartOffsetCircleView circleView)
	{
		m_levelView = levelView;
		m_monsterView = monsterView;
		this.m_circleView = circleView;
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		this.m_levelView.draw(canvas);
		this.m_monsterView.draw(canvas);
		this.m_circleView.draw(canvas);
		this.setDrawingCacheEnabled(true);

	}
	
	//endregion
}
