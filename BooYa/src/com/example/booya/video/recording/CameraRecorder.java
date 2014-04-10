package com.example.booya.video.recording;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.SurfaceView;
import android.view.View;

import com.example.booya.R;

public class CameraRecorder extends Activity
{
	private final String TAG = "Recorder";
	public SurfaceView mSurfaceView;
	//public static SurfaceHolder mSurfaceHolder;
	//public static Camera mCamera;
	//public static boolean mPreviewRunning;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		
		CameraHelper.getInstance().SetSurfaceView(mSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CameraHelper.getInstance().OpenCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: handle phone orientation change. we should disable it in the maze game.
        CameraHelper.getInstance().ShouldNotRecord();
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.STOP_RELEASE_ACTION);
        startService(i);
    }

    public void StartRec(View view) {
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.START_ACTION);
        startService(i);
    }

    public void StopRec(View view) {
        CameraHelper.getInstance().ShouldNotRecord();
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.STOP_ACTION);
        startService(i);
    }
}