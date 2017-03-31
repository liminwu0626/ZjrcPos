
package com.whty.zjrcpos.action;

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
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class SmartCardAction2 extends ActionModel {

    private SmartCardReaderDevice device =null;
    private Card icCard;
    int area = SLE4442Card.MEMORY_CARD_AREA_MAIN;
    int address = 0;
    int length = 10;
    int logicalID = 1;
    
    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (SmartCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.smartcardreader");
        }
    }
    
    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open(logicalID);
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

    public void connect(Map<String, Object> param, ActionCallback callback) {
        try {
            ATR atr = ((CPUCard) icCard).connect();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void transmit(Map<String, Object> param, ActionCallback callback) {
        byte[] arryAPDU = new byte[] {
                0x00, (byte) 0x84, 0x00, 0x00, 0x08
        };
        try {
            byte[] apduResponse = ((CPUCard) icCard).transmit(arryAPDU);
        } catch (DeviceException e) {
            e.printStackTrace();
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

}
