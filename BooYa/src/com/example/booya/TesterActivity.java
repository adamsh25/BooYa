package com.example.booya;

import com.example.booya.social.facebook.FacebookPublishVideoActivity;
import com.example.booya.video.recording.CameraHelper;
import com.example.booya.video.recording.CameraRecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;

public class TesterActivity extends Activity {
	public static Boolean bIsDUBUG = Boolean.FALSE;
	public static boolean bHasFrontCamera;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tester);
		DetectFrontFacingCamera();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tester, menu);
		return true;
	}
	public void launchMainBooya(View view) {
		//bIsDUBUG = Boolean.FALSE;
	    Intent intent = new Intent(this, MainBooyaActivity.class);
	    startActivity(intent);
	}

    public void launchFacebookTester(View view) {
        Intent intent = new Intent(this, FacebookPublishVideoActivity.class);
        startActivity(intent);
    }

	public void launchMazeTester(View view) {
		//bIsDUBUG = Boolean.FALSE;
	    Intent intent = new Intent(this, GenericGameActivity.class);
	    startActivity(intent);
	}
	public void launchCameraTester(View view) {
		//bIsDUBUG = Boolean.FALSE;
	    Intent intent = new Intent(this, CameraRecorder.class);
	    startActivity(intent);
	}

    public void launchSocialSettings(View view) {
        Intent intent = new Intent(this, SocialSettingsActivity.class);
        startActivity(intent);
    }
	public static Boolean getIsDebugMode()
	{
		return bIsDUBUG;
	}
	public static void DetectFrontFacingCamera()
	{
		bHasFrontCamera = (CameraHelper.getInstance().findFrontFacingCameraId() != -1);
	}
	public void onCheckboxClicked(View view) 
	{
	    // Is the view now checked?
	    boolean checked = ((CheckBox)(findViewById(R.id.checkBox1))).isChecked();
	   
	    if(!checked)
	    {
	    	bHasFrontCamera = Boolean.FALSE;
	    	bIsDUBUG = Boolean.TRUE;
	    }
	    
	}

}
