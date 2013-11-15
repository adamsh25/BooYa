package com.example.booya;

import com.example.booya.recording.CameraRecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;

public class TesterActivity extends Activity {
	public static Boolean bIsDUBUG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tester);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tester, menu);
		return true;
	}
	public void launchMainBooya(View view) {
		bIsDUBUG = Boolean.FALSE;
	    Intent intent = new Intent(this, MainBooyaActivity.class);
	    startActivity(intent);
	}
	public void launchMazeTester(View view) {
		bIsDUBUG = Boolean.FALSE;
	    Intent intent = new Intent(this, GenericGameActivity.class);
	    startActivity(intent);
	}
	public void launchCameraTester(View view) {
		bIsDUBUG = Boolean.TRUE;
	    Intent intent = new Intent(this, CameraRecorder.class);
	    startActivity(intent);
	}
	public static Boolean getIsDebugMode()
	{
		return bIsDUBUG;
	}

}
