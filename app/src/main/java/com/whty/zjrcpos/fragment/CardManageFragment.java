package com.whty.zjrcpos.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.whty.zjrcpos.R;
import com.whty.zjrcpos.activity.PersonalizedInfoWritingHYPosActivity;
import com.whty.zjrcpos.adapter.CardManageAdapter;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class CardManageFragment extends Fragment {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.tv_title_center)
    TextView mTvTitleCenter;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.gridview)
    MyGridView mGridview;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.card_manage_fragment, container, false);
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
        mGridview.setAdapter(new CardManageAdapter(getActivity()));
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //收款
                    case 0:
//                        ToastUtils.toast(getActivity(), "开卡");
//                        startOtherAct(PersonalizedInfoWritingActivity.class);
                        startOtherAct(PersonalizedInfoWritingHYPosActivity.class);
                        break;
                    //撤销
                    case 1:
                        ToastUtils.toast(getActivity(), "销卡");
                        break;
                    //挂失/解挂
                    case 2:
                        ToastUtils.toast(getActivity(), "挂失/解挂");
                        break;
                    //挂失换临时卡
                    case 3:
                        ToastUtils.toast(getActivity(), "挂失换临时卡");
//                        startOtherAct(SystemSettingActivity.class);
                        break;
                    //临时卡换正式卡
                    case 4:
                        ToastUtils.toast(getActivity(), "临时卡换正式卡");
                        break;
                    //卡片信息查询
                    case 5:
                        ToastUtils.toast(getActivity(), "卡片信息查询");
                        break;
                    //黑名单查询
                    case 6:
                        ToastUtils.toast(getActivity(), "黑名单查询");
                        break;
                    //黑名单下发
                    case 7:
                        ToastUtils.toast(getActivity(), "黑名单下发");
                        break;
                    //黑名单删除
                    case 8:
                        ToastUtils.toast(getActivity(), "黑名单删除");
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
