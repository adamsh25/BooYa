//package com.example.booya.video.recording;
//
//import android.app.IntentService;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.os.Process;
//
///**
// * User: ronlut
// * Date: 13/12/13 13:39
// */
//public class RecordingService extends Service
//{
//    public static final String STOP_ACTION = "stop";
//    public static final String START_ACTION = "start";
//	private static final String TAG = "RecordingIntentService";
//
//    public RecordingService() {
//        super("RecordingIntentService");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        CameraHelper.getInstance().
//        super.onDestroy();
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
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
