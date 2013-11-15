package com.example.booya;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class GameModuleChooserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_module_chooser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_module_chooser, menu);
		return true;
	}
	public void launchClassicMazeGame(View view) {
	    Intent intent = new Intent(this, GenericGameActivity.class);
	    startActivity(intent);
	}

}
