package com.leahy.utils.utils;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: leahy
 * Time:   2018/5/23
 * Description: StringUtils
 */

public class StringUtils {

    /**
     * 修改指定字体大小，颜色
     * <p>
     * content 数据源
     * data 修改text
     * color 更改颜色
     * size  更改字体大小
     */
    public static SpannableStringBuilder style(String content, String data, int color, float size) {

        int sStart = content.indexOf(data);

        int sEnd = sStart + data.length();

        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ForegroundColorSpan(color), sStart, sEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan((int) size), sStart, sEnd, 0);
        return spannable;
    }

    /**
     * 修改指定字体大小，颜色
     * <p>
     * content 数据源
     * data 修改text
     * color 更改颜色
     */
    public static SpannableStringBuilder style(String content, String data, int color) {

        int sStart = content.indexOf(data);

        int sEnd = sStart + data.length();

        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ForegroundColorSpan(color), sStart, sEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return spannable;
    }


    /**
     * 删除字符串中得空格
     */
    public static String replace(String str) {
        try {
            return str.replaceAll("\\s*", "");
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 去首尾回车
     */
    public static String replaceEnter(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            /*   \n 回车(\u000a)   \t 水平制表符(\u0009)   \s 空格(\u0008)   \r 换行(\u000d)   */
            Matcher m = p.matcher(str);
            s = m.replaceFirst("");
        }
        return s;
    }

    /**
     * 关联教材特殊处理
     */
    public static String substing(String str) {
        try {
            return str.substring(0, str.indexOf("\n"));
        } catch (Exception e) {
            return str;
        }
    }


    /**
     * 截取指定字符串之前
     */
    public static String before(String str, String key) {
        try {
            return str.substring(0, str.indexOf(key));
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 截取指定字符串之后
     */
    public static String after(String str, String key) {
        try {
            return str.substring(str.indexOf(key) + 1);
        } catch (Exception e) {
            return str;
        }
    }


    /**
     * list 转字符串
     */
    public static String listAsString(List<String> listStr) {
        StringBuilder orderNumbers = new StringBuilder();
        for (int i = 0; i < listStr.size(); i++) {
            if (i < listStr.size() - 1) {
                orderNumbers.append(listStr.get(i) + ",");
            } else {
                orderNumbers.append(listStr.get(i));
            }
        }
        return orderNumbers.toString();
    }


    /**
     * list 转字符串
     */
    public static String listAsStringCn(List<String> listStr) {
        StringBuilder orderNumbers = new StringBuilder();
        for (int i = 0; i < listStr.size(); i++) {
            if (i < listStr.size() - 1) {
                orderNumbers.append(listStr.get(i) + "，  ");
            } else {
                orderNumbers.append(listStr.get(i));
            }
        }
        return orderNumbers.toString();
    }

    /**
     * 隐藏电话号码中间4位
     */

    public static String maskNumber(String telephone) {
        return telephone.length() != 11 ? telephone : telephone.substring(0, 3) + "****" + telephone.substring(7, telephone.length());
    }


    /**
     * 获取当前时间的差
     */
    public static String getTimeSpanByNow(String time) {
        if (TextUtils.isEmpty(time)) return "";
        @SuppressLint("SimpleDateFormat")
        long millis = TimeUtils.string2Millis(time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
//            return String.format("%tc", millis);
            return "刚刚";
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 获取当前时间的差
     */
    public static String getTimeSpanByNow(long millis) {

        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
//            return String.format("%tc", millis);
            return "刚刚";
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }


    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    /**
     * MD5 加密
     */
    public static String encryptMD5(String password) {
        StringBuffer sb = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 数字转化k
     */
    public static String valueOfK(int num) {
        if (num < 1000) return String.valueOf(num);
        return new DecimalFormat("#.0").format((double) num / 1000) + "k";
    }


    /**
     * 保留小数
     */
    public static String valueOfPoint(String content, int num) {
        StringBuilder sbs = new StringBuilder("#.");
        for (int i = 0; i < num; i++) {
            sbs.append("0");
        }
        return new DecimalFormat(sbs.toString()).format(Float.parseFloat(content));
    }

}
