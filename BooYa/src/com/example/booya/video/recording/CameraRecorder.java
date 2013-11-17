package com.example.booya.video.recording;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.booya.R;

public class CameraRecorder extends Activity 
{
	private final String TAG = "Recorder";
	public SurfaceView mSurfaceView;
	//public static SurfaceHolder mSurfaceHolder;
	//public static Camera mCamera;
	//public static boolean mPreviewRunning;
	private CameraHelper cameraHelper;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		
		cameraHelper = new CameraHelper(mSurfaceView);
//		mSurfaceHolder = mSurfaceView.getHolder();
		//mSurfaceHolder.addCallback(this);
		//mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Button btnStart = (Button) findViewById(R.id.StartService);
		btnStart.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Intent intent = new Intent(CameraRecorder.this, RecorderService.class);
				//Intent intent2 = new Intent(CameraRecorder.this, GameActivity.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
				cameraHelper.StartRecording();
				
				// OR HERE
				//startService(intent);
			
				//startActivity(intent2);
				//finish();
			}
		});

		Button btnStop = (Button) findViewById(R.id.StopService);
		btnStop.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//stopService(new Intent(CameraRecorder.this, RecorderService.class));
				cameraHelper.StopRecording();
			}
		});
    }
}