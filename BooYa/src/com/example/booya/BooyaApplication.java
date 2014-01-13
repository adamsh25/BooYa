package com.example.booya;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import android.app.Application;

public class BooyaApplication extends Application {
	private static BooyaApplication singleton;
	
	// TODO: not hardcoded
	private final String FACEBOOK_APP_ID = "696649103679042";
    private final String FACEBOOK_APP_NAMESPACE = "booya-app";
    private final SessionDefaultAudience FACEBOOK_DEFAULT_AUDIENCE = SessionDefaultAudience.FRIENDS;
    private final Permissions[] NEEDED_PERMISSIONS = new Permissions[]
            {
                    Permissions.PUBLISH_ACTION
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
		.setAppId(FACEBOOK_APP_ID)
		.setNamespace(FACEBOOK_APP_NAMESPACE)
		.setPermissions(NEEDED_PERMISSIONS)
		.setDefaultAudience(FACEBOOK_DEFAULT_AUDIENCE)
		.build();
		
		SimpleFacebook.setConfiguration(configuration);
	}
}
