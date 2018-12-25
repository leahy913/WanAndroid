package com.leahy.wanandroid.network.http;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;

import okhttp3.OkHttpClient;

/**
 * Author: leahy
 * Time:   2018/5/18
 * Description: CXOkGo
 */

public class YcxOkGo {

    public static String getUrl() {
        return "http://www.wanandroid.com";
    }


    public static void init(Application app) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(app)));//使用数据库保持cookie，如果cookie不过期，则一直有效


//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS * 2, TimeUnit.MILLISECONDS);
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/

        //全局公共头
//        HttpHeaders header = new HttpHeaders();
//        header.put("Accept", "application/json");


        OkGo.getInstance()
                .init(app)//必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)  //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                           //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(header);                      //全局公共头
//                .addCommonParams(params);                     //全局公共参数

    }


}
