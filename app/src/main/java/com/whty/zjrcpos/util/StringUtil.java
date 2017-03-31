package com.whty.zjrcpos.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：字符串处理类.
 */
public final class StringUtil {

    /**
     * .描述：将null转化为“”. .@param str 指定的字符串 .@return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    /**
     * .描述：判断一个字符串是否为null或空值. .@param str 指定的字符串 .@return true or false
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || "null".equals(str) || "NULL".equals(str)
                || "".equals(str.trim()) || "[]".equals(str);
        // return str == null || str.trim().length() == 0;
    }

    /**
     * 十六进制字符串转换成bytes
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return bytes;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param byte[] b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
//            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * @param String strInput
     * @param byte[] arryByte
     * @return int
     * @author 杨工
     */
    static public int StringToByteArray(String strInput, byte[] arryByte) {
        strInput = strInput.trim();// 清除空白
        String[] arryString = strInput.split(" ");
        if (arryByte.length < arryString.length)
            return -1;
        for (int i = 0; i < arryString.length; i++) {
            if (!CheckString(arryString[i]))
                return -1;
            arryByte[i] = StringToByte(arryString[i]);
            // Log.i("APP", String.format("%02X", arryByte[i]));
        }

        return arryString.length;
    }

    static public String ByteArrayToString(byte[] arryByte, int nDataLength) {
        String strOut = new String();
        for (int i = 0; i < nDataLength; i++)
            strOut += String.format("%02X ", arryByte[i]);
        return strOut;
    }

    public static String byteArray2String(byte[] arryByte) {
        String strOut = new String();
        for (int i = 0; i < arryByte.length; i++)
            strOut += String.format("%02X ", arryByte[i]);
        return strOut;
    }

