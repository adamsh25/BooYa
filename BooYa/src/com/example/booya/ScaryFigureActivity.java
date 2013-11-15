package com.example.booya;

import com.example.booya.recording.GameActivity;
import com.example.booya.recording.RecorderService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ScaryFigureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scary_figure);
		stopRecording();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scary_figure, menu);
		return true;
	}
	
	public void stopRecording()
	{
		Intent intent = new Intent(this, RecorderService.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		stopService(intent);
	}

}
