package com.example.booya.video.recording;

import android.app.IntentService;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.example.booya.video.processing.BooyaFFMPEGIntentService;

import java.util.concurrent.TimeUnit;

/**
 * User: ronlut
 * Date: 13/12/13 13:39
 */
public class RecordingIntentService extends IntentService
{
    // Intent actions
    public static final String ACTION_STOP_RECORDING = "com.example.booya.stop_recording";
    public static final String ACTION_RELEASE_CAMERA = "com.example.booya.release_camera";
    public static final String ACTION_START_RECORDING = "com.example.booya.start_recording";
    public static final String ACTION_OPEN_CAMERA = "com.example.booya.open_camera";

    // Intent Extras
    public static final String EXTRA_THREAD_PRIORITY = "com.example.booya.thread_priority";
    public static final String EXTRA_DELAY_SECONDS = "com.example.booya.delay";
    public static final String EXTRA_START_FFMPEG = "com.example.booya.start_ffmpeg";
    public static final String EXTRA_WRITE_TO_DB = "com.example.booya.write_to_db";

    // Broadcast results

    private static boolean shouldRecord = false;

	private final String TAG = RecordingIntentService.class.getSimpleName();

    public RecordingIntentService() {
        super(RecordingIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        int threadPriority = intent.getIntExtra(EXTRA_THREAD_PRIORITY, Process.THREAD_PRIORITY_DEFAULT);
        int sleepTime = intent.getIntExtra(EXTRA_DELAY_SECONDS, 0);

        android.os.Process.setThreadPriority(threadPriority);

        if (sleepTime > 0) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (action.equalsIgnoreCase(ACTION_START_RECORDING)) {
            CameraHelper.getInstance().StartRecording();
            shouldRecord = true;

            while (shouldRecord)
            {
            }
        }
        else if (action.equalsIgnoreCase(ACTION_STOP_RECORDING)) {
            //if (!CameraHelper.getInstance().isRecording) {
            String fileName = CameraHelper.getInstance().StopRecording();
            //}

            if (intent.getBooleanExtra(ACTION_RELEASE_CAMERA, false)) {
                CameraHelper.getInstance().ReleaseCamera();
            }

            if ((intent.getBooleanExtra(EXTRA_WRITE_TO_DB, false) || intent.getBooleanExtra(EXTRA_START_FFMPEG, false)) && fileName == null) {
                Log.d(TAG, "Recording probably failed or was too short [fileName == null]. Returning.");
                return;
            }

            if (intent.getBooleanExtra(EXTRA_WRITE_TO_DB, false)) {
                //TODO: write the video file path to db with status "raw" (= 0)
            }

            if (intent.getBooleanExtra(EXTRA_START_FFMPEG, false)) {
                //TODO: get the relevant parameters here from the intent
                Intent ffmpegIntent = new Intent(getApplicationContext(), BooyaFFMPEGIntentService.class);
                ffmpegIntent.putExtra(BooyaFFMPEGIntentService.EXTRA_FILE_NAME, fileName);
                startService(ffmpegIntent);
            }
        }
        else if (action.equalsIgnoreCase(ACTION_RELEASE_CAMERA)) {
            CameraHelper.getInstance().ReleaseCamera();
        }
        else if (action.equalsIgnoreCase(ACTION_OPEN_CAMERA)) {
            CameraHelper.getInstance().OpenCamera();
            //TODO: implement broadcast to announce on camera open fail so game won't start.
        }
    }

    public static boolean isShouldRecord() {
        return shouldRecord;
    }

    public static void setShouldRecord(boolean shouldRecordValue) {
        shouldRecord = shouldRecordValue;
    }
}
