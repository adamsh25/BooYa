package com.example.booya;


import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import com.example.booya.video.processing.BooyaFFMPEG;
import com.example.booya.video.recording.CameraHelper;
import com.example.booya.video.recording.RecordingIntentService;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ScaryFigureActivity extends Activity {
    final Runnable runnable  = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            //mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true); //TODO: make it work
            stopRecording(); //TODO: not good
            //mgr.setStreamMute(AudioManager.STREAM_SYSTEM, false); //TODO: make it work
            while(CameraHelper.getInstance().isRecording) { //TODO: consider asynctask or thread
                Log.d("3 sec thread", "waiting for camera to stop..");
            }
            BooyaFFMPEG a = new BooyaFFMPEG(getBaseContext());
            a.execute("a");
            Intent i = new Intent(getBaseContext(), TesterActivity.class);
            startActivity(i);
        }
    };

    private void stopRecording2() {
        CameraHelper.getInstance().StopRecording();
    }

    Thread soundThread = new Thread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    playScarySound();
                    Handler handler = new Handler();
                    handler.postDelayed(runnable, 3000);
                }

            });

    private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scary_figure);

        soundThread.run();
		//playScarySound();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scary_figure, menu);
		return true;
	}
	
	public void playScarySound()
	{
		mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.scary1);
		mediaPlayer.start(); // no need to call prepare(); create() does that for you
	}
	
	public void stopRecording()
	{
//		Intent intent = new Intent(this, RecorderService.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		stopService(intent);
//		CameraHelper.getInstance().StopRecording();
		Intent i = new Intent(this, RecordingIntentService.class);
        i.setAction(RecordingIntentService.STOP_ACTION);
		startService(i);
	}
	
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        stopRecording();
    }

}
