package com.leahy.utils.network;

import java.util.List;

/**
 * Author: leahy
 * Time:   2018/5/18
 * Description: JsonCallback
 */

public interface JsonCallbackArray<T> {



    void onSuccess(List<T> result);

    void onError(int code);

}
