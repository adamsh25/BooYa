package com.example.booya.video.processing;

import android.app.IntentService;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.example.booya.video.processing.ShellUtils.ShellCallback;


public class BooyaFFMPEG extends IntentService {
	private static String BOOYA_WATERMARK_FILE_PATH = "/sdcard/videokit/file1.png";
	private static String BOOYA_MAZE_SAMPE_FILE_PATH = "/sdcard/videokit/in2.mp4";
	private static String BOOYA_TEMP_VIDEO_FILE_PATH = "/sdcard/videokit/currtmp.mp4";
    final String TAG = "BooyaFFMPEG";

    private String ffmpegBin;
    private FfmpegController myFfmpegController;
    private ShellCallback sc;
    private BooyaFFMPEG booya_ffmpeg_instance;

    public BooyaFFMPEG() {
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
        try
        {
            myFfmpegController = new FfmpegController(getApplicationContext());
            ffmpegBin = myFfmpegController.GetFfmpegBin();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sc = new ShellCallback() {

            @Override
            public void shellOut(String shellLine) {
                // TODO Auto-generated method stub
                Log.d(TAG, shellLine);
            }

            @Override
            public void processComplete(int exitValue) {
                // TODO Auto-generated method stub
                Log.d(TAG, String.valueOf(exitValue));
            }

        };

        try {
            CreateBooyaVideo("/sdcard/video.mp4", "/sdcard/outFull.mp4"); //TODO: take data from intent
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

/*    public BooyaFFMPEG getInstance()
    {
        if(booya_ffmpeg_instance)
        {
            return booya_ffmpeg_instance;
        }
        else
        {
    }*/

	private void CreateBooyaVideo(String inputCameraRawVideoFilePath, String outputBooyaVideoFilePath) throws IOException, InterruptedException
	{
        Log.i(TAG, "Starting ffmpeg processing");
        Log.d(TAG, "Stage 1");
        InsertSampleGameIntoCameraVideo(inputCameraRawVideoFilePath, BOOYA_TEMP_VIDEO_FILE_PATH);
        Log.d(TAG, "Stage 2");
        InsertBooyaWatermark(BOOYA_TEMP_VIDEO_FILE_PATH,outputBooyaVideoFilePath);
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
