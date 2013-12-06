package com.example.booya;

import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

import com.example.booya.video.recording.CameraHelper;

public class BooyaUser {
	
	private static BooyaDAL datasource;
	private static boolean bHasFrontCamera;
	private static int nNumOfVictims;
	private List<String> facebookFriendsList;
	private static List<Integer> purchasedFiguresList;
	private static List<Integer> purchasedScreamList;
	private List<String> videosPathList;
	private static int nAndroidApiVersion;
	private boolean bCanVibrate;
	
	
	private BooyaUser()
	{

	}
	
	public static void loadInfo(Context context)
	{
		nAndroidApiVersion = android.os.Build.VERSION.SDK_INT;
		bHasFrontCamera = (CameraHelper.getInstance().findFrontFacingCameraId() != -1);	
		
		datasource = new BooyaDAL(context);	
	    datasource.open();
		InitializeParametersFromDB();
		
		datasource.close();
	}
	
		
	public static void IncreaseNumberOfVictims()
	{
		nNumOfVictims++;
		
		datasource.open();
		datasource.IncreaseNumberOfVictimsInDB(nNumOfVictims);
		datasource.close();
	}
	
	private static void InitializeParametersFromDB()
	{
		nNumOfVictims = datasource.GetUserVictimsFromDB();
		purchasedFiguresList = datasource.getAllPurchasedFigures();
		purchasedScreamList = datasource.getAllPurchasedScreams();
	}
	
	public static int GetNumberOfVictims()
	{
		return nNumOfVictims;
	}
	
	public static boolean HasFrontCamera()
	{
		return bHasFrontCamera;
	}
	

}
