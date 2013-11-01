package com.example.booya.UI.Views;
import com.example.booya.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class MazeView extends View 
{
	
	//region members
	
	private MonsterView m_monsterView;
	private GameLevelView m_levelView;
	private Bitmap m_backgroundBitmap;	
	
	//endregion
	
	//region C'tor
	
	public MazeView(Context context)
	{
		super(context);
		m_backgroundBitmap = BitmapFactory.
				decodeResource(getResources(), R.drawable.back);
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
		canvas.drawBitmap(this.m_backgroundBitmap, 1, 1, null);
		this.m_levelView.draw(canvas);
		this.m_monsterView.draw(canvas);
	}
	
	//endregion
}
