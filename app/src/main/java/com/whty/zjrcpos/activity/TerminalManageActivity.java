package com.whty.zjrcpos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whty.zjrcpos.R;
import com.whty.zjrcpos.base.AppActivityManager;
import com.whty.zjrcpos.base.BaseActivity;
import com.whty.zjrcpos.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
* @function TerminalManageActivity
* @author wulimin
* @time 2017/3/31 下午12:03
*/
public class TerminalManageActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.ll_pwd_modify)
    LinearLayout mLlPwdModify;
    @BindView(R.id.ll_operate_manage)
    LinearLayout mLlOperateManage;
    @BindView(R.id.ll_recent_set)
    LinearLayout mLlRecentSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_manage);
        setBackView();
        setTitle("终端管理");
        AppActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.ll_pwd_modify, R.id.ll_operate_manage, R.id.ll_recent_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            //修改密码
            case R.id.ll_pwd_modify:
                AppActivityManager.getInstance().startOtherAct(this,ModifyPwdActivity.class);
                break;
            //操作管理
            case R.id.ll_operate_manage:
                ToastUtils.toast(TerminalManageActivity.this, "操作管理");
                break;
            //近期设置
            case R.id.ll_recent_set:
                ToastUtils.toast(TerminalManageActivity.this, "近期设置");
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.getInstance().removeActivity(this);
    }
}
