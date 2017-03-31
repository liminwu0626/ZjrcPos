package com.whty.zjrcpos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.whty.zjrcpos.R;
import com.whty.zjrcpos.adapter.CustemSpinerAdapter;
import com.whty.zjrcpos.base.BaseActivity;
import com.whty.zjrcpos.entity.PersonalInfoInputEntity;
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
 * @function PersonalInfoInputActivity 个人客户信息写入
 * @time 2017/3/11 上午10:08
 */


public class PersonalInfoInputActivity extends BaseActivity {

    @BindView(R.id.edit_customer_name)
    ClearEditText mEditCustomerName;
    @BindView(R.id.tv_customerNameInputPrompt)
    TextView mTvCustomerNameInputPrompt;
    @BindView(R.id.edit_client_type)
    ClearEditText mEditClientType;
    @BindView(R.id.tv_id_info_input_type)
    TextView mTvIdInfoInputType;
    @BindView(R.id.rl_id_type_info_input_DropDown)
    RelativeLayout mRlIdTypeInfoInputDropDown;
    @BindView(R.id.tv_id_type_prompt)
    TextView mTvIdTypePrompt;
    @BindView(R.id.tv_IDNoTitile)
    TextView mTvIDNoTitile;
    @BindView(R.id.edit_IDNo)
    ClearEditText mEditIDNo;
    @BindView(R.id.tv_IDNoInputPrompt)
    TextView mTvIDNoInputPrompt;
    @BindView(R.id.edit_class)
    ClearEditText mEditClass;
    @BindView(R.id.tv_class_prompt)
    TextView mTvClassPrompt;
    @BindView(R.id.tv_nativePlaceTitile)
    TextView mTvNativePlaceTitile;
    @BindView(R.id.edit_major)
    ClearEditText mEditMajor;
    @BindView(R.id.tv_majorInputPrompt)
    TextView mTvMajorInputPrompt;
    @BindView(R.id.tv_unit_name)
    TextView mTvUnitName;
    @BindView(R.id.rl_unit_name_DropDown)
    RelativeLayout mRlUnitNameDropDown;
    @BindView(R.id.tv_card_type_prompt)
    TextView mTvCardTypePrompt;
    @BindView(R.id.tv_sexTitile)
    TextView mTvSexTitile;
    @BindView(R.id.male)
    RadioButton mMale;
    @BindView(R.id.female)
    RadioButton mFemale;
    @BindView(R.id.sex)
    RadioGroup mSex;
    @BindView(R.id.tv_sexInputPrompt)
    TextView mTvSexInputPrompt;
    @BindView(R.id.tv_birthDateTitile)
    TextView mTvBirthDateTitile;
    @BindView(R.id.tv_birthDate)
    TextView mTvBirthDate;
    @BindView(R.id.ll_birthDate)
    LinearLayout mLlBirthDate;
    @BindView(R.id.tv_emailTitile)
    TextView mTvEmailTitile;
    @BindView(R.id.edit_email)
    ClearEditText mEditEmail;
    @BindView(R.id.tv_contactAddressTitile)
    TextView mTvContactAddressTitile;
    @BindView(R.id.edit_contactAddress)
    ClearEditText mEditContactAddress;
    @BindView(R.id.tv_contactPhoneTitile)
    TextView mTvContactPhoneTitile;
    @BindView(R.id.edit_contactPhone)
    ClearEditText mEditContactPhone;
    @BindView(R.id.tv_companyNameTitile)
    TextView mTvCompanyNameTitile;
    @BindView(R.id.edit_companyName)
    ClearEditText mEditCompanyName;
    @BindView(R.id.tv_companyAddressTitle)
    TextView mTvCompanyAddressTitle;
    @BindView(R.id.edit_companyAddress)
    ClearEditText mEditCompanyAddress;
    @BindView(R.id.tv_companyPhoneTitle)
    TextView mTvCompanyPhoneTitle;
    @BindView(R.id.edit_companyPhone)
    ClearEditText mEditCompanyPhone;
    @BindView(R.id.btn_personal_info_input_next)
    Button mBtnPersonalInfoInputNext;
    private List<CustemObject> idTypeInfoInputList = new ArrayList<CustemObject>();
    private List<CustemObject> unitNameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter idTypeInfoInputAdapter;
    private AbstractSpinerAdapter unitNameAdapter;
    private SpinerPopWindow mIDTypeInfoInputSpinerPopWindow;
    private SpinerPopWindow mUnitNamePopWindow;
    Observer<PersonalInfoInputEntity> obInfoInput;
    private Subscription sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_input);
        ButterKnife.bind(this);
        setBackView();
        setTitle("个人客户信息录入");
        initSpinnerData();
    }

    private void initSpinnerData() {
        //信息录入证件类型选择
        String[] idTypeInfoInputListArr = getResources().getStringArray(R.array.IDTypeArr);
        for (int i = 0; i < idTypeInfoInputListArr.length; i++) {
            CustemObject object = new CustemObject();
            object.data = idTypeInfoInputListArr[i];
            idTypeInfoInputList.add(object);
        }
        idTypeInfoInputAdapter = new CustemSpinerAdapter(PersonalInfoInputActivity.this);
        idTypeInfoInputAdapter.refreshData(idTypeInfoInputList, 0);
        mIDTypeInfoInputSpinerPopWindow = new SpinerPopWindow(PersonalInfoInputActivity.this);
        mIDTypeInfoInputSpinerPopWindow.setAdatper(idTypeInfoInputAdapter);
        mIDTypeInfoInputSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                setIDTypeInfoInputHero(pos);

            }
        });
        //单位名称选择
        String[] unitNameListArr = getResources().getStringArray(R.array.unitNameArr);
        for (int i = 0; i < unitNameListArr.length; i++) {
            CustemObject object = new CustemObject();
            object.data = unitNameListArr[i];
            unitNameList.add(object);
        }
        unitNameAdapter = new CustemSpinerAdapter(PersonalInfoInputActivity.this);
        unitNameAdapter.refreshData(unitNameList, 0);
        mUnitNamePopWindow = new SpinerPopWindow(PersonalInfoInputActivity.this);
        mUnitNamePopWindow.setAdatper(unitNameAdapter);
        mUnitNamePopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                setUnitNameHero(pos);
            }
        });
    }


    @OnClick({R.id.rl_id_type_info_input_DropDown, R.id.rl_unit_name_DropDown, R.id.btn_personal_info_input_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_id_type_info_input_DropDown:
                showIDTypeInfoInputSpinWindow();
                break;
            case R.id.rl_unit_name_DropDown:
                showUnitNameSpinWindow();
                break;
            case R.id.btn_personal_info_input_next:
                String customerStr = mEditCustomerName.getText().toString();
                String idNoStr = mEditIDNo.getText().toString();
                if (StringUtil.isEmpty(customerStr)) {
                    mTvCustomerNameInputPrompt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mTvCustomerNameInputPrompt.setVisibility(View.GONE);
                }
                if (StringUtil.isEmpty(idNoStr)) {
                    mTvIDNoInputPrompt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mTvIDNoInputPrompt.setVisibility(View.GONE);
                }
                sendInfoInputRequest();
                break;
        }
    }

    private void sendInfoInputRequest() {
        obInfoInput = new Subscriber<PersonalInfoInputEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                DialogUtils.showProgressDialog(PersonalInfoInputActivity.this, "正在加载中...", null);
            }

            @Override
            public void onCompleted() {
                DialogUtils.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                DialogUtils.hideProgressDialog();
                ToastUtils.toast(PersonalInfoInputActivity.this, e.getMessage());
            }

            @Override
            public void onNext(PersonalInfoInputEntity personalInfoInputEntity) {
                Logger.t("个人信息写入响应").d(personalInfoInputEntity.toString());
                ToastUtils.toast(PersonalInfoInputActivity.this, "个人信息写入成功");
            }
        };
        request(true);
    }

    public void request(boolean isRefresh) {
        JSONObject param = new JSONObject();
        try {
            param.put("JIO1DM", "IM02");//外部交易代码
            param.put("JIO1GY", Constant.JIO1GY);
            param.put("QINTLS", "999999");
            param.put("QINTRQ", "20160810");
            param.put("QINTSJ", "204025");
            param.put("JIO1QD", Constant.JIO1QD);
            param.put("TxnCode", "IM02");
            param.put("KEHZWM", "海宁");
            param.put("CKARGB", "001");
            param.put("KEHUJB", "02");
            param.put("KHZHLX", "02");
            param.put("KHZJHM", "344825170301001321");
            param.put("KHDWMC", "1213");
            param.put("XUEHAO", "342425199311040211");
            param.put("KHKZLX", "02");
            param.put("KBANJI", "13计算机高级");
            param.put("PROFES", "计算机");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.t("个人信息写入请求").json(param.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), param.toString());
        sb = NetWorkUtil.getPersonalInfoInputApi()
                .getInfoInput(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obInfoInput);
    }

    private void setIDTypeInfoInputHero(int pos) {
        if (pos >= 0 && pos <= idTypeInfoInputList.size()) {
            CustemObject value = idTypeInfoInputList.get(pos);
//            politicalLandscapeStr = value.toString();
            Logger.d(value.toString());
            mTvIdInfoInputType.setText(value.toString());
        }
    }

    private void setUnitNameHero(int pos) {
        if (pos >= 0 && pos <= unitNameList.size()) {
            CustemObject value = unitNameList.get(pos);
//            politicalLandscapeStr = value.toString();
            mTvUnitName.setText(value.toString());
        }
    }

    //信息录入证件类型下拉选择
    private void showIDTypeInfoInputSpinWindow() {
        mIDTypeInfoInputSpinerPopWindow.setWidth(mTvIdInfoInputType.getWidth());
        mIDTypeInfoInputSpinerPopWindow.showAsDropDown(mTvIdInfoInputType);
    }

    //单位名称下拉选择
    private void showUnitNameSpinWindow() {
        mUnitNamePopWindow.setWidth(mTvUnitName.getWidth());
        mUnitNamePopWindow.showAsDropDown(mTvUnitName);
    }
}
