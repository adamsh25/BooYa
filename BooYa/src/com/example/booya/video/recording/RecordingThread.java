//package com.example.booya.video.recording;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.os.Process;
//
///**
// * User: ronlut
// * Date: 13/12/13 13:39
// */
//public class RecordingThread extends Thread
//{
//    public static final String STOP_ACTION = "stop";
//    public static final String START_ACTION = "start";
//	private static final String TAG = "RecordingService";
//
//    public RecordingThread() {
//        super("RecordingService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//
//        String action = intent.getAction();
//
//        if (action.equalsIgnoreCase(START_ACTION)) {
//            CameraHelper.getInstance().StartRecording();
//        }
//        else if (action.equalsIgnoreCase(STOP_ACTION)) {
//            CameraHelper.getInstance().StopRecording();
//        }
//    }
//
//
//}
