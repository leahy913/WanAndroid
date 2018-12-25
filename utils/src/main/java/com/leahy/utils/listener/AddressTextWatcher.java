package com.leahy.utils.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Author: leahy
 * Time:   2018/8/4
 * Description: AddressTextWatcher
 */

public class AddressTextWatcher implements TextWatcher {


    private View view;
    private OnTextWatcherListener listener;

    public AddressTextWatcher(View view, OnTextWatcherListener listener) {
        this.view = view;
        this.listener = listener;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        listener.onTextChanged(view, s.toString());
    }
}
