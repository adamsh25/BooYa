package com.example.booya.recording;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class RecorderService extends Service {
	private static final String TAG = "RecorderService";
	private static final String DEBUG_TAG = "RecorderService";
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private static Camera mServiceCamera;
	private boolean mRecordingStatus;
	private MediaRecorder mMediaRecorder;
	
	@Override
	public void onCreate() {
		mRecordingStatus = false;
		mServiceCamera = CameraRecorder.mCamera;
		mSurfaceView = CameraRecorder.mSurfaceView;
		mSurfaceHolder = CameraRecorder.mSurfaceHolder;
		
		
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		if (mRecordingStatus == false)
			startRecording();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		stopRecording();
		mRecordingStatus = false;
		
		super.onDestroy();
	}   

	public boolean startRecording(){
		try {
			
			//Toast.makeText(getBaseContext(), "Recording Started", Toast.LENGTH_SHORT).show();
			
			mServiceCamera = Camera.open(findFrontFacingCamera());
			
			mMediaRecorder = new MediaRecorder();
			
			mServiceCamera.unlock();
			
			mMediaRecorder.setCamera(mServiceCamera);
//			Camera.Parameters params = mServiceCamera.getParameters();
//			mServiceCamera.setParameters(params);
//			Camera.Parameters p = mServiceCamera.getParameters();
//			
//			final List<Size> listSize = p.getSupportedPreviewSizes();
//			Size mPreviewSize = listSize.get(2);
//			Log.v(TAG, "use: width = " + mPreviewSize.width 
//						+ " height = " + mPreviewSize.height);
//			p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//			p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
//			mServiceCamera.setParameters(p);

			
			
			
			
			
			
			
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mMediaRecorder.setProfile(CamcorderProfile.get(findFrontFacingCamera(), CamcorderProfile.QUALITY_HIGH));
			mMediaRecorder.setOutputFile("/sdcard/video.mp4");
			//mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			//mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			//mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
			
			mMediaRecorder.setVideoFrameRate(15);
			//mMediaRecorder.setVideoSize(mPreviewSize.width, mPreviewSize.height);
			mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
			
//			try {
//				mServiceCamera.setPreviewDisplay(mSurfaceHolder);
//				mServiceCamera.startPreview();
//			}
//			catch (IOException e) {
//				Log.e(TAG, e.getMessage());
//				e.printStackTrace();
//			}
			
			mMediaRecorder.prepare();
			mMediaRecorder.start(); 

			mRecordingStatus = true;
			
			return true;
		} catch (IllegalStateException e) {
			Log.d(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			Log.d(TAG, ex.getMessage());
			ex.printStackTrace();
			
			try
			{
			mServiceCamera.reconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			try
			{
				mServiceCamera.stopPreview();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			try
			{
				mMediaRecorder.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			try
			{
				mServiceCamera.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mServiceCamera = null;
			
			return false;
		}
		finally
		{
			
		}
	}
	
	@SuppressLint("NewApi")
	private static int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(DEBUG_TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	public void stopRecording() {
		//Toast.makeText(getBaseContext(), "Recording Stopped", Toast.LENGTH_SHORT).show();
		try {
			mServiceCamera.reconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mMediaRecorder.stop();
		mMediaRecorder.reset();
		
		mServiceCamera.stopPreview();
		mMediaRecorder.release();
		
		mServiceCamera.release();
		mServiceCamera = null;
	}
}
