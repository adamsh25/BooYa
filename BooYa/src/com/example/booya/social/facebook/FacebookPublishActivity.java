package com.example.booya.social.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.booya.R;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Privacy;
import com.sromku.simple.fb.entities.Video;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.io.File;

/**
 * User: ronlut
 * Date: 23/11/13 15:03
 */
public class FacebookPublishActivity extends Activity {
    private final String TAG = FacebookPublishActivity.class.getName();

    private SimpleFacebook mSimpleFacebook;

    // Login listener
    private OnLoginListener mOnLoginListener = new OnLoginListener()
    {
        @Override
        public void onFail(String reason)
        {
            toast(reason);
            Log.w(TAG, "Failed to login: " + reason);
            finish();
        }

        @Override
        public void onException(Throwable throwable)
        {
            toast("Exception: " + throwable.getMessage());
            Log.e(TAG, "Bad thing happened", throwable);
            finish();
        }

        @Override
        public void onThinking()
        {
            // show progress bar or something to the user while login is happening
//            toast("Thinking...");
        }

        @Override
        public void onLogin()
        {
            // change the state of the button or do whatever you want
//            toast("Succesfully logged in");
        }

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            toast(String.format("You didn't accept %s permissions", type.name()));
            finish();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_publish);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        if (mSimpleFacebook.isLogin()) {
            ((Button) findViewById(R.id.loginButton)).setEnabled(false);
            ((Button) findViewById(R.id.publishButton)).setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    }

    public void publishDummyVideo(View view) {
        // create publish listener
        OnPublishListener onPublishListener = new OnPublishListener()
        {
            @Override
            public void onFail(String reason)
            {
                // insure that you are logged in before publishing
                Log.w(TAG, reason);
                toast(String.format("Failed to publish, reason: %s", reason));
                finish();
            }

            @Override
            public void onException(Throwable throwable)
            {
                Log.e(TAG, "Bad thing happened", throwable);
                finish();
            }

            @Override
            public void onThinking()
            {
                // show progress bar or something to the user while publishing
//                Log.i(TAG, "In progress");
            }

            @Override
            public void onComplete(String id)
            {
                Log.d(TAG, "Published successfully. id = " + id);
                toast("Successfully published!");
                finish();
            }
        };

        Privacy privacy = new Privacy.Builder().setPrivacySettings(Privacy.PrivacySettings.SELF).build();

        // create Video instace and add some properties
        Video videoObj = new Video.Builder()
                .setVideo(new File("/sdcard/dummy.mp4"))
                .setDescription("Download #BooYa now: https://play.google.com/store/apps/details?id=com.example.booya") //TODO: change to real url!
//                .setName("I scared the hell out of *friend's name*!") //title
                .setName("New grave in my BooYa graveyard!") //title
                .setPrivacy(privacy) //TODO: remove
                .build();

        // publish video to Videos album
        mSimpleFacebook.publish(videoObj, onPublishListener);
    }

    private void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        mSimpleFacebook.login(mOnLoginListener);
    }
}