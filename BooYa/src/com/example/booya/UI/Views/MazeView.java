package com.example.booya.UI.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class MazeView extends View 
{
	
	//region members
	
	private MonsterView m_monsterView;
	private GameLevelView m_levelView;
	
	//endregion
	
	//region C'tor
	
	public MazeView(Context context)
	{
		super(context);

	}
	
	
	//endregion

	//region Methods
	
	public void setViews(GameLevelView levelView, MonsterView monsterView)
	{
		m_levelView = levelView;
		m_monsterView = monsterView;
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//canvas.drawBitmap(this.m_backgroundBitmap, 1, 1, null);
		canvas.drawColor(Color.BLACK);
		this.m_levelView.draw(canvas);
		this.m_monsterView.draw(canvas);
	}
	
	//endregion
}
