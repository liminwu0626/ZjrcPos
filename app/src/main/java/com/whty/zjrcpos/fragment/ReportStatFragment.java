package com.whty.zjrcpos.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.whty.zjrcpos.adapter.ReportStatAdapter;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wulimin
 * @function ReportStatFragment 报表统计
 * @time 2017/3/8 下午2:03
 */

public class ReportStatFragment extends Fragment {

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
            Log.e("666", "ReportStatFragment");
            mRootView = inflater.inflate(R.layout.report_stat_fragment, container, false);
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
        mGridview.setAdapter(new ReportStatAdapter(getActivity()));
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //发卡统计报表
                    case 0:
                        ToastUtils.toast(getActivity(), "发卡统计报表");
//                        startOtherAct(GatheringMoneyStyleActivity.class);
                        break;
                    //差错调账报表
                    case 1:
                        ToastUtils.toast(getActivity(), "差错调账报表");
                        break;
                    //营业统计报表
                    case 2:
                        ToastUtils.toast(getActivity(), "营业统计报表");
                        break;
                    //出入统计报表
                    case 3:
                        ToastUtils.toast(getActivity(), "出入统计报表");
//                        startOtherAct(SystemSettingActivity.class);
                        break;
                    //操作员日志查询
                    case 4:
                        ToastUtils.toast(getActivity(), "操作员日志查询");
//                        startOtherAct(SystemSettingActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 跳转至指定的Activity
     *
     * @param targetActivity
     */
    private void startOtherAct(Class<? extends Activity> targetActivity) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), targetActivity);
        startActivity(intent);
    }
}
