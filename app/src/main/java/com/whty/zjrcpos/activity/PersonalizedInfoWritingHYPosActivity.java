package com.whty.zjrcpos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.card.ATR;
import com.unionpay.cloudpos.card.CPUCard;
import com.unionpay.cloudpos.card.Card;
import com.unionpay.cloudpos.smartcardreader.SmartCardReaderDevice;
import com.unionpay.cloudpos.smartcardreader.SmartCardReaderOperationResult;
import com.whty.zjrcpos.R;
import com.whty.zjrcpos.base.BaseActivity;
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

public class PersonalizedInfoWritingHYPosActivity extends BaseActivity {

    @BindView(R.id.tv_result_show)
    TextView mTvResultShow;
    @BindView(R.id.btn_card_info_next)
    Button mBtnCardInfoInput;
    @BindView(R.id.btn_card_open)
    Button mBtnCardOpen;
    private String cardNo;
    SmartCardReaderDevice mDevice;
    private Card icCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_open);
        ButterKnife.bind(this);
        mDevice = (SmartCardReaderDevice) POSTerminal.getInstance(getApplicationContext()).getDevice("cloudpos.device.smartcardreader");
        setBackView();
        setTitle("开卡");
    }

    private void initDeviceConfig() {
        try {
            mDevice.open();
            mDevice.listenForCardPresent(new OperationListener() {
                @Override
                public void handleResult(OperationResult operationResult) {
                    if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                        SmartCardReaderOperationResult mSmartCardResult = (SmartCardReaderOperationResult) operationResult;
                        icCard = mSmartCardResult.getCard();
                        try {
                            ATR atr = ((CPUCard) icCard).connect();
                            Logger.t("复位应答").d(byte2HexStr(atr.getBytes()));
                        } catch (DeviceException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDeviceConfig();
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
                String apduData2 = "00B2010C1A";
                if (TextUtils.isEmpty(cardNo)) {
                    if (isGetRightApduResonse(apduData)) {
                        if (isGetRightApduResonse(apduData2)) {
                            try {
                                byte[] apduResponse = ((CPUCard) icCard).transmit(hexStr2Bytes(apduData2));
                                String cardNoFull = byte2HexStr(apduResponse);
                                cardNo = cardNoFull.substring(8, 27);
                                mTvResultShow.setText(cardNo);
                            } catch (DeviceException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    ToastUtils.toast(PersonalizedInfoWritingHYPosActivity.this, "已完成读卡操作");
                }
                break;
            //读卡后开卡,下一步跳转开卡客户类型选择
            case R.id.btn_card_info_next:
                if (TextUtils.isEmpty(cardNo)) {
                    ToastUtils.toast(PersonalizedInfoWritingHYPosActivity.this, "请先完成个人化信息写入");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(PersonalizedInfoWritingHYPosActivity.this, AccountTypeSelActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            icCard = null;
            mDevice.close();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    private boolean isGetRightApduResonse(String ApduStr) {
        //将字符串格式指令转换为16进制数组
        byte[] apduByte = hexStr2Bytes(ApduStr);
        try {
            //获取卡片与终端交互的byte数组
            byte[] apduResponse = ((CPUCard) icCard).transmit(apduByte);
            //转化为字符串格式
            String responseData = byte2HexStr(apduResponse);
            Log.i("第一条响应数据", responseData);
            if ((!(responseData).endsWith("9000")) && (responseData).startsWith("61")) {
                String rightApdu = "00C00000" + responseData.substring(2, 4);
                Log.i("重新组装的指令", rightApdu);
                byte[] rightApduByte = hexStr2Bytes(rightApdu);
                byte[] apduResponseReset = ((CPUCard) icCard).transmit(rightApduByte);
                if ((byte2HexStr(apduResponseReset)).endsWith("9000")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (DeviceException e) {
            e.printStackTrace();
        }
        return false;
    }
}
