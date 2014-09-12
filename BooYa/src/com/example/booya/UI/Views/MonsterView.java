package com.example.booya.UI.Views;

import com.example.booya.BL.Monster;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

@SuppressLint("ViewConstructor")
public class MonsterView extends View {

    //region members

    private Paint m_monsterPaint; // The Wall Design Member
    private Monster _monster;

    //endregion

    //region C'tor

    /**
     * @param context
     * @param monster
     */
    public MonsterView(Context context, Monster monster) {
        super(context);
        _monster = monster;
    }

    //endregion

    //region Methods

    public void MoveMonster(float x, float y) {
        _monster.move(x, y);
        invalidate();
    }

    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try { //todo: why try catch?
            //canvas.drawBitmap(this.getMonsterBitmap(),
            //_monster.getX(),
            //_monster.getY(),
            //null);
            canvas.drawRect(_monster.getLeft(), _monster.getTop(),
                    _monster.getRight(), _monster.getBottom(),
                    getMonsterPaint());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Paint getMonsterPaint() {
        if (m_monsterPaint == null) {
            m_monsterPaint = new Paint();
            m_monsterPaint.setColor(Color.BLACK); //todo: extract to configuration
        }

        return (m_monsterPaint);
    }


    //endregion
}
