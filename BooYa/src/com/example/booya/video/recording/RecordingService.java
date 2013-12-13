package com.example.booya.video.recording;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Camera;
import android.os.IBinder;

/**
 * User: ronlut
 * Date: 13/12/13 13:39
 */
public class RecordingService extends Service
{
	private static final String TAG = "RecordingService";
	Thread recordThread = new Thread(
			new Runnable()
			{
				@Override
				public void run()
				{
					CameraHelper.getInstance().StartRecording();
				}
			});
	Thread stopThread = new Thread(
			new Runnable()
			{
				@Override
				public void run()
				{
					CameraHelper.getInstance().StopRecording();
				}
			});


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		recordThread.start();

		return Service.START_NOT_STICKY;
	}

	public void onDestroy()
	{
		stopThread.start();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}


	}
