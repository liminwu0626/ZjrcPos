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
import com.whty.zjrcpos.adapter.AccountTransAdapter;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wulimin
 * @function AccountTransFragment 账户交易
 * @time 2017/3/8 下午2:02
 */

public class AccountTransFragment extends Fragment {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.gridview)
    MyGridView mGridview;
    View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "AccountTransFragment");
            mRootView = inflater.inflate(R.layout.account_trans_fragment, container, false);
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
        mGridview.setAdapter(new AccountTransAdapter(getActivity()));
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //账户充值
                    case 0:
                        ToastUtils.toast(getActivity(), "账户充值");
//                        startOtherAct(GatheringMoneyStyleActivity.class);
                        break;
                    //余额查询
                    case 1:
                        ToastUtils.toast(getActivity(), "余额查询");
                        break;
                    //挂失充值
                    case 2:
                        ToastUtils.toast(getActivity(), "挂失充值");
                        break;
                    //补助发放
                    case 3:
                        ToastUtils.toast(getActivity(), "补助发放");
//                        startOtherAct(SystemSettingActivity.class);
                        break;
                    //圈存
                    case 4:
                        ToastUtils.toast(getActivity(), "圈存");
//                        startOtherAct(SystemSettingActivity.class);
                        break;
                    //脱机清算
                    case 5:
                        ToastUtils.toast(getActivity(), "脱机清算");
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
