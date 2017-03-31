package com.whty.zjrcpos.action;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.OperationListener;
import com.unionpay.cloudpos.OperationResult;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.TimeConstants;
import com.wizarpos.mvc.base.ActionCallback;
import com.unionpay.cloudpos.fingerprint.Fingerprint;
import com.unionpay.cloudpos.fingerprint.FingerprintDevice;
import com.unionpay.cloudpos.fingerprint.FingerprintOperationResult;
import java.util.Map;

public class FingerPrintAction extends ActionModel {

	private FingerprintDevice device = null;
	
	@Override
	protected void doBefore(Map<String, Object> param, ActionCallback callback) {
		super.doBefore(param, callback);
		if (device == null) {
			device = (FingerprintDevice) POSTerminal.getInstance(mContext).getDevice("cloudpos.device.fingerprint");
		}
	}
	
	public void open(Map<String, Object> param, ActionCallback callback) {
		try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
	}
	
	public void listenForFingerprint(Map<String, Object> param, ActionCallback callback) {
		try {
			OperationListener listener = new OperationListener() {
				
				@Override
				public void handleResult(OperationResult arg0) {
					if (arg0.getResultCode() == OperationResult.SUCCESS) {
					} else {
					}
				}
			};
			device.listenForFingerprint(listener, TimeConstants.FOREVER);
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForFingerprint(Map<String, Object> param, ActionCallback callback) {
		try {
			FingerprintOperationResult operationResult = device.waitForFingerprint(TimeConstants.FOREVER);
			if (operationResult.getResultCode() == OperationResult.SUCCESS) {
			} else {
			}
		} catch (DeviceException e) {
			e.printStackTrace();
		}
	}
	
	public void match(Map<String, Object> param, ActionCallback callback) {
		try {
			Fingerprint fingerprint1 = getFingerprint();
			Fingerprint fingerprint2 = getFingerprint();
			if (fingerprint1 != null && fingerprint2 != null) {
				int match = device.match(fingerprint1, fingerprint2);
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
	
	private Fingerprint getFingerprint() {
		Fingerprint fingerprint = null;
		try {
			FingerprintOperationResult operationResult = device.waitForFingerprint(TimeConstants.FOREVER);
			if (operationResult.getResultCode() == OperationResult.SUCCESS) {
				fingerprint = operationResult.getFingerprint(0, 0);//该接口参数暂时不知用途，随便传的0
			} else {
			}
		} catch (DeviceException e) {
			e.printStackTrace();
		}
		return fingerprint;
	}
}
