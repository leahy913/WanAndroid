package com.leahy.utils.widget;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.leahy.utils.R;


public class Loading {
    private static PopupWindow loadingView;
    private static Loading loading;
    private Context mActivity;
    private View mContextView;


    public static void show(Context activity) {
        loading = new Loading(activity, null);
    }


    public static void show(Context activity, View mContextView) {
        loading = new Loading(activity, mContextView);
    }

    public static void dismiss() {
        if (loadingView == null) return;
        if (loadingView.isShowing()) {
            loadingView.dismiss();
            loadingView = null;
        }
    }


    public Loading(Context mActivity, View mContextView) {
        this.mActivity = mActivity;
        this.mContextView = mContextView;
        initWindow();
    }


    private void initWindow() {

        View view = LayoutInflater.from(mActivity).inflate(R.layout.loading_view, null);


        view.findViewById(R.id.loading_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        loadingView = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        loadingView.showAtLocation(mContextView == null ? view : mContextView, Gravity.CENTER, 0, 0);

    }

}
