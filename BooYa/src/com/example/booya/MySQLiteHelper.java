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
	
  // User_Details table
  public static final String TABLE_USER = "user_details";
  public static final String COLUMN_NUM_OF_VICTIMS = "num_of_victims";
  
  // User_Inventory table  
  public static final String TABLE_USER_INVENTORY = "user_inventory";  
  public static final String COLUMN_INVENTORY_TYPE = "inventory_type";
  public static final String COLUMN_PRODUCT_ID = "product_id";
  
  // User_Pranks_Methods table  
  public static final String TABLE_USER_PRANK_METHODS = "user_prank_methods";  
  public static final String COLUMN_PRANK_ID = "prank_id";
  public static final String COLUMN_IMG_RES_ID = "img_res_id";
  public static final String COLUMN_PURCHASED = "purchased";
 

  // Database creation sql statement
  private static final String DATABASE_CREATE_USER_DETAILES = "create table "
      + TABLE_USER + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NUM_OF_VICTIMS
      + " integer not null);";
  
  private static final String DATABASE_CREATE_USER_INVENTORY = "create table "
	      + TABLE_USER_INVENTORY + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_INVENTORY_TYPE
	      + " integer not null, " +COLUMN_PRODUCT_ID+ " integer not null);";
  
  private static final String DATABASE_CREATE_USER_PRANKS_METHODS = "create table "
	      + TABLE_USER_PRANK_METHODS + "(" + COLUMN_PRANK_ID
	      + " integer primary key, " + COLUMN_IMG_RES_ID
	      + " integer not null, " + COLUMN_PURCHASED+ " integer not null);";
  
  

  public MySQLiteHelper(Context context) 
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE_USER_DETAILES);
    database.execSQL(DATABASE_CREATE_USER_INVENTORY);
    database.execSQL(DATABASE_CREATE_USER_PRANKS_METHODS);
    database.execSQL("INSERT INTO USER_DETAILS(num_of_victims) values(0);");
    database.execSQL("INSERT INTO user_inventory(inventory_type,product_id) values(1,1111);");
    database.execSQL("INSERT INTO user_inventory(inventory_type,product_id) values(2,2222);");
    
    database.execSQL("INSERT INTO user_prank_methods(prank_id,img_res_id,purchased) values(1,1,1);");
    database.execSQL("INSERT INTO user_prank_methods(prank_id,img_res_id,purchased) values(2,2,1);");
 
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INVENTORY);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PRANK_METHODS);
    onCreate(db);
  }

} 