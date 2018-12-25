package com.leahy.wanandroid.ui.webview;

import android.annotation.TargetApi;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.utils.utils.CommonUtils;
import com.leahy.utils.utils.Show;
import com.leahy.wanandroid.R;
import com.leahy.wanandroid.ui.webview.config.FullscreenHolder;
import com.leahy.wanandroid.ui.webview.config.IWebPageView;
import com.leahy.wanandroid.ui.webview.config.ImageClickInterface;
import com.leahy.wanandroid.ui.webview.config.MyWebChromeClient;
import com.leahy.wanandroid.ui.webview.sonic.OfflinePkgSessionConnection;
import com.leahy.wanandroid.ui.webview.sonic.SonicRuntimeImpl;
import com.leahy.wanandroid.ui.webview.sonic.SonicSessionClientImpl;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;


public class ActivityWeb extends AppCompatActivity implements IWebPageView {

    protected static final String TITLE = "mTitle";
    protected static final String URL = "mUrl";

    // 进度条
    private ProgressBar mProgressBar;
    protected WebView webView;
    // 全屏时视频加载view
    private FrameLayout videoFullView;
    // 加载视频相关
    protected MyWebChromeClient mWebChromeClient;
    // title
    protected String mTitle;
    // 网页链接
    protected String mUrl;
    // 可滚动的title 使用复杂 文字显示有渐变效果，文字两旁没有阴影
    private TextSwitcher mTsTitle;
    // 可滚动的title 使用简单 没有渐变效果，文字两旁有阴影
    private TextView tvGunTitle;
    private Toolbar mTitleToolBar;
    private SonicSession sonicSession;
    private SonicSessionClientImpl sonicSessionClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        setContentView(getLayout());
        initView();
        initSonic();
        initWebView();
    }

    protected int getLayout() {
        return R.layout.activity_web_view;
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(TITLE);
            mUrl = getIntent().getStringExtra(URL);
        }
    }

    private void initSonic() {
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
        sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
            @Override
            public String getCacheData(SonicSession session) {
                return null; // offline pkg does not need cache
            }
        });

        sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
            @Override
            public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                return new OfflinePkgSessionConnection(ActivityWeb.this, session, intent);
            }
        });
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
        sonicSession = SonicEngine.getInstance().createSession(mUrl, new SonicSessionConfig.Builder().build());

        if (sonicSession != null) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        }
    }


    private void initView() {
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.ColorStatusBar), 0);
        mProgressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);
        videoFullView = findViewById(R.id.video_fullView);
        mTsTitle = findViewById(R.id.ts_title);
        tvGunTitle = findViewById(R.id.tv_gun_title);
        mTitleToolBar = findViewById(R.id.tool_bar);

        setSupportActionBar(mTitleToolBar);
        mTitleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));

        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        findViewById(R.id.toolbar_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出网页
                finish();
            }
        });

        tvGunTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvGunTitle.setSelected(true);
            }
        }, 1900);
        setTitle(mTitle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.web_menu_share) {
            // 分享到
            share();
        } else if (i == R.id.web_menu_refresh) {
            // 刷新
            if (webView != null) webView.reload();
        } else if (i == R.id.web_menu_copy) {
            // 复制链接
            ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(webView.getUrl().trim());
            Show.defaults("复制成功");
        } else if (i == R.id.web_menu_open) {
            // 打开链接
            Uri issuesUrl = Uri.parse(webView.getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void share() {
        String shareText = mWebChromeClient.getTitle() + webView.getUrl() + " (玩安卓)";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享"));
    }


    public void setTitle(String mTitle) {
        tvGunTitle.setText(mTitle);
    }

    private void initWebView() {
        mProgressBar.setVisibility(View.VISIBLE);
        WebSettings ws = webView.getSettings();

        ws.setLoadWithOverviewMode(false);  // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setSaveFormData(false);  // 保存表单数据
        ws.setSupportZoom(true);  // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
//        ws.setAppCacheEnabled(true); // 启动应用缓存
//        ws.setCacheMode(WebSettings.LOAD_DEFAULT);  // 设置缓存模式
        ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放。

        ws.setJavaScriptEnabled(true);  // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setBlockNetworkImage(false); //  页面加载好以后，再放开图片
        ws.setDomStorageEnabled(true); // 使用localStorage则必须打开
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); // 排版适应屏幕
//        ws.setSupportMultipleWindows(true);  // WebView是否新窗口打开(加了后可能打不开网页)

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);
        ws.setAllowContentAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setSavePassword(false);

        // 不缩放
        webView.setInitialScale(100);
        mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        webView.addJavascriptInterface(new ImageClickInterface(this, getIsShowPhoto()), "injectedObject");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(mUrl);
        }
    }


    /**
     * @return 是否浏览图片
     */
    protected boolean getIsShowPhoto() {
        return true;
    }

    @Override
    public void hindProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(ActivityWeb.this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView() {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindVideoFullView() {
        videoFullView.setVisibility(View.GONE);
    }

    @Override
    public void startProgress(int newProgress) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(newProgress);
        if (newProgress == 100) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        webView.loadUrl(PHOTO_URL);
        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        webView.loadUrl(INTENT_URL);
    }

    public FrameLayout getVideoFullView() {
        return videoFullView;
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //全屏播放退出全屏
            if (mWebChromeClient.inCustomView()) {
                hideCustomView();
                return true;

                //返回网页上一页
            } else if (webView.canGoBack()) {
                webView.goBack();
                return true;
                //退出网页
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        webView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
        videoFullView.removeAllViews();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
    }

    /**
     * 打开网页:
     *
     * @param mActivity 上下文
     * @param mUrl      要加载的网页url
     * @param mTitle    title
     */
    public static void loadUrl(Context mActivity, String mUrl, String mTitle) {
        if (NetworkUtils.isConnected()) {
            Intent intent = new Intent(mActivity, ActivityWeb.class);
            intent.putExtra("mUrl", mUrl);
            intent.putExtra("mTitle", mTitle == null ? "" : mTitle);
            mActivity.startActivity(intent);
        } else {
            Show.defaults("当前网络不可用，请检查你的网络设置");
        }
    }


    // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
    public static final String PHOTO_URL = "javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\");" +
            "for(var i=0;i<objs.length;i++)" +
            "{" +
            //  "objs[i].onclick=function(){alert(this.getAttribute(\"has_link\"));}" +
            "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
            "}" +
            "})()";

    //属性自定义,用于页面跳转
    public static final String INTENT_URL = "javascript:(function(){" +
            "var objs =document.getElementsByTagName(\"a\");" +
            "for(var i=0;i<objs.length;i++)" +
            "{" +
            "objs[i].onclick=function(){" +
            "window.injectedObject.textClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
            "}" +
            "})()";
}
