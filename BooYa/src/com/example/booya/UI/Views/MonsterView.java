package com.example.booya.UI.Views;

import com.example.booya.BL.Monster;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

@SuppressLint("ViewConstructor")
public class MonsterView extends View
{

	//region members
	
	/**
	 * 
	 */
	private Paint m_monsterPaint; // The Wall Design Member
	private Monster m_theMonster;
	
	//endregion
	
	//region C'tor
	
	/**
	 * @param context
	 * @param monster
	 */
	public MonsterView(Context context, Monster monster) 
	{
		super(context);
		this.m_theMonster = monster;
		
	}

	//endregion
	
	//region Methods
	
	

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{

		super.onDraw(canvas);

		try
		{
			//canvas.drawBitmap(this.getMonsterBitmap(),
								//m_theMonster.getX(),
								//m_theMonster.getY(),
								//null);
			canvas.drawRect(m_theMonster.getLeft(), m_theMonster.getTop(),
							m_theMonster.getRight(), m_theMonster.getBottom(),
							getMonsterPaint());
		} 
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}
	
	protected Paint getMonsterPaint() 
	{
		if(m_monsterPaint == null)
		{
			m_monsterPaint = new Paint();
			m_monsterPaint.setColor(Color.BLACK);
		}
		
		return (m_monsterPaint);
		
	}
	
	//endregion
}
