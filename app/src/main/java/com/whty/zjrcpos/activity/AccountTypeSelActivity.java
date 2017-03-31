package com.whty.zjrcpos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.whty.zjrcpos.R;
import com.whty.zjrcpos.adapter.CustemSpinerAdapter;
import com.whty.zjrcpos.base.BaseActivity;
import com.whty.zjrcpos.entity.CardOpenEntity;
import com.whty.zjrcpos.fragment.BaseFragment;
import com.whty.zjrcpos.http.NetWorkUtil;
import com.whty.zjrcpos.util.Constant;
import com.whty.zjrcpos.util.DialogUtils;
import com.whty.zjrcpos.util.StringUtil;
import com.whty.zjrcpos.util.ToastUtils;
import com.whty.zjrcpos.widget.AbstractSpinerAdapter;
import com.whty.zjrcpos.widget.ClearEditText;
import com.whty.zjrcpos.widget.CustemObject;
import com.whty.zjrcpos.widget.SpinerPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author wulimin
 * @function AccountTypeSelActivity 开户类型选择
 * @time 2017/3/11 上午10:06
 */

public class AccountTypeSelActivity extends BaseActivity implements BaseFragment.OnReloadDataListener {

    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.rl_card_type_DropDown)
    RelativeLayout mRlCardTypeDropDown;
    @BindView(R.id.tv_card_type_prompt)
    TextView mTvCardTypePrompt;
    @BindView(R.id.tv_id_type)
    TextView mTvIdType;
    @BindView(R.id.rl_id_type_DropDown)
    RelativeLayout mRlIdTypeDropDown;
    @BindView(R.id.tv_id_type_prompt)
    TextView mTvIdTypePrompt;
    @BindView(R.id.edit_IDNo)
    ClearEditText mEditIDNo;
    @BindView(R.id.tv_IDNoInputPrompt)
    TextView mTvIDNoInputPrompt;
    @BindView(R.id.edit_IDNo_confirm)
    ClearEditText mEditIDNoConfirm;
    @BindView(R.id.tv_IDNoConfirmPrompt)
    TextView mTvIDNoConfirmPrompt;
    @BindView(R.id.btn_card_type_next)
    Button mBtnCardTypeNext;
    @BindView(R.id.btn_card_type_reset)
    Button mBtnCardTypeReset;
    private List<CustemObject> idTypeList = new ArrayList<CustemObject>();
    private SpinerPopWindow mIDTypeSpinerPopWindow;
    private AbstractSpinerAdapter idTypeAdapter;
    private Observer<CardOpenEntity> obXW;
    private Subscription sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type_sel);
        ButterKnife.bind(this);
        setBackView();
        setTitle("开户类型选择");
        initSpinnerData();
    }

    private void initSpinnerData() {
        //证件类型选择
        String[] idTypeListArr = getResources().getStringArray(R.array.IDTypeArr);
        for (int i = 0; i < idTypeListArr.length; i++) {
            CustemObject object = new CustemObject();
            object.data = idTypeListArr[i];
            idTypeList.add(object);
        }
        idTypeAdapter = new CustemSpinerAdapter(AccountTypeSelActivity.this);
        idTypeAdapter.refreshData(idTypeList, 0);
        mIDTypeSpinerPopWindow = new SpinerPopWindow(AccountTypeSelActivity.this);
        mIDTypeSpinerPopWindow.setAdatper(idTypeAdapter);
        mIDTypeSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                setIDTypeHero(pos);

            }
        });
    }


    @OnClick({R.id.rl_card_type_DropDown, R.id.rl_id_type_DropDown, R.id.btn_card_type_next, R.id.btn_card_type_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_card_type_DropDown:

                break;
            case R.id.rl_id_type_DropDown:
                showIDTypeSpinWindow();
                break;
            case R.id.btn_card_type_next:
                String idNoStr = mEditIDNo.getText().toString();
                String idNoConfirmStr = mEditIDNoConfirm.getText().toString();
                if (StringUtil.isEmpty(idNoStr)) {
                    mTvIDNoInputPrompt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mTvIDNoInputPrompt.setVisibility(View.GONE);
                }
                if (StringUtil.isEmpty(idNoConfirmStr)) {
                    mTvIDNoConfirmPrompt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mTvIDNoConfirmPrompt.setVisibility(View.GONE);
                }
                if (!idNoStr.equals(idNoConfirmStr)) {
                    ToastUtils.toast(AccountTypeSelActivity.this, "两次输入的证件号码不一致");
                    return;
                }
                toSelectCardType();
                break;
            case R.id.btn_card_type_reset:
                mEditIDNo.setText("");
                mEditIDNoConfirm.setText("");
                break;
        }
    }

    private void toSelectCardType() {
        obXW = new Subscriber<CardOpenEntity>() {
            //显示加载框
            @Override
            public void onStart() {
                super.onStart();
                DialogUtils.showProgressDialog(AccountTypeSelActivity.this, "正在加载中...", null);
            }

            //关闭加载框
            @Override
            public void onCompleted() {
                DialogUtils.hideProgressDialog();
            }

            //错误处理
            @Override
            public void onError(Throwable e) {
                DialogUtils.hideProgressDialog();
//                showErrorView("数据加载失败,点击重试", R.drawable.ic_error);
                ToastUtils.toast(AccountTypeSelActivity.this, e.getMessage());
            }

            //返回结果
            @Override
            public void onNext(CardOpenEntity xwEntity) {
                Logger.t("写卡响应").d(xwEntity.toString());
                Intent intent = new Intent();
                intent.setClass(AccountTypeSelActivity.this, PersonalInfoInputActivity.class);
                startActivity(intent);
            }
        };
        request(true);
    }

    private void setIDTypeHero(int pos) {
        if (pos >= 0 && pos <= idTypeList.size()) {
            CustemObject value = idTypeList.get(pos);
//            politicalLandscapeStr = value.toString();
            mTvIdType.setText(value.toString());
        }
    }

    //证件类型下拉选择
    private void showIDTypeSpinWindow() {
        mIDTypeSpinerPopWindow.setWidth(mTvIdType.getWidth());
        mIDTypeSpinerPopWindow.showAsDropDown(mTvIdType);
    }

    public void request(boolean isRefresh) {
        JSONObject param = new JSONObject();
        try {
            param.put("JIO1DM", "AM01");//外部交易代码
            param.put("J1JGDM", Constant.J1JGDM);
            param.put("JIO1GY", Constant.JIO1GY);
            param.put("JIO1QD", Constant.JIO1QD);
            param.put("TxnCode", "AM01");
            param.put("KEHKHH", "SC000000000000000002");
            param.put("KEHUXM", "海宁");
            param.put("KHZHLX", "01");
            param.put("KHZJHM", "342425199311040211");
            param.put("ZHUKKH", "1008000051");
            param.put("KPANLX", "2000");
            param.put("KPNUID", "4F0FBED0");
            param.put("KPCPDM", "1000000001");
            param.put("KPYXQI", "20991231");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), param.toString());
        Logger.t("写卡请求").json(param.toString());
        sb = NetWorkUtil.getCardAccountOpenApi()
                .getCardOpenInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obXW);
    }
}
