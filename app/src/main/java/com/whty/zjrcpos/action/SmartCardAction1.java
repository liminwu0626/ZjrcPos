
package com.whty.zjrcpos.action;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.card.ATR;
import com.unionpay.cloudpos.card.CPUCard;
import com.unionpay.cloudpos.card.Card;
import com.unionpay.cloudpos.card.SLE4442Card;
import com.unionpay.cloudpos.smartcardreader.SmartCardReaderDevice;
import com.unionpay.cloudpos.smartcardreader.SmartCardReaderOperationResult;
import com.whty.zjrcpos.common.Common;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

import static com.whty.zjrcpos.util.StringUtil.hexStr2Bytes;
import static com.whty.zjrcpos.util.StringUtil.byte2HexStr;


public class SmartCardAction1 extends ActionModel {

    private SmartCardReaderDevice device = null;
    private Card icCard;
    int area = SLE4442Card.MEMORY_CARD_AREA_MAIN;
    int address = 0;
    int length = 10;

    public SmartCardAction1(SmartCardReaderDevice device) {
        this.device = device;
        Logger.d("device");
    }

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (SmartCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.smartcardreader");
        }
        Logger.d("device");
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听插卡操作 异步调用
     *
     * @param param
     * @param callback
     */
    public void listenForCardPresent(Map<String, Object> param, final ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        icCard = ((SmartCardReaderOperationResult) arg0).getCard();
                    } else {

                    }
                }
            };
            device.listenForCardPresent(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本方法是上述对应的 listenForCardPresent(OperationListener, int)方法的同步版本。
     *
     * @param param
     * @param callback
     */
    public void waitForCardPresent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationResult operationResult = device.waitForCardPresent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                icCard = ((SmartCardReaderOperationResult) operationResult).getCard();
            } else {
            }
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听卡片移除动作
     *
     * @param param
     * @param callback
     */
    public void listenForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        icCard = null;
                    } else {
                    }
                }
            };
            device.listenForCardAbsent(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本方法是上述对应的 listenForCardAbsent( OperationListener, int)方法的同步版本
     *
     * @param param
     * @param callback
     */
    public void waitForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationResult operationResult = device.waitForCardAbsent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                icCard = null;
            } else {
            }
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void cancelRequest(Map<String, Object> param, ActionCallback callback) {
        try {
            device.cancelRequest();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getID(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] cardID = icCard.getID();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getProtocol(Map<String, Object> param, ActionCallback callback) {
        try {
            int protocol = icCard.getProtocol();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getCardStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int cardStatus = icCard.getCardStatus();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void verify(Map<String, Object> param, ActionCallback callback) {
        byte[] key = new byte[]{
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0xFF
        };
        try {

            boolean verifyResult = ((SLE4442Card) icCard).verify(key);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void read(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] result = ((SLE4442Card) icCard).read(area, address, length);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void write(Map<String, Object> param, ActionCallback callback) {
        byte[] arryData = Common.createMasterKey(10);// 随机创造10个字节的数组
        try {
            ((SLE4442Card) icCard).write(area, address, arryData);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void connect(Map<String, Object> param, ActionCallback callback) {
        try {
            ATR atr = ((CPUCard) icCard).connect();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void transmit(Map<String, Object> param, ActionCallback callback) {
        byte[] arryAPDU = new byte[]{
                0x00, (byte) 0x84, 0x00, 0x00, 0x08
        };
        //读金融卡号
        //发送： 00A4040007A0000003330101
        //返回： 6F608408A000000333010101A554500A50424F432044454249548701019F381B9F66049F02069F03069F1A0295055F2A029A039C019F3704DF60015F2D027A689F1101019F120A50424F43204445424954BF0C0E9F4D020B0ADF4D020C0ADF6101029000（
        // 检测最后四位为9000为成功，其它为错误）
        String apduData = "00A4040007A0000003330101";
        String apduData2 = "00B2010C1A";
        if (isGetRightApduResonse(apduData)) {
            if (isGetRightApduResonse(apduData2)) {
                try {
                    byte[] apduResponse = ((CPUCard) icCard).transmit(hexStr2Bytes(apduData2));
                    String cardNoFull = byte2HexStr(apduResponse);
                    String cardNo = cardNoFull.substring(8, 27);
                } catch (DeviceException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void disconnect(Map<String, Object> param, ActionCallback callback) {
        try {
            ((CPUCard) icCard).disconnect();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            icCard = null;
            device.close();
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
                }else {
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
