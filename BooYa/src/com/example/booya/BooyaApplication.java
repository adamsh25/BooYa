package com.example.booya;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import android.app.Application;

//TODO: move to publish activity?

public class BooyaApplication extends Application {
	private static BooyaApplication singleton;

    private final Permission[] NEEDED_PERMISSIONS = new Permission[]
            {
                    Permission.PUBLISH_STREAM
            };

	public BooyaApplication getInstance() {
		return singleton;
	}

	public void onCreate() {
		super.onCreate();
		singleton = this;

		configureSimpleFacebook();
	}

	private void configureSimpleFacebook() {
		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
		.setAppId(this.getString(R.string.facebook_app_id))
		.setNamespace(this.getString(R.string.facebook_app_ns))
		.setPermissions(NEEDED_PERMISSIONS)
		.build();

		SimpleFacebook.setConfiguration(configuration);
	}
}
