package com.leahy.wanandroid.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.leahy.utils.utils.CommonUtils;
import com.leahy.utils.utils.GlideUtils;
import com.leahy.wanandroid.network.http.YcxOkGo;

import org.litepal.LitePal;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/12/24
 * Description: App
 */
public class App extends Application {

    private static App app;
    public static int START_PAGE = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //资源文件获取工具
        CommonUtils.init(this);
        //初始化图片框架
        GlideUtils.init(this);
        //初始化工具类
        Utils.init(this);
        //数据库
        LitePal.initialize(this);
        //设置 Log 全局 tag
        LogUtils.getConfig().setGlobalTag("Ycx");
        //初始化网络
        YcxOkGo.init(this);

        // 设置 app 不随着系统字体的调整而变化
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static App getApp() {
        return app;
    }

    public static void setApp(App app) {
        App.app = app;
    }
}
