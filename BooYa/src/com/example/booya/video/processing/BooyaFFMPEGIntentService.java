package com.example.booya.video.processing;

import android.app.IntentService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.example.booya.video.processing.ShellUtils.ShellCallback;


public class BooyaFFMPEGIntentService extends IntentService {
	private static String BOOYA_WATERMARK_FILE_PATH = "/sdcard/videokit/file1.png";
	private static String BOOYA_MAZE_SAMPE_FILE_PATH = "/sdcard/videokit/in2.mp4";
	private static String BOOYA_TEMP_VIDEO_FILE_PATH = "/sdcard/videokit/currtmp.mp4";
    private final String BOOYA_EXT = "-booya.mp4";
    public static final String EXTRA_THREAD_PRIORITY = "com.example.booya.thread_priority";
    public static final String EXTRA_FILE_NAME = "com.example.booya.ffmpeg_filename";
    final String TAG = "BooyaFFMPEG";

    /*****/
    //TODO: move to static members @ GalleryActivity
    private final String ACTION_FFMPEG_PROCESSING = "com.example.booya.ffmpeg_processing";
    private final String ACTION_FFMPEG_DONE = "com.example.booya.ffmpeg_done";
    private final String EXTRA_FFMPEG_PERCENTS = "com.example.booya.ffmpeg_percents";
    //TODO: complete the receiver side in gallery activity by: http://www.mobiledevguide.com/2013/01/how-to-use-intentservice-in-android.html
    /*****/

    private String ffmpegBin;
    private FfmpegController myFfmpegController;
    private ShellCallback sc;
    //private BooyaFFMPEGIntentService booya_ffmpeg_instance;

    public BooyaFFMPEGIntentService() {
        super("BooyaFFMPEG");
//        try
//        {
//            myFfmpegController = new FfmpegController(mContext);
//            ffmpegBin = myFfmpegController.GetFfmpegBin();
//        }
//        catch (FileNotFoundException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        sc = new ShellCallback() {
//
//            @Override
//            public void shellOut(String shellLine) {
//                // TODO Auto-generated method stub
//                Log.d(TAG, shellLine);
//            }
//
//            @Override
//            public void processComplete(int exitValue) {
//                // TODO Auto-generated method stub
//                Log.d(TAG, String.valueOf(exitValue));
//            }
//
//        };
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String fileName = intent.getStringExtra(EXTRA_FILE_NAME);

        if (fileName == null || fileName.isEmpty()) {
            Log.e(TAG, "Received null or empty file name");
            return;
        }

        int threadPriority = intent.getIntExtra(EXTRA_THREAD_PRIORITY, Process.THREAD_PRIORITY_BACKGROUND); //TODO: is this the right priority?
        android.os.Process.setThreadPriority(threadPriority);

        sc = new ShellCallback() {

            @Override
            public void shellOut(String shellLine) {
                Log.d(TAG, shellLine);
            }

            @Override
            public void processComplete(int exitValue) {
                Log.d(TAG, "Exit code: " + String.valueOf(exitValue));
            }
        };

        try
        {
            myFfmpegController = new FfmpegController(getApplicationContext());
            ffmpegBin = myFfmpegController.GetFfmpegBin();

            BroadcastFFMPEGStatus(ACTION_FFMPEG_PROCESSING, 10);

            try {
                CreateBooyaVideo(fileName, fileName + BOOYA_EXT);
                //TODO: update the video file status in db to "finished" (= 1)
                BroadcastFFMPEGStatus(ACTION_FFMPEG_DONE, null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            Log.d(TAG, "ffmpeg binary not found, message: " + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // clean up
            sc = null;
            ffmpegBin = null;
            myFfmpegController = null;
        }
    }

    private void BroadcastFFMPEGStatus(String action, Integer percents) {
        Intent ffmpegStatusBroadCastIntent = new Intent();
        ffmpegStatusBroadCastIntent.setAction(action);
        ffmpegStatusBroadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        if (percents != null) {
            ffmpegStatusBroadCastIntent.putExtra(EXTRA_FFMPEG_PERCENTS, percents);
        }

        sendBroadcast(ffmpegStatusBroadCastIntent);
    }

	private void CreateBooyaVideo(String inputCameraRawVideoFilePath, String outputBooyaVideoFilePath) throws IOException, InterruptedException
	{
        Log.i(TAG, "Starting ffmpeg processing");
        Log.d(TAG, "Stage 1");
        InsertSampleGameIntoCameraVideo(inputCameraRawVideoFilePath, BOOYA_TEMP_VIDEO_FILE_PATH);
        BroadcastFFMPEGStatus(ACTION_FFMPEG_PROCESSING, 50);
        Log.d(TAG, "Stage 2");
        InsertBooyaWatermark(BOOYA_TEMP_VIDEO_FILE_PATH,outputBooyaVideoFilePath);
        BroadcastFFMPEGStatus(ACTION_FFMPEG_PROCESSING, 90);
        Log.i(TAG, "processing ended");
    }
	
	private void InsertBooyaWatermark(String inputTempFilePath, String outTempFilePath) throws IOException, InterruptedException
	{
    	//Watermarking - Working using -filter_complex
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegBin);
		cmd.add("-y");
		cmd.add("-i");
		cmd.add(new File(inputTempFilePath).getCanonicalPath());
		cmd.add("-i");
		cmd.add(new File(BOOYA_WATERMARK_FILE_PATH).getCanonicalPath());
		cmd.add("-strict");
		cmd.add("-2");
		cmd.add("-filter_complex");	
		cmd.add("[0][1] overlay=10:10");
		cmd.add(new File(outTempFilePath).getCanonicalPath());
		myFfmpegController.execFFMPEG(cmd, sc);
	}
	
	private void InsertSampleGameIntoCameraVideo(String inputCameraRawVideoFilePath, String outTempFilePath) throws IOException, InterruptedException
	{
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegBin);
		cmd.add("-y");
		cmd.add("-i");
		cmd.add(new File(inputCameraRawVideoFilePath).getCanonicalPath());
		cmd.add("-i");
		cmd.add(new File(BOOYA_MAZE_SAMPE_FILE_PATH).getCanonicalPath());
		cmd.add("-strict");
		cmd.add("-2");
		cmd.add("-filter_complex");
		cmd.add("[1]scale=iw/2:ih/2,transpose=1 [pip]; [0][pip] overlay=main_w-overlay_w-500:main_h-overlay_h-10,transpose=2");
		cmd.add(new File(outTempFilePath).getCanonicalPath());
		myFfmpegController.execFFMPEG(cmd, sc);
		
	}
}
