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
  public static final String COLUMN_PRODUCT_ID = "product_id";
 

  // Database creation sql statement
  private static final String DATABASE_CREATE_USER_DETAILES = "create table "
      + TABLE_USER + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NUM_OF_VICTIMS
      + " integer not null);";
  
  private static final String DATABASE_CREATE_USER_INVENTORY = "create table "
	      + TABLE_USER_INVENTORY + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_INVENTORY_TYPE
	      + " integer not null, " +COLUMN_PRODUCT_ID+ "integer not null);";

  public MySQLiteHelper(Context context) 
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE_USER_DETAILES);
    database.execSQL(DATABASE_CREATE_USER_INVENTORY);
    database.execSQL("INSERT INTO USER_DETAILS(num_of_victims) values(0);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INVENTORY);
    onCreate(db);
  }

} 