package com.example.booya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;

/**
 * Created by ronlut on 08/12/13.
 */
public class SocialSettingsActivity extends Activity {

    private final String TAG = SocialSettingsActivity.class.getName();

    private SimpleFacebook mSimpleFacebook;
    private Button mLogInOutButton;

    // Login listener
    private OnLoginListener mOnLoginListener = new OnLoginListener()
    {
        @Override
        public void onFail(String reason)
        {
            toast(reason);
            Log.w(TAG, "Failed to login");
        }

        @Override
        public void onException(Throwable throwable)
        {
            toast("Exception: " + throwable.getMessage());
            Log.e(TAG, "Bad thing happened", throwable);
        }

        @Override
        public void onThinking()
        {
            // show progress bar or something to the user while login is happening
            toast("Thinking...");
        }

        @Override
        public void onLogin()
        {
            // change the state of the button or do whatever you want
            toast("Logged in");
            mLogInOutButton.setText("Sign Out");
        }

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            toast(String.format("You didn't accept %s permissions", type.name()));
        }
    };

    // Logout listener
    private OnLogoutListener mOnLogoutListener = new OnLogoutListener()
    {
        @Override
        public void onFail(String reason)
        {
            toast(reason);
            Log.w(TAG, "Failed to login");
        }

        @Override
        public void onException(Throwable throwable)
        {
            toast("Exception: " + throwable.getMessage());
            Log.e(TAG, "Bad thing happened", throwable);
        }

        @Override
        public void onThinking()
        {
            // show progress bar or something to the user while login is happening
            toast("Thinking...");
        }

        @Override
        public void onLogout()
        {
            // change the state of the button or do whatever you want
            toast("Logged out");
            mLogInOutButton.setText("Sign In");
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_settings);

        mSimpleFacebook = SimpleFacebook.getInstance(this);
        initUI();
    }

    private void initUI() {
        mLogInOutButton = (Button) findViewById(R.id.btnFacebookLoginLogout);
        if (mSimpleFacebook.isLogin())
            mLogInOutButton.setText("Sign Out"); // means he's logging out
        else
            mLogInOutButton.setText("Sign In");
    }

    public void onFacebookBtnClick(View view) {
        if (mSimpleFacebook.isLogin()) { // means he's logging out
            mSimpleFacebook.logout(mOnLogoutListener);
        }
        else { // means he's logging in
            mSimpleFacebook.login(mOnLoginListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    }

    private void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}