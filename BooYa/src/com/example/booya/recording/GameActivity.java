package com.example.booya.recording;

import com.example.booya.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		Intent intent = new Intent(GameActivity.this, RecorderService.class);
		startService(intent);
		
		// Here or in Camera Recorder
		// TODO: Replace with 
		Button btnStop = (Button) findViewById(R.id.btnMainBooya);
		btnStop.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(GameActivity.this, RecorderService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				stopService(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
