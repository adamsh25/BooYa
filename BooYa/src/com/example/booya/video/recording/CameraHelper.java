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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//documentation: http://developer.android.com/guide/topics/media/camera.html

public class CameraHelper {
	private static final CameraHelper INSTANCE = new CameraHelper();
	private final String TAG = CameraHelper.class.getSimpleName();
	Surface previewSurface;
    //SurfaceHolder surfaceHolder;
	private Camera camera;
	private MediaRecorder recorder;
	private int frontFacingCameraId;
	public boolean isRecording = false;
    private String currentFileName;

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
        //surfaceHolder = view.getHolder();
	}

    public boolean hasFrontFacingCamera() {
        return frontFacingCameraId != -1;
    }

	/**
	 * SetSurfaceView(view) must be called before!
	 * Starts recording if hasn't already.
	 */
	public void StartRecordingOld() {
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

    public synchronized void OpenCamera() {
        if (isRecording) {
            Log.e(TAG, "OpenCamera() called while recording! returning.");
            return;
        }

        if (camera == null) {
            try {
                camera = Camera.open(frontFacingCameraId);
                Log.d(TAG, "Camera opened");
            } catch (RuntimeException re) {
                Log.e(TAG, "Couldn't open camera.");
                re.printStackTrace();
            }
        } else {
            Log.d(TAG, "Won't open camera [camera != null], opened already?");
        }
    }

    public synchronized void ReleaseCamera() {
        if (isRecording) {
            Log.e(TAG, "ReleaseCamera() called while recording! returning.");
            return;
        }

        if (camera != null) {
            camera.release(); // release the camera for other applications
            Log.d(TAG, "Camera released");
            camera = null;
        } else {
            Log.d(TAG, "Won't release camera [camera == null], already released?");
        }
    }

    private boolean PrepareVideoRecorder() {
//        try {
//            camera.setPreviewDisplay(h); //TODO: maybe move to OpenCamera()
//        } catch (IOException e) {
//            Log.e(TAG, "Could not set preview display");
//            e.printStackTrace();
//            return false;
//        }

        recorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        recorder.setCamera(camera);

        // Step 2: Set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER); // TODO: fix error on LG p999 (http://stackoverflow.com/questions/21014399/mediarecorder-start-failed-2147483648-when-call-recording-in-android)
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        recorder.setProfile(CamcorderProfile.get(frontFacingCameraId,
                CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        currentFileName = "/sdcard/video.mp4"; //TODO: change to dir from db + save with unique name like (date_time_orig)
        recorder.setOutputFile(currentFileName);

        //TODO: NEEDED?
        //recorder.setVideoFrameRate(15);

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

            try {
                //camera.lock(); // lock camera for later use
                camera.reconnect();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't reconnect to camera after releasing media recorder");
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Won't release media recorder [recorder == null], already released?");
        }
    }

    /**
     * SetSurfaceView(view) must be called before!
     * Starts recording if hasn't already.
     */
    public synchronized void StartRecording() {
        if (isRecording) {
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
    }

    public String StopRecording() {
        if (!isRecording) {
            Log.d(TAG, "Asked to stop, but already stopped");
            return null;
        }

        try {
            recorder.stop();
        } catch (RuntimeException re) {
            Log.d(TAG, "Probably stop was called right after start, exception: " + re.getMessage());
            currentFileName = null;
        }

        String fileName = currentFileName;
        currentFileName = null;

        ReleaseMediaRecorder();
        Log.i(TAG, "Stopped recording");
        isRecording = false;

        return fileName;
    }

	public void StopRecordingOld() {
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

	private int findFrontFacingCameraId() {
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
