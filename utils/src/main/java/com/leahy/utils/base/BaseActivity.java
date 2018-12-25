package com.leahy.utils.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leahy.utils.R;
import com.leahy.utils.databinding.ActivityBaseBinding;
import com.leahy.utils.listener.PerfectClickListener;
import com.leahy.utils.statusbar.StatusBarUtil;
import com.leahy.utils.swipeback.app.SwipeBackActivity;
import com.leahy.utils.utils.CommonUtils;


public class BaseActivity<SV extends ViewDataBinding> extends SwipeBackActivity {

    // 布局view
    protected SV mBinding;
    private LinearLayout llProgressBar;
    private View refresh;
    private ActivityBaseBinding mBaseBinding;
    private ActionBar actionBar;
    protected Activity mActivity;


    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        mActivity = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(mBinding.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());


        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.ColorStatusBar), 0);
        llProgressBar = getView(R.id.ll_progress_bar);
        refresh = getView(R.id.ll_error_refresh);


        setToolBar();
        // 点击加载失败布局
        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        mBinding.getRoot().setVisibility(View.GONE);
    }

    /**
     * 设置titlebar
     */
    protected void setToolBar() {
        setSupportActionBar(mBaseBinding.include.toolBar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        mBaseBinding.include.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeftMenu(v);
            }
        });


    }


    public void setTitle(CharSequence text) {
        mBaseBinding.include.title.setText(text);
    }

    public TextView getTitles() {
        return mBaseBinding.include.title;
    }

    public void closeFocus() {
        mBaseBinding.include.title.setFocusable(false);
        mBaseBinding.include.title.setFocusableInTouchMode(false);
    }

    protected Toolbar getToolbar() {
        return mBaseBinding.include.toolBar;
    }


    protected void hideToolbar() {
        mBaseBinding.include.toolBar.setVisibility(View.GONE);
    }

    protected ImageView getBackPressed() {
        return mBaseBinding.include.toolbarBack;
    }

    /**
     * 隐藏自定义返回按钮
     */
    protected void hideBackPressed() {
        mBaseBinding.include.toolbarBack.setVisibility(View.GONE);
    }

    /**
     * 显示系统的返回按钮
     */
    protected void showBackPressed() {
        mBaseBinding.include.toolbarBack.setVisibility(View.GONE);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        mBaseBinding.include.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeftMenu(v);
            }
        });
    }

    /**
     * 左菜单
     */
    protected void setLeftMenuText(String left) {
        mBaseBinding.include.toolbarBack.setVisibility(View.GONE);
        mBaseBinding.include.toolbarLiftMenu.setVisibility(View.VISIBLE);
        mBaseBinding.include.toolbarLiftMenu.setText(left);
        mBaseBinding.include.toolbarLiftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeftMenu(v);
            }
        });
    }

    protected void onClickLeftMenu(View v) {
        onBackPressed();
    }

    protected TextView getLeftMenu() {
        return mBaseBinding.include.toolbarLiftMenu;
    }


    /**
     * 右菜单
     */
    protected TextView setRightMenuText(String right) {
        mBaseBinding.include.toolbarRightMenu.setVisibility(View.VISIBLE);
        mBaseBinding.include.toolbarRightMenu.setText(right);
        mBaseBinding.include.toolbarRightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRightMenu(v);
            }
        });
        return mBaseBinding.include.toolbarRightMenu;
    }

    protected void setRightMenuText(int icon, String right) {
        mBaseBinding.include.toolbarRightMenuLayout.setVisibility(View.VISIBLE);
        mBaseBinding.include.toolbarRightMenuTextIcon.setImageResource(icon);
        mBaseBinding.include.toolbarRightMenuText.setText(right);
        mBaseBinding.include.toolbarRightMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRightMenu(v);
            }
        });
    }

    protected void setRightMenuIcon(int icon) {
        mBaseBinding.include.toolbarRightMenuIcon.setVisibility(View.VISIBLE);
        mBaseBinding.include.toolbarRightMenuIcon.setImageResource(icon);
        mBaseBinding.include.toolbarRightMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRightMenu(v);
            }
        });
    }

    protected void setRightAssistMenuIcon(int icon) {
        mBaseBinding.include.toolbarRightAssistMenu.setVisibility(View.VISIBLE);
        mBaseBinding.include.toolbarRightAssistMenu.setImageResource(icon);
        mBaseBinding.include.toolbarRightAssistMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRightAssistMenu(v);
            }
        });
    }

    protected TextView getRightMenuText() {
        return mBaseBinding.include.toolbarRightMenuText;
    }

    protected ImageView getRightMenuIcon() {
        return mBaseBinding.include.toolbarRightMenuIcon;
    }


    protected TextView getRightMenu() {
        return mBaseBinding.include.toolbarRightMenu;
    }

    protected LinearLayout getRightMenuLayot() {
        return mBaseBinding.include.toolbarRightMenuLayout;
    }

    /**
     * 右菜单事件监听
     */
    protected void onClickRightMenu(View v) {

    }

    /**
     * 辅助右菜单事件监听
     */
    protected void onClickRightAssistMenu(View v) {

    }


    protected void showLoading() {
        if (llProgressBar.getVisibility() != View.VISIBLE) {
            llProgressBar.setVisibility(View.VISIBLE);
        }

        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }

        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (mBinding.getRoot().getVisibility() != View.VISIBLE) {
            mBinding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }

        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
        }
        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
    }


    protected void setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(CommonUtils.getColor(R.color.windowBackground));
        }
    }

    protected void setNavigationBarColor(int resid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(CommonUtils.getColor(resid));
        }
    }


    /**
     * 失败后点击刷新
     */
    public void onRefresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
