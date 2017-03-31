
package com.whty.zjrcpos.action;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.unionpay.cloudpos.msr.MSRDevice;
import com.unionpay.cloudpos.msr.MSROperationResult;
import com.unionpay.cloudpos.msr.MSRTrackData;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class MSRAction extends ActionModel {

//    private MSRDevice device = new MSRDeviceImpl();
    private MSRDevice device = null;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (MSRDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.msr");
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void listenForSwipe(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        MSRTrackData data = ((MSROperationResult) arg0).getMSRTrackData();
                        int trackError = 0;
                        byte[] trackData = null;
                        for (int trackNo = 0; trackNo < 3; trackNo++) {
                            trackError = data.getTrackError(trackNo);
                            if (trackError == MSRTrackData.NO_ERROR) {
                                trackData = data.getTrackData(trackNo);
                            } else {
                            }
                        }
                    } else {
                    }
                }
            };
            device.listenForSwipe(listener, TimeConstants.FOREVER);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void waitForSwipe(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationResult operationResult = device.waitForSwipe(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                MSRTrackData data = ((MSROperationResult) operationResult).getMSRTrackData();
                int trackError = 0;
                byte[] trackData = null;
                for (int trackNo = 0; trackNo < 3; trackNo++) {
                    trackError = data.getTrackError(trackNo);
                    if (trackError == MSRTrackData.NO_ERROR) {
                        trackData = data.getTrackData(trackNo);
                    } else {
                    }
                }
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
