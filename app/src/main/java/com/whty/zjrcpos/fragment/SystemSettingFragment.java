package com.whty.zjrcpos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.whty.zjrcpos.R;
import com.whty.zjrcpos.activity.TerminalManageActivity;
import com.whty.zjrcpos.adapter.SystemSettingAdapter;
import com.whty.zjrcpos.base.AppActivityManager;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wulimin
 * @function SystemSettingFragment 系统设置
 * @time 2017/3/8 下午2:05
 */

public class SystemSettingFragment extends Fragment {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.gridview)
    MyGridView mGridview;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "SystemSettingFragment");
            mRootView = inflater.inflate(R.layout.system_setting_fragment, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mTvTitleCenter.setText(getResources().getString(R.string.app_name));
        mBack.setVisibility(View.INVISIBLE);
        mGridview.setAdapter(new SystemSettingAdapter(getActivity()));
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //终端管理
                    case 0:
//                        ToastUtils.toast(getActivity(), "终端管理");
                        AppActivityManager.getInstance().startOtherAct(getActivity(), TerminalManageActivity.class);
                        break;
                    //操作员管理
                    case 2:
                        ToastUtils.toast(getActivity(), "操作员管理");
                        break;
                }
            }
        });
    }

}
