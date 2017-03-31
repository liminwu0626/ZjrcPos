package com.whty.zjrcpos.util;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whty.zjrcpos.MyApplication;
import com.whty.zjrcpos.entity.LocalUserDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
* @function BaseUtils
* @author wulimin
* @time 2017/3/31 上午10:48
*/

public class BaseUtils {

    /**
     * 判断链表第某个位置是否为空
     *
     * @param list
     * @param index
     * @return true:为Null或者size为0
     */
    public static boolean isEmpty(List<?> list, int index) {
        return list == null || list.size() == 0 || list.size() <= index;
    }


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        return dm.heightPixels;
    }


    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        String valueBinary = Integer.toBinaryString(Integer.parseInt(str));
        int doubleValue = Integer.parseInt(valueBinary) * 2;
        return String.valueOf(doubleValue);
    }


    /**
     * 去掉空格并把字符串变为小写
     *
     * @param str
     * @return
     */
    public static String tranLowCase(String str) {
        String string = str.replaceAll(" ", "");
        return string.toLowerCase();
    }


    /**
     * 是否存在sdcard
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 把对象转换为json
     *
     * @param dataModel
     * @return
     */
    public static JSONObject convertJson(LocalUserDataModel dataModel) {
        Gson gson = new Gson();
        String result = gson.toJson(dataModel);
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 把用户数据保存起来
     *
     * @param context
     * @param data
     */
    public static void saveLocalUser(Context context, LocalUserDataModel data) {
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(MyApplication.FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(convertJson(data).toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 读取用户数据
     *
     * @param context
     * @return
     */
    public static LocalUserDataModel readLocalUser(Context context) {
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(MyApplication.FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            Gson gson = new Gson();
            LocalUserDataModel result = gson.fromJson(jsonString.toString(), LocalUserDataModel.class);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean checkLogin(Context context) {
        int uid = readLocalUser(context).getUid();
        boolean isLogin = readLocalUser(context).isLogin();
        if (uid > 0 && isLogin) {
            return true;
        } else {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * md5加密
     *
     * @param content
     * @return
     */
    public static String Md5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
