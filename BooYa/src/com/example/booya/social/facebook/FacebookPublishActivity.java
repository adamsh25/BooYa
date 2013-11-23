package com.example.booya.social.facebook;

import android.app.Activity;
import android.os.Bundle;
import com.example.booya.R;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

/**
 * User: Rony
 * Date: 23/11/13 15:03
 */
public class FacebookPublishActivity extends Activity {
    private static final String PERMISSION = "publish_actions";

    private UiLifecycleHelper uiHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_facebook_publish);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
    }

    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
    }
}