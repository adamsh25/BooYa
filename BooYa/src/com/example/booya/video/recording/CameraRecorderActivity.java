package com.example.booya.video.recording;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.view.SurfaceView;
import android.view.View;

import com.example.booya.R;

public class CameraRecorderActivity extends Activity
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
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.ACTION_OPEN_CAMERA);
        startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: handle phone orientation change. we should disable it in the maze game.
        RecordingIntentService.setShouldRecord(false);
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.ACTION_RELEASE_CAMERA);
        //i.putExtra(RecordingIntentService.DELAY_SECONDS, 3);
        startService(i);
    }

    public void StartRec(View view) {
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.ACTION_START_RECORDING);
        i.putExtra(RecordingIntentService.EXTRA_THREAD_PRIORITY, Process.THREAD_PRIORITY_BACKGROUND);
        startService(i);
    }

    public void StopRec(View view) {
        RecordingIntentService.setShouldRecord(false);
        Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.ACTION_STOP_RECORDING);
        i.putExtra(RecordingIntentService.EXTRA_DELAY_SECONDS, 3);
        //i.putExtra(RecordingIntentService.THREAD_PRIORITY, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        i.putExtra(RecordingIntentService.EXTRA_START_FFMPEG, true);
        i.putExtra(RecordingIntentService.EXTRA_WRITE_TO_DB, true);
        startService(i);
    }
}