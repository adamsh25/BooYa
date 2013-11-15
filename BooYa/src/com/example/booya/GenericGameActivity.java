package com.example.booya;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class GenericGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO check what game are we in
		setContentView(R.layout.activity_maze_game_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generic_game, menu);
		return true;
	}
	
	public void launchGame(View view) {
		//TODO check what game are we in
	    Intent intent = new Intent(this,MazeGameActivity.class);
	    startActivity(intent);
	}

}
