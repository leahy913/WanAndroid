package com.leahy.utils.listener;

import android.graphics.Rect;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Author: leahy
 * Time:   2018/8/4
 * Description: 显示或隐藏 输入框光标
 */

public class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {


    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private EditText editText;

    public GlobalLayoutListener(RelativeLayout relativeLayout, EditText editText) {
        this.relativeLayout = relativeLayout;
        this.editText = editText;
    }

    public GlobalLayoutListener(LinearLayout linearLayout, EditText editText) {
        this.linearLayout = linearLayout;
        this.editText = editText;
    }


    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        int screenHeight;
        if (linearLayout != null) {
            linearLayout.getWindowVisibleDisplayFrame(r);
            screenHeight = linearLayout.getRootView().getHeight();
        } else {
            relativeLayout.getWindowVisibleDisplayFrame(r);
            screenHeight = relativeLayout.getRootView().getHeight();
        }
        int heightDifference = screenHeight - (r.bottom);
        if (heightDifference > 200) {
            //软键盘显示
            editText.setCursorVisible(true);
        } else {
            //软键盘隐藏
            editText.setCursorVisible(false);
        }
    }
}
