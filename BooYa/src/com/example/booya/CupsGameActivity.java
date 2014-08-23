package com.example.booya;

import gif.decoder.GifRun;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceView;

public class CupsGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cups_game);
		SurfaceView v = (SurfaceView) findViewById(R.id.surfaceView1);
		GifRun r = new GifRun();
		r.LoadGiff(v, this, R.drawable.change_to_cups_game_gif);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cups_game, menu);
		return true;
	}

}
