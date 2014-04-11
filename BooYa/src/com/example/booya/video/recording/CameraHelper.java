package com.example.booya.video.recording;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

//documentation: http://developer.android.com/guide/topics/media/camera.html

public class CameraHelper {
	private static final CameraHelper INSTANCE = new CameraHelper();
	private final String TAG = "CameraHelper";
	Surface previewSurface;
	private Camera camera;
	private MediaRecorder recorder;
	private int frontFacingCameraId;
	public boolean isRecording = false;
    private final Semaphore lock = new Semaphore(1);

	public CameraHelper() {
		frontFacingCameraId = findFrontFacingCameraId();
	}

	public static CameraHelper getInstance() {
		return INSTANCE;
	}

	/***
	 * Must be called before StartRecording()!
	 * Sets the preview surface.
	 * @param view the preview surface
	 */
	public void SetSurfaceView(SurfaceView view) {
		previewSurface = view.getHolder().getSurface();
	}

	/**
	 * SetSurfaceView(view) must be called before!
	 * Starts recording if hasn't already.
	 */
	public void StartRecording() {
		if (isRecording) {
            Log.d(TAG, "Asked to start, but already recording");
            return;
        }
		
		camera = Camera.open(frontFacingCameraId);
		camera.unlock();
		recorder = new MediaRecorder();
		// camera.lock();
		recorder.setCamera(camera);
		recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		// recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setProfile(CamcorderProfile.get(frontFacingCameraId,
				CamcorderProfile.QUALITY_HIGH));
		recorder.setOutputFile("/sdcard/video.mp4"); // TODO: change to dir from db
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
			Log.d(TAG, "orientation set skipped "); // TODO: schedule mp4 rotation with mp4parse/ffmpeg
		}

		try {
			recorder.prepare();
			recorder.start();
            Log.i(TAG, "Started recording");
            isRecording = true;
		} catch (IllegalStateException ise) {

		} catch (IOException ioe) {
			Log.d(TAG, "prepare failed");
			ioe.printStackTrace();
		}
	}

    public void OpenCamera() {
        lock.acquireUninterruptibly();

        if (camera == null) {
            camera = Camera.open(frontFacingCameraId);
            Log.d(TAG, "Camera opened");
        }

        lock.release();
    }

    public void ReleaseCamera() {
        lock.acquireUninterruptibly();

        if (camera != null) {
            camera.release();        // release the camera for other applications
            Log.d(TAG, "Camera released");
            camera = null;
        }

        lock.release();
    }

    private boolean PrepareVideoRecorder() {
        recorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        recorder.setCamera(camera);

        // Step 2: Set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        recorder.setProfile(CamcorderProfile.get(frontFacingCameraId,
                CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        recorder.setOutputFile("/sdcard/video.mp4"); // TODO: change to dir from db

        //TODO: NEEDED?
        recorder.setVideoFrameRate(15);

        // Step 5: Set the preview output
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
            Log.d(TAG, "orientation set skipped "); // TODO: schedule mp4 rotation with mp4parse/ffmpeg
        }

        // Step 6: Prepare configured MediaRecorder
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            ReleaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            ReleaseMediaRecorder();
            return false;
        }

        return true;
    }

    private void ReleaseMediaRecorder() {
        if (recorder != null) {
            recorder.reset();   // clear recorder configuration
            recorder.release(); // release the recorder object
            Log.d(TAG, "MediaRecorder released");
            recorder = null;
            camera.lock();          // lock camera for later use
        }
    }

    public void StartRecording2() {
        lock.acquireUninterruptibly();

        if (isRecording) {
            lock.release();
            Log.d(TAG, "Asked to start recording, but already recording");
            return;
        }

        // initialize video camera
        if (PrepareVideoRecorder()) {
            // Camera is available and unlocked, MediaRecorder is prepared,
            // now you can start recording
            recorder.start();
            isRecording = true;
            Log.i(TAG, "Started recording");
        } else {
            // prepare didn't work, release the recorder
            ReleaseMediaRecorder();
        }

        lock.release();
    }

    public void StopRecording2() {
        lock.acquireUninterruptibly();

        if (!isRecording) {
            lock.release();
            Log.d(TAG, "Asked to stop, but already stopped");
            return;
        }

        recorder.stop();
        ReleaseMediaRecorder();
        Log.i(TAG, "Stopped recording");
        isRecording = false;

        lock.release();
    }

	public void StopRecording() {
		if (!isRecording) {
            Log.d(TAG, "Asked to stop, but already stopped");
            return;
        }

        recorder.stop();
        recorder.reset();
        recorder.release();
        camera.stopPreview();
        recorder.release();
        recorder = null;
        camera.release();

        Log.i(TAG, "Stopped recording");
        isRecording = false;
	}

	public int findFrontFacingCameraId() {
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
