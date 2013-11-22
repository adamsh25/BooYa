package com.example.booya;

import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.booya.video.recording.CameraHelper;

public class BooyaUser {
	
	private BooyaDAL datasource;
	private boolean bHasFrontCamera;
	private int nNumOfVictims;
	private List<String> facebookFriendsList;
	private List<Integer> purchasedFiguresList;
	private List<Integer> purchasedScreamList;
	private List<String> videosPathList;
	private int nAndroidApiVersion;
	
	
	public BooyaUser(Context context)
	{
		nAndroidApiVersion = android.os.Build.VERSION.SDK_INT;
		bHasFrontCamera = (CameraHelper.getInstance().findFrontFacingCameraId() != -1);	
		datasource = new BooyaDAL(context);
		
	    datasource.open();		
		InitializeParametersFromDB();
		
		datasource.close();
	}
	
	public void IncreaseNumberOfVictims()
	{
		nNumOfVictims++;
		
		datasource.open();
		datasource.IncreaseNumberOfVictimsInDB(nNumOfVictims);
		datasource.close();
	}
	
	public void InitializeParametersFromDB()
	{
		datasource.GetUserVictimsFromDB();
		purchasedFiguresList = datasource.getAllPurchasedFigures();
		purchasedScreamList = datasource.getAllPurchasedScreams();
	}
	
	public int GetNumberOfVictims()
	{
		return nNumOfVictims;
	}
	
	public boolean HasFrontCamera()
	{
		return bHasFrontCamera;
	}
	

	
	
	
	

}