    /**
     * ..获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串 .@return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;

    /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
        /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
        /* 判断是否为中文字符 */
                String chinese = "[\u0391-\uFFE5]";
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    // 中文字符长度为2
                    valueLength += 2;
                } else {
                    // 其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;
        try {
            // Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0,5-9]))\\d{8}$");
            Pattern p = Pattern.compile("^1\\d{10}$");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMobileNo;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    /**
     * 描述：是否只是字母和数字组合.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetterCombinations(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    public static boolean pwdCheck(String input) {
        if (input == null) {
            return false;
        }
        return input.matches("(?!\\d+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,}");
    }

    /**
     * 描述：是否只是字母和数字汉字日文韩文.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9\u3400-\u9FFF]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    // ^([\u4e00-\u9fa5]+|([a-z]+\s?)+)$
    public static Boolean isAllInChineseOrEnglish(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-z]+$|^[\u4E00-\u9FA5]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        Boolean isNumber = false;
        String expr = "^-?[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }


    /**
     * 描述：是否是浮点数.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isDecimal(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Boolean isNumber = false;
        String expr = "^-?[0-9]+\\.?[0-9]+$";
        String expr2 = "^-?[0-9]+$";
        if (str.matches(expr2) || str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr =
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 描述：是否只含有数字字母
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberOrLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }


    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：从输入流中获得String.
     *
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            // 最后一个\n删除
            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        String[] date = str.split("-");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] date = str.split(":");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    文本
     * @param length 字节长度
     * @param dot    省略符号
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length, String dot) {
        int strBLen = strlen(str, "GBK");
        if (strBLen <= length) {
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        char[] ch = str.toCharArray();
        for (char c : ch) {
            sb.append(c);
            if (c > 256) {
                temp += 2;
            } else {
                temp += 1;
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot);
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1   原文本
     * @param str2   指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        }
        int start = str1.indexOf(str2);
        if (start != -1) {
            if (str1.length() > start + offset) {
                return str1.substring(start + offset);
            }
        }
        return "";
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str     文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    public static int strlen(String str, String charset) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        try {
            length = str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return 大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size = size >> 10;
            if (size >= 1024) {
                suffix = "M";
                // size /= 1024;
                size = size >> 10;
                if (size >= 1024) {
                    suffix = "G";
                    size = size >> 10;
                    // size /= 1024;
                }
            }
        }
        return size + suffix;
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip) {
        ip = ip.replace(".", ",");
        String[] items = ip.split(",");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16
                | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }

    public static boolean isHtml(String value) {
        if (TextUtils.isEmpty(value))
            return false;
        int start = value.indexOf("<");
        int end = value.indexOf(">");
        if (start < 0 || end < 0)
            return false;
        String rootHtml = value.substring(start, end + 1);
        String endHtml = rootHtml.replace("<", "</");
        if (value.indexOf(endHtml) > 0)
            return true;
        return false;
    }

    public static String getErrorInfo(Object value) {
        return "网络异常，请稍后重试";
    }

    /**
     * replaceBlank:【去除字符串中的空格、回车、换行符、制表符】. <br/>
     * .@param str .@return.<br/>
     */
    public static String replaceBlank(String str) {

        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\u005cn");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getFormatStr(Context conext, int resId, Object... obj) {
        String resStr = conext.getString(resId);
        resStr = String.format(resStr, obj);
        return resStr;
    }

    public static String scientificToString(String value) {
        try {
            value = value.replaceAll(",", "");
            BigDecimal bigDecimal = new BigDecimal(value);
            value = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * getMessage:【得到message消息】. <br/>
     * .@param result
     * .@return.<br/>
     */
    public static String getMessage(JSONObject result) {
        if (result == null) {
            return "";
        } else if (!isEmpty(result.optString("message"))) {
            return result.optString("message");
        } else if (!isEmpty(result.optString("msg"))) {
            return result.optString("msg");
        }
        return "";
    }


    /**
     * pathToURL:【如果是本地文件显示本地路径,否则显示它本身地址】. <br/>
     * .@param urlPath .@return.<br/>
     */
    public static String pathToURL(String urlPath) {
        String newPaht = "";
        if (!TextUtils.isEmpty(urlPath)) {
            File file = new File(urlPath);
            if (file.exists()) {
                Uri parse = Uri.fromFile(file);
                newPaht = Uri.decode(parse.toString());
            } else {
                newPaht = urlPath;
            }
        }
        return newPaht;
    }

    //阿拉伯数字转中文,如果超过10返回本身
    public static String numToChinese(int num) {
        String[] bigNum = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if (num < bigNum.length) {
            return bigNum[num];
        }
        return num + "";
    }

    /**
     * ByteBuffer 转换 String
     *
     * @param buffer
     * @return
     */
    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    //  char转化为byte：
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static byte[] getBytes(char[] chars) {//将字符转为字节(编码)
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static StringBuffer getStringBuffer(byte[] b, int len) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < ((len == 0) ? b.length : len); i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
            sb.append(",");
        }
        return sb;
    }

    /**
     * 身份证的有效验证
     *
     * @param IDStr
     * @return
     * @throws NumberFormatException
     * @throwsParseException
     */
    public static String IDCardValidate(String IDStr) throws NumberFormatException, ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        //号码的长度 15位或18位
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        //数字  除最后以外都为数字
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        //出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                || (gc.getTime().getTime() - s.parse(
                strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
            errorInfo = "身份证生日不在有效范围。";
            return errorInfo;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        //地区码是否有效
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        //判断最后一位的值
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    private static boolean isDataFormat(String str) {
        boolean flag = false;
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 利用BufferedReader实现Inputstream转换成String <功能详细描述>
     *
     * @param in
     * @return String
     */

    public static String Inputstr2Str_Reader(InputStream in, String encode) {

        String str = "";
        try {
            if (encode == null || encode.equals("")) {
                // 默认以utf-8形式
                encode = "utf-8";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
            StringBuffer sb = new StringBuffer();

            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 利用byte数组转换InputStream------->String <功能详细描述>
     *
     * @param in
     * @return
     * @see [类、类#方法、类#成员]
     */

    public static String Inputstr2Str_byteArr(InputStream in, String encode) {
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[1024];
        int len = 0;
        try {
            if (encode == null || encode.equals("")) {
                // 默认以utf-8形式
                encode = "utf-8";
            }
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len, encode));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 利用ByteArrayOutputStream：Inputstream------------>String <功能详细描述>
     *
     * @param in
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String Inputstr2Str_ByteArrayOutputStream(InputStream in, String encode) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        try {
            if (encode == null || encode.equals("")) {
                // 默认以utf-8形式
                encode = "utf-8";
            }
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            return out.toString(encode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 利用ByteArrayInputStream：String------------------>InputStream <功能详细描述>
     *
     * @param inStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static InputStream Str2Inputstr(String inStr) {
        try {
            // return new ByteArrayInputStream(inStr.getBytes());
            // return new ByteArrayInputStream(inStr.getBytes("UTF-8"));
            return new StringBufferInputStream(inStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;

    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 手机号处理(隐藏中间四位)
     *
     * @param phoneNum
     * @param hideLenth
     * @return
     */
    public static String hidePhoneNum(String phoneNum, int hideLenth) {
        if (phoneNum == null) {
            return phoneNum;
        }
        StringBuilder sb = new StringBuilder();
        if (phoneNum.length() <= hideLenth) {
            for (int i = 0; i < phoneNum.length(); i++) {
                sb.append('*');
            }
            return sb.toString();
        }
        sb.append(phoneNum);
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < hideLenth; i++) {
            stars.append('*');
        }
        int start = (phoneNum.length() - hideLenth) / 2;
        sb.replace(start, start + stars.length(), stars.toString());
        return sb.toString();
    }

    /**
     * 身份证号转*工具（身份证号码应只显示前3位和末2位）
     *
     * @param idCardNo 身份证号
     * @return
     */
    public static String idCardToStar(String idCardNo, int headPosition, int endPosition) {
        if (idCardNo.length() > (headPosition + endPosition)) {
            // 获取银行卡前几位数字
            String head = idCardNo.substring(0, headPosition);
            // 获得银行卡中间几位数字
            String mid = idCardNo.substring(headPosition, idCardNo.length() - endPosition);
            // 获得银行卡末尾几位数字
            String end = idCardNo.substring(idCardNo.length() - endPosition, idCardNo.length());
            // 将中间数字进行类型转换
            char[] midNo = mid.toCharArray();
            // 对中间数字进行处理，变为*
            for (int i = 0; i < midNo.length; i++) {
                midNo[i] = '*';
            }
            mid = String.valueOf(midNo);
            // 合并前中后三部分
            idCardNo = head + mid + end;
//      System.out.println(idCardNo);
        }
        return idCardNo;
    }

    /**
     * 卡片格式分隔设置
     * 6230910 199012 870099
     *
     * @param cardId
     * @return
     */
    public static String cardNoFormat(String cardId) {
        StringBuilder sb = new StringBuilder(cardId);
        sb.insert(6, " ");
        sb.insert(13, " ");
        return sb.toString();
    }


    static protected boolean CheckByte(byte byteIn) {
        // '0' - '9'
        if (byteIn <= 0x39 && byteIn >= 0x30)
            return true;
        // 'A' - 'F'
        if (byteIn <= 0x46 && byteIn >= 0x41)
            return true;
        // 'a' - 'f'
        if (byteIn <= 0x66 && byteIn >= 0x61)
            return true;
        return false;
    }

    static protected boolean CheckString(String strInput) {
        strInput = strInput.trim();
        if (strInput.length() != 2)
            return false;
        byte[] byteArry = strInput.getBytes();
        for (int i = 0; i < 2; i++) {
            if (!CheckByte(byteArry[i]))
                return false;
        }
        return true;
    }

    static protected byte StringToByte(String strInput) {
        byte[] byteArry = strInput.getBytes();
        for (int i = 0; i < 2; i++) {

            if (byteArry[i] <= 0x39 && byteArry[i] >= 0x30) {
                byteArry[i] -= 0x30;
            } else if (byteArry[i] <= 0x46 && byteArry[i] >= 0x41) {
                byteArry[i] -= 0x37;
            } else if (byteArry[i] <= 0x66 && byteArry[i] >= 0x61) {
                byteArry[i] -= 0x57;
            }
        }
        // Log.i("APP", String.format("byteArry[0] = 0x%X\n", byteArry[0]));
        // Log.i("APP", String.format("byteArry[1] = 0x%X\n", byteArry[1]));
        return (byte) ((byteArry[0] << 4) | (byteArry[1] & 0x0F));
    }


    /**
     * @param String str 传入字符串
     * @param String reg 按照哪种方式或哪个字段拆分
     * @return Stringp[] 返回拆分后的数组。
     * @author john.li
     */
    static public String[] spiltStrings(String str, String reg) {
        String[] arrayStr = str.split(reg);
        return arrayStr;
    }
}

