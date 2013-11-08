package com.example.booya.UI.Views;

import com.example.booya.R;
import com.example.booya.BL.Monster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MonsterView extends View {

	//region members
	
	/**
	 * 
	 */
	private float m_monsterWidth = Monster.MONSTER_SIZE;
	private float m_monsterHeight = Monster.MONSTER_SIZE;
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
	
	
//	/**
//	 * @return
//	 * @throws Exception
//	 */
//	private Bitmap getMonsterBitmap() throws Exception
//	{
//		Bitmap monsterBitmap = null;
//		try 
//		{
//			BitmapFactory.Options o = new BitmapFactory.Options();
//			o.outWidth  = m_monsterWidth ;
//			o.outHeight = m_monsterHeight; 
//			monsterBitmap = BitmapFactory.decodeResource(getResources(),
//					R.drawable.monster,o);
//
//			monsterBitmap = Bitmap.createScaledBitmap(monsterBitmap,
//					m_monsterWidth, m_monsterHeight, false);
//		}
//		catch (Exception e) 
//		{
//			throw e;
//		}
//		return monsterBitmap;
//	}
	
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
			m_monsterPaint.setColor(Color.BLUE);
		}
		
		return (m_monsterPaint);
		
	}
	
	//endregion
}
