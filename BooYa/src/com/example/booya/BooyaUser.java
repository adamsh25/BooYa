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
	private List<String> purchasedFiguresList;
	private List<String> purchasedScreamList;
	private List<String> videosPathList;
	private int nAndroidApiVersion;
	
	
	public BooyaUser(Context context)
	{
		nAndroidApiVersion = android.os.Build.VERSION.SDK_INT;
		bHasFrontCamera = (CameraHelper.getInstance().findFrontFacingCameraId() != -1);
		
		datasource = new BooyaDAL(context);
	    datasource.open();
		
		InitializeParametersFromDB();	
	}
	
	public void IncreaseNumberOfVictims()
	{
		nNumOfVictims++;
		
		datasource.IncreaseNumberOfVictimsInDB(nNumOfVictims);
	}
	
	public void InitializeParametersFromDB()
	{
		datasource.GetUserVictimsFromDB();
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
