
package com.whty.zjrcpos.action;

import com.unionpay.cloudpos.AlgorithmConstants;
import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.pinpad.KeyInfo;
import com.unionpay.cloudpos.pinpad.PINPadDevice;
import com.unionpay.cloudpos.pinpad.PINPadOperationResult;
import com.whty.zjrcpos.common.Common;
import com.whty.zjrcpos.util.StringUtil;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class PINPadAction extends ActionModel {

    private PINPadDevice device = null;
    private int masterKeyID = 0;
    private int userKeyID = 0;
    
    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (PINPadDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.pinpad");
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void showText(Map<String, Object> param, ActionCallback callback) {
        try {
            device.showText(0, "密码余额元");
            device.showText(1, "show test");
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void clearText(Map<String, Object> param, ActionCallback callback) {
        try {
            device.clearText();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void setPINLength(Map<String, Object> param, ActionCallback callback) {
        try {
            device.setPINLength(4, 12);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getSN(Map<String, Object> param, ActionCallback callback) {
        try {
            String sn = device.getSN();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getRandom(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] random = device.getRandom(5);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void updateUserKey1(Map<String, Object> param, ActionCallback callback) {
        String userKey = "09 FA 17 0B 03 11 22 76 09 FA 17 0B 03 11 22 76";
        byte[] arryCipherNewUserKey = new byte[16];
        StringUtil.StringToByteArray(userKey, arryCipherNewUserKey);
        try {
            device.updateUserKey(masterKeyID, userKeyID, arryCipherNewUserKey);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void updateUserKey2(Map<String, Object> param, ActionCallback callback) {
        String userKey = "09 FA 17 0B 03 11 22 76 09 FA 17 0B 03 11 22 76";
        byte[] arryCipherNewUserKey = new byte[16];
        StringUtil.StringToByteArray(userKey, arryCipherNewUserKey);
        String checkValue = "A5 17 3A D5";
        byte[] arryCheckValue = new byte[4];
        StringUtil.StringToByteArray(checkValue, arryCheckValue);
        try {
            device.updateUserKey(masterKeyID, userKeyID, arryCipherNewUserKey,
                    PINPadDevice.CHECK_TYPE_NONE, arryCheckValue);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void encryptData(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 2,
                AlgorithmConstants.ALG_3DES);
        byte[] plain = new byte[] {
                0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38
        };
        try {
            byte[] cipher = device.encryptData(keyInfo, plain);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void calculateMAC(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 1,
                AlgorithmConstants.ALG_3DES);
        byte[] arryMACInData = Common.createMasterKey(8);
        try {
            byte[] mac = device.calculateMac(keyInfo, AlgorithmConstants.ALG_MAC_METHOD_X99,
                    arryMACInData);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void listenForPinBlock(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 0, 4);
        String pan = "0123456789012345678";
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        byte[] pinBlock = ((PINPadOperationResult) arg0).getEncryptedPINBlock();
                    } else {
                    }
                }
            };
            device.listenForPinBlock(keyInfo, pan, false, listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void waitForPinBlock(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 0, 4);
        String pan = "0123456789012345678";
        try {
            OperationResult operationResult = device.waitForPinBlock(keyInfo, pan, false,
                    TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                byte[] pinBlock = ((PINPadOperationResult) operationResult).getEncryptedPINBlock();
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

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            device.close();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
}
