package com.example.booya.UI.Views;

import com.example.booya.R;
import com.example.booya.BL.Monster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class MonsterView extends View {

	//region members
	
	private int m_monsterWidth = Monster.MONSTER_WIDTH;
	private int m_monsterHeight = Monster.MONSTER_HEIGHT;
	
	private Monster m_theMonster;
	
	//endregion
	
	//region C'tor
	
	public MonsterView(Context context, Monster monster) 
	{
		super(context);
		this.m_theMonster = monster;
		
	}

	//endregion
	
	//region Methods
	private Bitmap getMonsterBitmap() throws Exception
	{
		Bitmap monsterBitmap = null;
		try 
		{
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.outWidth  = m_monsterWidth ;
			o.outHeight = m_monsterHeight; 
			monsterBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.monster,o);

			monsterBitmap = monsterBitmap.createScaledBitmap(monsterBitmap,
					m_monsterWidth, m_monsterHeight, false);
		}
		catch (Exception e) 
		{
			throw e;
		}
		return monsterBitmap;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		try
		{
			canvas.drawBitmap(this.getMonsterBitmap(),
								m_theMonster.getX(),
								m_theMonster.getY(),
								null);
		} 
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}
	
	//endregion
}
