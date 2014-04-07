package com.example.booya;


import android.os.Handler;
import android.widget.Toast;
import com.example.booya.video.recording.CameraHelper;
import com.example.booya.video.recording.RecordingService;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

import java.sql.Time;
import java.util.TimerTask;

public class ScaryFigureActivity extends Activity {
    final Runnable runnable  = new Runnable() {
        public void run() {
            Intent i = new Intent(getBaseContext(), TesterActivity.class);
            startActivity(i);
        }
    };

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
		Intent i = new Intent(this, RecordingService.class);
		stopService(i);
	}
	
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        // Check if have front camera
 		if(TesterActivity.bHasFrontCamera)
 		{
 			stopRecording();
 		}
    }

}
