
package com.whty.zjrcpos.action;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.unionpay.cloudpos.DeviceException;
import com.unionpay.cloudpos.POSTerminal;
import com.unionpay.cloudpos.printer.Format;
import com.unionpay.cloudpos.printer.PrinterDevice;
import com.whty.zjrcpos.R;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class PrinterAction extends ActionModel {

    private PrinterDevice device = null;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (PrinterDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.printer");
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void printText(Map<String, Object> param, ActionCallback callback) {
        try {
//            device.printText("This is a test\n");
            Format format = new Format();
            format.setParameter(Format.FORMAT_FONT_SIZE, Format.FORMAT_FONT_SIZE_SMALL );
            device.printText(format, "This is a test\n");
            device.printText(format, "This is a test\n");
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void sendESCCommand(Map<String, Object> param, ActionCallback callback) {
        byte[] command = new byte[] {
                (byte) 0x12, (byte) 0x54
        };
        try {
            device.sendESCCommand(command);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }

    public void queryStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int status = device.queryStatus();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void cutPaper(Map<String, Object> param, ActionCallback callback) {
        try {
            device.cutPaper();
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void printBitmap(Map<String, Object> param, ActionCallback callback) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.printer_barcode_low);
        try {
            device.printBitmap(bitmap);
        } catch (DeviceException e) {
            e.printStackTrace();
        }
    }
    
    public void printBarcode(Map<String, Object> param, ActionCallback callback) {
        try {
            Format format = new Format();
            format.setParameter("HRI-location", "DOWN");
            //0, 01234567896
            //1, "04310000526"
            //
            device.printBarcode(format, PrinterDevice.BARCODE_CODE128, "01234567896");
            device.printText("\n\n\n");
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
