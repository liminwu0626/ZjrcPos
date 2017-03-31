package com.whty.zjrcpos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.centerm.cpay.midsdk.dev.DeviceFactory;
import com.centerm.cpay.midsdk.dev.EnumSDKType;
import com.centerm.cpay.midsdk.dev.define.IRfCardDev;
import com.orhanobut.logger.Logger;
import com.whty.zjrcpos.R;
import com.whty.zjrcpos.base.BaseActivity;
import com.whty.zjrcpos.util.StringUtil;
import com.whty.zjrcpos.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.whty.zjrcpos.util.StringUtil.byte2HexStr;
import static com.whty.zjrcpos.util.StringUtil.hexStr2Bytes;


/**
 * @author wulimin
 * @function PersonalizedInfoWritingActivity 个人化信息写入
 * @time 2017/3/9 下午4:18
 */

public class PersonalizedInfoWritingActivity extends BaseActivity {

    @BindView(R.id.tv_result_show)
    TextView mTvResultShow;
    @BindView(R.id.btn_card_info_next)
    Button mBtnCardInfoInput;
    @BindView(R.id.btn_card_open)
    Button mBtnCardOpen;

    //    private IIcCardDev rfCardDev;
    private boolean icCardDevStatus;
    private String cardNo;
    public DeviceFactory factory;
    private IRfCardDev rfCardDev;
    private CountDownTimer timerRefreshData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_open);
        ButterKnife.bind(this);
        setBackView();
        setTitle("开卡");
        try {
            factory = DeviceFactory.getInstance();
            factory.init(getApplicationContext(), EnumSDKType.CPAY_SDK, mInitCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    DeviceFactory.InitCallback mInitCallback = new DeviceFactory.InitCallback() {
        @Override
        public void onResult(boolean finishFlag) {
            //判断结束标志，如果为true，就可以开始调用相应的设备服务
            Logger.i("获取IC卡设备" + finishFlag);
            if (finishFlag) {
                try {
                    //获取IC卡设备
//                    rfCardDev = factory.getIcCardDev();
                    rfCardDev = factory.getRfCardDev();
                    rfCardDev.open();
                    initDevStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                    //捕获异常需要进行处理，如果忽略异常，可能会导致程序异常
                }
            }
        }
    };

    private void initDevStatus() {
        // 刷新数据倒数计时
        if (timerRefreshData == null) {
            timerRefreshData = new CountDownTimer(1000 * 60 * 5, 1000) {
                int refreshTime = 60 * 5;

                @Override
                public void onTick(long millisUntilFinished) {
                    refreshTime--;
                    icCardDevStatus = rfCardDev.getStatus();
                    Logger.t("设备状态").i(icCardDevStatus + "");
                    if (icCardDevStatus) {
                        byte[] result = rfCardDev.reset();
                        String resultStr = byte2HexStr(result);
                        Logger.t("复位结果").i(resultStr);
                        ToastUtils.toast(PersonalizedInfoWritingActivity.this, "已获取到卡片信息");
                    } else {
                        ToastUtils.toast(PersonalizedInfoWritingActivity.this, "请将卡片放置终端感应区");
                    }
                    if (icCardDevStatus) {
                        timerRefreshData.cancel();
                        timerRefreshData = null;
                    }
                }

                @Override
                public void onFinish() {

                }
            };
            timerRefreshData.start();
        } else {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.btn_card_open, R.id.btn_card_info_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_card_open:
                //读金融卡号
                //发送： 00A4040007A0000003330101
                //返回： 6F608408A000000333010101A554500A50424F432044454249548701019F381B9F66049F02069F03069F1A0295055F2A029A039C019F3704DF60015F2D027A689F1101019F120A50424F43204445424954BF0C0E9F4D020B0ADF4D020C0ADF6101029000（
                // 检测最后四位为9000为成功，其它为错误）
                String apduData = "00A4040007A0000003330101";
                String apduData2 = "00B2010C69";
                byte[] apduByte1 = hexStr2Bytes(apduData);
                byte[] apduByte2 = hexStr2Bytes(apduData2);
                Logger.t("APDU指令发送").i(byte2HexStr(apduByte1));
                Logger.t("APDU指令发送").i(byte2HexStr(apduByte2));
                if (icCardDevStatus) {
                    byte[] resultData1, resultData2;
                    resultData1 = rfCardDev.send(apduByte1);
                    resultData2 = rfCardDev.send(apduByte2);
                    String cardNoFull = byte2HexStr(resultData2);
                    cardNo = cardNoFull.substring(8, 27);
                    Logger.t("APDU指令响应").i(byte2HexStr(resultData1));
                    Logger.t("APDU指令响应").i(byte2HexStr(resultData2));
                    Logger.t("截取IC卡账户").i(cardNo);
                    mTvResultShow.setText(StringUtil.cardNoFormat(cardNo));
                    ToastUtils.toast(PersonalizedInfoWritingActivity.this, "个人化成功");
                }
                break;
            //读卡后开卡,下一步跳转开卡客户类型选择
            case R.id.btn_card_info_next:
                if (TextUtils.isEmpty(cardNo)) {
                    ToastUtils.toast(PersonalizedInfoWritingActivity.this, "请先完成个人化信息写入");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(PersonalizedInfoWritingActivity.this, AccountTypeSelActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeviceFactory.getInstance().release();
    }

}
