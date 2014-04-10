package com.example.booya.video.recording;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Camera;
import android.os.*;
import android.os.Process;

/**
 * User: ronlut
 * Date: 13/12/13 13:39
 */
public class RecordingIntentService extends IntentService
{
    public static final String STOP_ACTION = "stop";
    public static final String STOP_RELEASE_ACTION = "stopandrelease";
    public static final String START_ACTION = "start";
	private static final String TAG = "RecordingIntentService";

    public RecordingIntentService() {
        super("RecordingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        String action = intent.getAction();

        if (action.equalsIgnoreCase(START_ACTION)) {
            CameraHelper.getInstance().StartRecording2();
            while (CameraHelper.getInstance().shouldRecord) {}
        }
        else if (action.equalsIgnoreCase(STOP_ACTION)) {
            CameraHelper.getInstance().StopRecording2();
        }
        else if (action.equalsIgnoreCase(STOP_RELEASE_ACTION)) {
            CameraHelper.getInstance().StopRecording2();
            CameraHelper.getInstance().ReleaseCamera();
        }
    }
}
