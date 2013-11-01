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
	
	private final int MONSTER_WIDTH = 15;
	private final int MONSTER_HEIGHT = 15;
	
	private Monster m_theMonster;
	
	//endregion
	
	//region C'tor
	
	public MonsterView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//endregion
	
	//region Methods
	private Bitmap getMonsterBitmap() throws Exception
	{
		Bitmap monsterBitmap = null;
		try 
		{
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.outWidth  = MONSTER_WIDTH ;
			o.outHeight = MONSTER_HEIGHT; 
			monsterBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.monster,o);

/*			monsterBitmap = monsterBitmap.createScaledBitmap(monsterBitmap,
					MONSTER_WIDTH, MONSTER_HEIGHT, false);*/
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
