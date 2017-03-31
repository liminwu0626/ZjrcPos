
package com.whty.zjrcpos.action;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.led.LEDDevice;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class LEDAction4 extends ActionModel {

//    private LEDDevice device = new LEDDeviceImpl();
    private LEDDevice device = null;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (LEDDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.led");
        }
    }
    int logicalID = 3;

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open(logicalID);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getLogicalID(Map<String, Object> param, ActionCallback callback) {
        try {
            int logicalID = device.getLogicalID();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void startBlink(Map<String, Object> param, ActionCallback callback) {
        try {
            device.startBlink(100, 100, 10);
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

    public void cancelBlink(Map<String, Object> param, ActionCallback callback) {
        try {
            device.cancelBlink();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void blink(Map<String, Object> param, ActionCallback callback) {
        try {
            device.blink(100, 100, 100);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void getStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int status = device.getStatus();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void turnOn(Map<String, Object> param, ActionCallback callback) {
        try {
            device.turnOn();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void turnOff(Map<String, Object> param, ActionCallback callback) {
        try {
            device.turnOff();
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
