package com.leahy.utils.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.leahy.utils.R;

/**
 * Author: leahy
 * Time:   2018/4/28
 * Description: Show
 */

public class Show {

    public static void defaults(Object msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.setBgColor(CommonUtils.getColor(R.color.toast_default));
        ToastUtils.setMsgColor(CommonUtils.getColor(R.color.colorPrimaryDark));
        ToastUtils.showShort(String.valueOf(msg));
    }

    public static void error(Object msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.setBgColor(CommonUtils.getColor(R.color.toast_error));
        ToastUtils.setMsgColor(CommonUtils.getColor(R.color.colorPrimaryDark));
        ToastUtils.showShort(String.valueOf(msg));
    }

    public static void success(Object msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.setBgColor(CommonUtils.getColor(R.color.toast_success));
        ToastUtils.setMsgColor(CommonUtils.getColor(R.color.colorPrimaryDark));
        ToastUtils.showShort(String.valueOf(msg));
    }

    public static void warning(Object msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.setBgColor(CommonUtils.getColor(R.color.toast_warning));
        ToastUtils.setMsgColor(CommonUtils.getColor(R.color.colorPrimaryDark));
        ToastUtils.showShort(String.valueOf(msg));
    }


    public static void snackbar(View view, String hint, String actionText, View.OnClickListener listener) {

        Snackbar snackbar = Snackbar.make(view, hint, Snackbar.LENGTH_LONG).setAction(actionText, listener);
        snackbar.setActionTextColor(CommonUtils.getColor(R.color.text_color_red));
        snackbar.show();

    }

}
