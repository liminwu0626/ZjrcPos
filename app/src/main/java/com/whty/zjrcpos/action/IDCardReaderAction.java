
package com.whty.zjrcpos.action;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.idcardreader.IDCardReaderDevice;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class IDCardReaderAction extends ActionModel {

    IDCardReaderDevice device = null;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        
        if (device == null) {
            device = (IDCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.idcardreader");
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
            OperationListener listener = new OperationListener(){

                @Override
                public void handleResult(OperationResult arg0) {
                }
            };
            device.listenForCardPresent(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
//    public void open(Map<String, Object> param, ActionCallback callback) {
//        try {
//            device.open();
//            sendSuccessLog(mContext.getString(R.string.operation_succeed));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }
//    }
//    
//    public void open(Map<String, Object> param, ActionCallback callback) {
//        try {
//            device.open();
//            sendSuccessLog(mContext.getString(R.string.operation_succeed));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }
//    }
//    
//    public void open(Map<String, Object> param, ActionCallback callback) {
//        try {
//            device.open();
//            sendSuccessLog(mContext.getString(R.string.operation_succeed));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }
//    }
//    
//    public void open(Map<String, Object> param, ActionCallback callback) {
//        try {
//            device.open();
//            sendSuccessLog(mContext.getString(R.string.operation_succeed));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }
//    }
//    
//    public void open(Map<String, Object> param, ActionCallback callback) {
//        try {
//            device.open();
//            sendSuccessLog(mContext.getString(R.string.operation_succeed));
//        } catch (DeviceException e) {
//            e.printStackTrace();
//            sendFailedLog(mContext.getString(R.string.operation_failed));
//        }
//    }
    
    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            device.close();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
}
