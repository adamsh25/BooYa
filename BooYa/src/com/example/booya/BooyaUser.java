package com.example.booya;

import java.util.List;

import android.annotation.SuppressLint;
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
	private static List<PrankMethod> prankMethodList;
	private List<String> videosPathList;
	private static int nAndroidApiVersion;
	private static boolean bCanVibrate;
	
	
	private BooyaUser()
	{

	}
	

	@SuppressLint("NewApi")
	public static void loadInfo(Context context)
	{
		// Check android version
		nAndroidApiVersion = android.os.Build.VERSION.SDK_INT;
		bCanVibrate = false;
		
		// Check camera
		bHasFrontCamera = (CameraHelper.getInstance().findFrontFacingCameraId() != -1);
		
		if(nAndroidApiVersion>10)
		{
		// Check vibration
		bCanVibrate = ((Vibrator)context.getSystemService(context.VIBRATOR_SERVICE)).hasVibrator();
		}
		// Set datasource
		datasource = new BooyaDAL(context);
		
		// Open DB
	    datasource.open();
	    
	    // Initailize Info
		InitializeParametersFromDB();
		
		// Close DB
		datasource.close();
	}
	
		
	public static void IncreaseNumberOfVictims()
	{
		// Increase the number in cache
		nNumOfVictims++;
		
		// Open DB in order to make changes in disk || TODO make it asynchronous
		datasource.open();
		datasource.IncreaseNumberOfVictimsInDB(nNumOfVictims);
		datasource.close();
	}
	
	private static void InitializeParametersFromDB()
	{
		nNumOfVictims = datasource.GetUserVictimsFromDB();
		purchasedFiguresList = datasource.getAllPurchasedFigures();
		purchasedScreamList = datasource.getAllPurchasedScreams();
		prankMethodList = datasource.getAllPrankMethods();
	}
	
	public static List<PrankMethod> getprankMethodList()
	{
		return prankMethodList;
	}
	
	public static int GetNumberOfVictims()
	{
		return nNumOfVictims;
	}
	
	public static boolean HasFrontCamera()
	{
		return bHasFrontCamera;
	}
	
	public static boolean CanVibrate()
	{
		return bCanVibrate;
	}
	
	

}
