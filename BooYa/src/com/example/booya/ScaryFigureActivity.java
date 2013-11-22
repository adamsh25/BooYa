package com.example.booya;

import com.example.booya.recording.GameActivity;
import com.example.booya.recording.RecorderService;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ScaryFigureActivity extends Activity {
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scary_figure);
		
		playScarySound();
		// Check if have front camera
		if(TesterActivity.bHasFrontCamera)
		{
			stopRecording();
		}
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
		Intent intent = new Intent(this, RecorderService.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		stopService(intent);
	}
	
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

}
