package com.example.booya;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class BooyaDAL {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  
  public BooyaDAL(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close()
  {
    dbHelper.close();
  }
  
	public void IncreaseNumberOfVictimsInDB(int nNumOfVictims)
	{

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NUM_OF_VICTIMS, nNumOfVictims);
		
		database.update(MySQLiteHelper.TABLE_USER, values, null, null);		
		
	}
	
	public int GetUserVictimsFromDB()
	{
		int nNumOfVictims;
		// Getting only number of victims for now.
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
				new String[] {MySQLiteHelper.COLUMN_NUM_OF_VICTIMS}, null, null, null, null, null);
		
		nNumOfVictims = cursor.getInt(1);
		cursor.close();
		
		return nNumOfVictims;
	}
  
} 


