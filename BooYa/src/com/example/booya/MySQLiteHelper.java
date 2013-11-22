package com.example.booya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  // Global names 
  public static final String COLUMN_ID = "_id";	
  private static final String DATABASE_NAME = "booya.db";
  private static final int DATABASE_VERSION = 1;
	
  // User_Deatails table
  public static final String TABLE_USER = "user_details";
  public static final String COLUMN_NUM_OF_VICTIMS = "num_of_victims";
  
  // User_Inventory table  
  public static final String TABLE_USER_INVENTORY = "user_inventory";  
  public static final String COLUMN_INVENTORY_TYPE = "inventory_type";
 

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_USER + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NUM_OF_VICTIMS
      + " integer not null);";

  public MySQLiteHelper(Context context) 
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    onCreate(db);
  }

} 