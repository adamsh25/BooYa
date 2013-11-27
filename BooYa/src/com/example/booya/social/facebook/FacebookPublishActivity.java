package com.example.booya.social.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.booya.R;
import com.facebook.*;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.entities.Video;

import java.io.File;

/**
 * User: Rony
 * Date: 23/11/13 15:03
 */
public class FacebookPublishActivity extends Activity {
    private static final String FACEBOOK_APP_ID = "696649103679042";
    private static final String FACEBOOK_APP_NAMESPACE = "booya-app";
    private static final SessionDefaultAudience FACEBOOK_DEFAULT_AUDIENCE = SessionDefaultAudience.EVERYONE;
    private static final String TAG = FacebookPublishActivity.class.getName();

    private SimpleFacebook mSimpleFacebook;

    private Button mButtonLogin;
    private Button mButtonLogout;
    private TextView mTextStatus;
    private Button mButtonPublish;

    private final Permissions[] neededPermissions = new Permissions[]
            {
                    Permissions.PUBLISH_STREAM
            };

    // Login listener
    private SimpleFacebook.OnLoginListener mOnLoginListener = new SimpleFacebook.OnLoginListener()
    {

        @Override
        public void onFail(String reason)
        {
            mTextStatus.setText(reason);
            Log.w(TAG, "Failed to login");
        }

        @Override
        public void onException(Throwable throwable)
        {
            mTextStatus.setText("Exception: " + throwable.getMessage());
            Log.e(TAG, "Bad thing happened", throwable);
        }

        @Override
        public void onThinking()
        {
            // show progress bar or something to the user while login is happening
            mTextStatus.setText("Thinking...");
        }

        @Override
        public void onLogin()
        {
            // change the state of the button or do whatever you want
            mTextStatus.setText("Logged in");
            loggedInUIState();
        }

        @Override
        public void onNotAcceptingPermissions()
        {
            toast("You didn't accept read permissions");
        }
    };

    // Logout listener
    private SimpleFacebook.OnLogoutListener mOnLogoutListener = new SimpleFacebook.OnLogoutListener()
    {

        @Override
        public void onFail(String reason)
        {
            mTextStatus.setText(reason);
            Log.w(TAG, "Failed to login");
        }

        @Override
        public void onException(Throwable throwable)
        {
            mTextStatus.setText("Exception: " + throwable.getMessage());
            Log.e(TAG, "Bad thing happened", throwable);
        }

        @Override
        public void onThinking()
        {
            // show progress bar or something to the user while login is happening
            mTextStatus.setText("Thinking...");
        }

        @Override
        public void onLogout()
        {
            // change the state of the button or do whatever you want
            mTextStatus.setText("Logged out");
            loggedOutUIState();
            toast("You are logged out");
        }

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(FACEBOOK_APP_ID)
                .setNamespace(FACEBOOK_APP_NAMESPACE)
                .setPermissions(neededPermissions)
                .setDefaultAudience(FACEBOOK_DEFAULT_AUDIENCE)
                .build();
        SimpleFacebook.setConfiguration(configuration);

        setContentView(R.layout.activity_facebook_publish);

        initUI();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSimpleFacebook.login(mOnLoginListener);
            }
        });

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSimpleFacebook.logout(mOnLogoutListener);
            }
        });

        mButtonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishDummyVideo();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        setUIState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    }

    private void publishDummyVideo() {
        // create publish listener
        SimpleFacebook.OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
        {

            @Override
            public void onFail(String reason)
            {
                // insure that you are logged in before publishing
                Log.w(TAG, reason);
            }

            @Override
            public void onException(Throwable throwable)
            {
                Log.e(TAG, "Bad thing happened", throwable);
            }

            @Override
            public void onThinking()
            {
                // show progress bar or something to the user while publishing
                Log.i(TAG, "In progress");
            }

            @Override
            public void onComplete(String id)
            {
                Log.i(TAG, "Published successfully. id = " + id);
            }
        };

        // create Video instace and add some properties
        Video videoObj = new Video(new File("/sdcard/dummy.mp4"));
        videoObj.addDescription("Dummy Description");
        videoObj.addTitle("Dummy Title");


        // publish video to Videos album
        mSimpleFacebook.publish(videoObj, onPublishListener);
    }

    private void initUI()
    {
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonLogout = (Button)findViewById(R.id.button_logout);
        mTextStatus = (TextView)findViewById(R.id.text_status);
        mButtonPublish = (Button)findViewById(R.id.publishButton);
    }

    private void setUIState()
    {
        if (mSimpleFacebook.isLogin())
        {
            loggedInUIState();
        }
        else
        {
            loggedOutUIState();
        }
    }

    private void loggedInUIState()
    {
        mButtonLogin.setEnabled(false);
        mButtonLogout.setEnabled(true);
        mButtonPublish.setEnabled(true);
        mTextStatus.setText("Logged in");
    }

    private void loggedOutUIState()
    {
        mButtonLogin.setEnabled(true);
        mButtonLogout.setEnabled(false);
        mButtonPublish.setEnabled(false);
        mTextStatus.setText("Logged out");
    }

    private void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}