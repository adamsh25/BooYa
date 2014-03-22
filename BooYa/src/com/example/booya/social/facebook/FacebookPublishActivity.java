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
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Video;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.io.File;

/**
 * User: ronlut
 * Date: 23/11/13 15:03
 */
public class FacebookPublishActivity extends Activity {
    private final String TAG = FacebookPublishActivity.class.getName();

    private SimpleFacebook mSimpleFacebook;

    private Button mButtonLogin;
    private Button mButtonLogout;
    private TextView mTextStatus;
    private Button mButtonPublish;

    // Login listener
    private OnLoginListener mOnLoginListener = new OnLoginListener()
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
        public void onNotAcceptingPermissions(Permission.Type type) {
            toast("You didn't accept read permissions");
        }
    };

    // Logout listener
    private OnLogoutListener mOnLogoutListener = new OnLogoutListener()
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
        
        setContentView(R.layout.activity_facebook_publish);

        initUI();

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
        OnPublishListener onPublishListener = new OnPublishListener()
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
        Video videoObj = new Video.Builder()
        .setVideo(new File("/sdcard/dummy.mp4"))
        .setDescription("Dummy Description #hashtag")
        .setName("Dummy Title")
        .build();

        // publish video to Videos album
        mSimpleFacebook.publish(videoObj, onPublishListener);
    }

    private void initUI()
    {
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
        //mButtonLogin.setEnabled(false);
        //mButtonLogout.setEnabled(true);
        mButtonPublish.setEnabled(true);
        mTextStatus.setText("Logged in");
    }

    private void loggedOutUIState()
    {
        //mButtonLogin.setEnabled(true);
        //mButtonLogout.setEnabled(false);
        mButtonPublish.setEnabled(false);
        mTextStatus.setText("Logged out");
    }

    private void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}