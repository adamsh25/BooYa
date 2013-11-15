package com.example.booya;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GenericGameActivity extends Activity {
	
	// For Recording Stuff
	private static final String TAG = "Recorder";
	public static SurfaceView mSurfaceView;
	public static SurfaceHolder mSurfaceHolder;
	public static Camera mCamera;
	public static boolean mPreviewRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO check what game are we in
		setContentView(R.layout.activity_maze_game_main);
		
		// For Recording Stuff
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView2);
		mSurfaceHolder = mSurfaceView.getHolder();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_game, menu);
		return true;
	}
	
	public void launchGame(View view) {
		//TODO check what game are we in
		
	    Intent intent = new Intent(this,MazeGameActivity.class);
	    startActivity(intent);
	}

}
