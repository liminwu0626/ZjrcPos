package com.whty.zjrcpos.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

/**
 * Created by 24275 on 2016/6/3.
 */
public class DialogUtils {

    private static final long TIMEOUT = 50000;

    private static ProgressDialog progressDialog;
    private static Handler handler = new Handler();

    public static synchronized void showProgressDialog(Context context, String title, String msg) {
        hideProgressDialog();
        progressDialog = ProgressDialog.show(context, title, msg, false, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                hideProgressDialog();
            }
        });
        progressDialog.show();
    }

    public static synchronized boolean isShowing() {
        return progressDialog != null;
    }

    public static synchronized void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static synchronized void showProgressDialog(final Context context, String title, String msg, final String timeoutMsg) {
        hideProgressDialog();
        progressDialog = ProgressDialog.show(context, title, msg);
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    ToastUtils.toast(context, timeoutMsg);
                }
                hideProgressDialog();
            }
        }, TIMEOUT);
    }

}

