
package com.whty.zjrcpos.action;

import android.util.Log;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.card.ATR;
import com.unionpay.cloudpos.card.CPUCard;
import com.unionpay.cloudpos.card.Card;
import com.unionpay.cloudpos.card.MifareCard;
import com.unionpay.cloudpos.card.MifareUltralightCard;
import com.unionpay.cloudpos.card.MoneyValue;
import com.unionpay.cloudpos.rfcardreader.RFCardReaderDevice;
import com.unionpay.cloudpos.rfcardreader.RFCardReaderOperationResult;
import com.whty.zjrcpos.common.Common;
import com.whty.zjrcpos.util.StringUtil;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

import static com.whty.zjrcpos.util.StringUtil.hexStr2Bytes;

public class RFCardAction extends ActionModel {
    private RFCardReaderDevice device = null;
    Card rfCard;
    int sectorIndex = 0;
    int blockIndex = 1;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (RFCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.rfcardreader");
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void listenForCardPresent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        rfCard = ((RFCardReaderOperationResult) arg0).getCard();
                    } else {
                    }
                }
            };
            device.listenForCardPresent(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void waitForCardPresent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationResult operationResult = device.waitForCardPresent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                rfCard = ((RFCardReaderOperationResult) operationResult).getCard();
            } else {
            }
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void listenForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        rfCard = null;
                    } else {
                    }
                }
            };
            device.listenForCardAbsent(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void waitForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationResult operationResult = device.waitForCardAbsent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                rfCard = null;
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

    public void getMode(Map<String, Object> param, ActionCallback callback) {
        try {
            int mode = device.getMode();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void setSpeed(Map<String, Object> param, ActionCallback callback) {
        try {
            device.setSpeed(460800);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getSpeed(Map<String, Object> param, ActionCallback callback) {
        try {
            int speed = device.getSpeed();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getID(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] cardID = rfCard.getID();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getProtocol(Map<String, Object> param, ActionCallback callback) {
        try {
            int protocol = rfCard.getProtocol();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getCardStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int cardStatus = rfCard.getCardStatus();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void verifyKeyA(Map<String, Object> param, ActionCallback callback) {
        byte[] key = new byte[] {
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0xFF
        };
        try {

            boolean verifyResult = ((MifareCard) rfCard).verifyKeyA(sectorIndex, key);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void verifyKeyB(Map<String, Object> param, ActionCallback callback) {
        byte[] key = new byte[] {
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0xFF
        };
        try {

            boolean verifyResult = ((MifareCard) rfCard).verifyKeyB(sectorIndex, key);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void readBlock(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] result = ((MifareCard) rfCard).readBlock(sectorIndex, blockIndex);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void writeBlock(Map<String, Object> param, ActionCallback callback) {
        byte[] arryData = Common.createMasterKey(16);// 随机创造16个字节的数组
        try {
            ((MifareCard) rfCard).writeBlock(sectorIndex, blockIndex, arryData);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void readValue(Map<String, Object> param, ActionCallback callback) {
        try {
            MoneyValue value = ((MifareCard) rfCard).readValue(sectorIndex, blockIndex);
            Log.i("读卡信息"," value = "
                    + value.getMoney() + " user data: "
                    + StringUtil.byteArray2String(value.getUserData()));
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void writeValue(Map<String, Object> param, ActionCallback callback) {
        try {
            MoneyValue value = new MoneyValue(new byte[] {
                    (byte) 0x39
            }, 1024);
            ((MifareCard) rfCard).writeValue(sectorIndex, blockIndex, value);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void incrementValue(Map<String, Object> param, ActionCallback callback) {
        try {
            ((MifareCard) rfCard).increaseValue(sectorIndex, blockIndex, 10);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void decrementValue(Map<String, Object> param, ActionCallback callback) {
        try {
            ((MifareCard) rfCard).decreaseValue(sectorIndex, blockIndex, 10);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void read(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] result = ((MifareUltralightCard) rfCard).read(blockIndex);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void write(Map<String, Object> param, ActionCallback callback) {
        byte[] arryData = Common.createMasterKey(4);// 随机创造4个字节的数组
        try {
            ((MifareUltralightCard) rfCard).write(blockIndex, arryData);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void connect(Map<String, Object> param, ActionCallback callback) {
        try {
            ATR atr = ((CPUCard) rfCard).connect();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void transmit(Map<String, Object> param, ActionCallback callback) {
        final byte[] arryAPDU = new byte[] {
                (byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00,
                (byte) 0x0E, (byte) 0x32, (byte) 0x50, (byte) 0x41,
                (byte) 0x59, (byte) 0x2E, (byte) 0x53, (byte) 0x59,
                (byte) 0x53, (byte) 0x2E, (byte) 0x44, (byte) 0x44,
                (byte) 0x46, (byte) 0x30, (byte) 0x31
        };
        //读金融卡号
        //发送： 00A4040007A0000003330101
        //返回： 6F608408A000000333010101A554500A50424F432044454249548701019F381B9F66049F02069F03069F1A0295055F2A029A039C019F3704DF60015F2D027A689F1101019F120A50424F43204445424954BF0C0E9F4D020B0ADF4D020C0ADF6101029000（
        // 检测最后四位为9000为成功，其它为错误）
        String apduData = "00A4040007A0000003330101";
        String apduData2 = "00B2010C69";
        byte[] apduByte1 = hexStr2Bytes(apduData);
        byte[] apduByte2 = hexStr2Bytes(apduData2);
//        try {
//            byte[] apduResponse = ((CPUCard) rfCard).transmit(arryAPDU);
//                    + StringUtility.byteArray2String(apduResponse));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }


        try {
            byte[] apduResponse1 = ((CPUCard) rfCard).transmit(apduByte1);
            byte[] apduResponse2 = ((CPUCard) rfCard).transmit(apduByte2);
            String cardNoFull = StringUtil.byte2HexStr(apduResponse2);
            String cardNo = cardNoFull.substring(8, 27);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(Map<String, Object> param, ActionCallback callback) {
        try {
            ((CPUCard) rfCard).disconnect();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            rfCard = null;
            device.close();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
}
