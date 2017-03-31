package com.whty.zjrcpos.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whty.zjrcpos.R;
import com.whty.zjrcpos.base.AppActivityManager;
import com.whty.zjrcpos.base.BaseActivity;
import com.whty.zjrcpos.util.StringUtil;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.whty.zjrcpos.R.id.tv_title_center;

/**
 * @author wulimin
 * @function ModifyPwdActivity
 * @time 2017/3/31 下午12:21
 */


public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {
    String oldPasswordStr = "", newPasswordStr = "", confirmNewPasswordStr = "";
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.et_oldPassword)
    ClearEditText mEtOldPassword;
    @BindView(R.id.et_newPassword)
    ClearEditText mEtNewPassword;
    @BindView(R.id.et_confirmNewPassword)
    ClearEditText mEtConfirmNewPassword;
    @BindView(R.id.btn_pwd_confirm)
    Button mBtnPwdConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_modify_layout);
        setBackView();
        setTitle("密码修改");
        AppActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }

    private void goEditPassword() {
        oldPasswordStr = mEtOldPassword.getText().toString().trim();
        newPasswordStr = mEtNewPassword.getText().toString().trim();
        confirmNewPasswordStr = mEtConfirmNewPassword.getText().toString().trim();
        if (StringUtil.isEmpty(oldPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "请输入原先的密码！");
            return;
        }
        if (!StringUtil.pwdCheck(oldPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "密码应为6到20位的数字和字母的组合！");
            return;
        }
        if (StringUtil.isEmpty(newPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "请输入新密码！");
            return;
        }
        if (!StringUtil.pwdCheck(newPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "密码应为6到20位的数字和字母的组合！");
            return;
        }
        if (StringUtil.isEmpty(confirmNewPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "请再次输入新密码！");
            return;
        }
        if (!StringUtil.pwdCheck(confirmNewPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "密码应为6到20位的数字和字母的组合！");
            return;
        }
        if (!confirmNewPasswordStr.equals(newPasswordStr)) {
            ToastUtils.toast(ModifyPwdActivity.this, "两次输入的新密码不一致！");
            return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.getInstance().removeActivity(this);
    }

    @OnClick({R.id.back, R.id.btn_pwd_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_pwd_confirm:
                goEditPassword();
                break;
        }
    }
}
