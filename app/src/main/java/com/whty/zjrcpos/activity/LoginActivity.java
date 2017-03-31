package com.whty.zjrcpos.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.whty.zjrcpos.R;
import com.whty.zjrcpos.util.DialogUtils;
import com.whty.zjrcpos.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wulimin
 * @function LoginActivity
 * @time 2017/3/1 下午2:24
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.et_login_name)
    EditText mEtLoginName;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private UserLoginTask mAuthTask = null;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        mBack.setVisibility(View.INVISIBLE);
        mTvTitleCenter.setText(getResources().getString(R.string.app_name));
        mBtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtLoginName.setText("");
        mEtLoginPwd.setText("");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid strLoginName, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEtLoginName.setError(null);
        mEtLoginPwd.setError(null);
        // Store values at the time of the login attempt.
        String strLoginName = mEtLoginName.getText().toString();
        String strLoginPwd = mEtLoginPwd.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid strLoginName address.
        if (TextUtils.isEmpty(strLoginName)) {
            mEtLoginName.setError(getString(R.string.error_field_required));
            focusView = mEtLoginName;
            cancel = true;
        }
        if (TextUtils.isEmpty(strLoginPwd)) {
            mEtLoginPwd.setError(getString(R.string.error_pwd_required));
            focusView = mEtLoginPwd;
            cancel = true;
        }
//        else if (!isEmailValid(strLoginName)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            DialogUtils.showProgressDialog(LoginActivity.this, "", "正在登录中...");
            mAuthTask = new UserLoginTask(strLoginName, strLoginPwd);
            mAuthTask.execute((Void) null);
        }
    }


    private boolean isPasswordValid(String strLoginPwd) {
        //TODO: Replace this with your own logic
        return strLoginPwd.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String strLoginName, String strLoginPwd) {
            mEmail = strLoginName;
            mPassword = strLoginPwd;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            DialogUtils.hideProgressDialog();
            if (success) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
            } else {
                mEtLoginPwd.setError(getString(R.string.error_incorrect_password));
                mEtLoginPwd.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            DialogUtils.hideProgressDialog();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.toast(LoginActivity.this, getResources().getString(R.string.message_click_to_exit_system));
                mExitTime = System.currentTimeMillis();
            } else {
                mExitTime = 0;
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

