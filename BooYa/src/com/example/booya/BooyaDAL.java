package com.example.booya;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
		
		cursor.moveToFirst();
		nNumOfVictims = cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NUM_OF_VICTIMS));
		cursor.close();
		
		return nNumOfVictims;
	}
	
	  public List<Integer> getAllPurchasedFigures() {
		    List<Integer> PurchasedFigures = new ArrayList<Integer>();

		    // Purchased figure's inventory type is 1 for now
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_INVENTORY,
		    		new String[] {MySQLiteHelper.COLUMN_PRODUCT_ID}, MySQLiteHelper.COLUMN_INVENTORY_TYPE + " = 1", null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      int product_id = cursor.getInt(0);
		      PurchasedFigures.add(product_id);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return PurchasedFigures;
		  }
	  
	  public List<PrankMethod> getAllPrankMethods() {
		    List<PrankMethod> PrankMethodsList = new ArrayList<PrankMethod>();

		    // Purchased figure's inventory type is 1 for now
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_PRANK_METHODS,
		    		new String[] {MySQLiteHelper.COLUMN_PRANK_ID,MySQLiteHelper.COLUMN_IMG_RES_ID,MySQLiteHelper.COLUMN_PURCHASED}, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) 
		    {
		      PrankMethod currPrankMethod = new PrankMethod(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
		      PrankMethodsList.add(currPrankMethod);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return PrankMethodsList;
		  }
	  
	  public List<Integer> getAllPurchasedScreams() {
		    List<Integer> PurchasedFigures = new ArrayList<Integer>();

		    // Purchased screams's inventory type is 2 for now
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_INVENTORY,
		    		new String[] {MySQLiteHelper.COLUMN_PRODUCT_ID}, MySQLiteHelper.COLUMN_INVENTORY_TYPE + " = 2", null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      int product_id = cursor.getInt(0);
		      PurchasedFigures.add(product_id);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return PurchasedFigures;
		  }
  
} 


