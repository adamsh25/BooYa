package com.example.booya.video.recording;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Camera;
import android.os.*;
import android.os.Process;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * User: ronlut
 * Date: 13/12/13 13:39
 */
public class RecordingIntentService extends IntentService
{
    public static final String STOP_ACTION = "stop";
    public static final String STOP_RELEASE_ACTION = "stopandrelease";
    public static final String START_ACTION = "start";
    public static final String OPEN_ACTION = "open";
    public static final String THREAD_PRIORITY = "tp";
    public static final String DELAY_SECONDS = "delay";

    private static boolean shouldRecord = false;

	private static final String TAG = "RecordingIntentService";

    public RecordingIntentService() {
        super("RecordingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        int threadPriority = intent.getIntExtra(THREAD_PRIORITY, Process.THREAD_PRIORITY_DEFAULT);
        int sleepTime = intent.getIntExtra(DELAY_SECONDS, 0);

        android.os.Process.setThreadPriority(threadPriority);

        if (sleepTime > 0) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (action.equalsIgnoreCase(START_ACTION)) {
            CameraHelper.getInstance().StartRecording2();
            shouldRecord = true;

            while (shouldRecord) //change to field in this class
            {
            }
        }
        else if (action.equalsIgnoreCase(STOP_ACTION)) {
            CameraHelper.getInstance().StopRecording2();
        }
        else if (action.equalsIgnoreCase(STOP_RELEASE_ACTION)) {
            CameraHelper.getInstance().StopRecording2();
            CameraHelper.getInstance().ReleaseCamera();
        }
        else if (action.equalsIgnoreCase(OPEN_ACTION)) {
            CameraHelper.getInstance().OpenCamera();
        }
    }

    public static boolean isShouldRecord() {
        return shouldRecord;
    }

    public static void setShouldRecord(boolean shouldRecordValue) {
        shouldRecord = shouldRecordValue;
    }
}
