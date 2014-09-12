package com.example.booya;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.example.booya.video.recording.CameraHelper;

public class GenericGameActivity extends Activity {
	
	// For Recording Stuff
	public static final String ARG_PRANK_METHOD = "curr_prank_method";
	private static final String TAG = "Recorder";
	public static SurfaceView mSurfaceView;
	public static Camera mCamera;
	public static boolean mPreviewRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//TODO check what game are we in
		Intent myIntent = getIntent(); // gets the previously created intent
		PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
        switch (curPrankMethod.getN_prank_id()) {
            case 1:
                setContentView(R.layout.activity_maze_game_main);
                break;
            case 2:
                setContentView(R.layout.activity_cups_game_main);
                break;
        }
        // Check if have front camera
		if(CameraHelper.getInstance().HasFrontFacingCamera()) //todo: what are we checking here?
		{
			// For Recording Stuff
			mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView2);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_game, menu);
		return true;
	}
	
	public void launchGame(View view) 
	{
		//TODO check what game are we in
		Intent intent = null;
		
	    intent = new Intent(this,MazeGameActivity.class);
		Intent myIntent = getIntent(); // gets the previously created intent
		PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
		intent.putExtra("curPrankMethod",curPrankMethod);
	    startActivity(intent);
	}
	
	public void launchCupsGame(View view) 
	{
		//TODO check what game are we in
		Intent intent = null;
		Intent myIntent = getIntent(); // gets the previously created intent
		PrankMethod curPrankMethod = (PrankMethod) myIntent.getParcelableExtra("curPrankMethod");
	    intent = new Intent(this,CupsGameActivity.class);
	    intent.putExtra("curPrankMethod",curPrankMethod);
	    startActivity(intent);
	}

}
