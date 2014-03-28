package com.example.booya;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainBooyaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BooyaUser.loadInfo(this);
		
		setContentView(R.layout.activity_main_booya);

		((TextView)(findViewById(R.id.txtVictims))).setText("Victims : " + BooyaUser.GetNumberOfVictims());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_booya, menu);

		return true;
	}
	
	public void launchOptions(View view) {
	    Intent intent = new Intent(this, OptionsActivity.class);
	    startActivity(intent);
	}
	
	public void launchGameModuleChooser(View view) {
	    Intent intent = new Intent(this, GameModuleChooserActivity.class);
	    startActivity(intent);
	}
	
	public void launchMorgue(View view) {
	    Intent intent = new Intent(this, MorgueActivity.class);
	    startActivity(intent);
	}
	
	public void launchStore(View view) {
	    Intent intent = new Intent(this, StoreActivity.class);
	    startActivity(intent);
	}
	
	public void launchAbout(View view) {
	    Intent intent = new Intent(this,AboutActivity.class);
	    startActivity(intent);
	}

}
