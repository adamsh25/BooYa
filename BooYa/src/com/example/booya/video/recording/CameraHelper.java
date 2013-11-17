package com.example.booya.video.recording;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

public class CameraHelper {
	private final static String TAG = "CameraHelper";
	private Surface previewSurface;
	private Camera camera;
	private MediaRecorder recorder;
	private int frontFacingCameraId;
	public boolean isRecording;

	public CameraHelper(SurfaceView view) {
		previewSurface = view.getHolder().getSurface();
		frontFacingCameraId = findFrontFacingCameraId();
	}

	public void StartRecording() {
		camera = Camera.open(frontFacingCameraId);
		camera.unlock();
		recorder = new MediaRecorder();
		//camera.lock();
		recorder.setCamera(camera);
		recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		//recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setProfile(CamcorderProfile.get(frontFacingCameraId,
				CamcorderProfile.QUALITY_HIGH));
		recorder.setOutputFile("/sdcard/video.mp4"); // TODO: change to 
		recorder.setVideoFrameRate(15);
		recorder.setPreviewDisplay(previewSurface);

		if (android.os.Build.VERSION.SDK_INT >= 9) {
			// attempt to rotate the video 90 degrees.
			try {
				recorder.setOrientationHint(270);
				Log.d(TAG, "orientation rotated 270");
			} catch (IllegalArgumentException e) {
				// TODO: couldn't set angle
				Log.d(TAG, "error trying setOrientationHint" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "orientation set skipped "); //TODO: schedule mp4 rotation with mp4parse/ffmpeg
		}
		
		try {
		recorder.prepare();
		recorder.start(); 
		} catch (IllegalStateException ise) {
			
		} catch (IOException ioe) {
			Log.d(TAG, "prepare failed");
			ioe.printStackTrace();
		}
		
		isRecording = true;
	}

	public void StopRecording() {
		recorder.stop();
		recorder.reset();
		recorder.release();
		camera.stopPreview();
		recorder.release();
		recorder = null;
		camera.release();
		
		isRecording = false;
	}

	public static int findFrontFacingCameraId() {
		int cameraId = -1;

		// Search for a front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();

		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);

			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(TAG, "Camera found");
				cameraId = i;
				break;
			}
		}

		return cameraId;
	}
}
