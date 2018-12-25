package com.leahy.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.leahy.utils.R;
import com.leahy.utils.image.photodrag.PhotoDragHelper;
import com.leahy.utils.image.photodrag.PhotoDragRelativeLayout;
import com.luck.picture.lib.photoview.PhotoView;


/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/10/19
 * Description: ActivityPhotos
 */

public class ActivityPhotos extends AppCompatActivity implements PhotoDragHelper.OnDragListener {


    private static final String URL = "PhotosUrl";


    private PhotoDragRelativeLayout group;
    private PhotoView photos;
    private RelativeLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        BarUtils.setNavBarVisibility(this,false);
        setContentView(R.layout.activity_photos);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        photos = findViewById(R.id.photo);
        group = findViewById(R.id.group);
        group.setDragListener(new PhotoDragHelper().setOnDragListener(this));

        Glide.with(this)
                .load(getIntent().getStringExtra(URL))
                .into(photos);

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolbar(v);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }

    public static void start(Activity activity, String url) {
        activity.startActivity(new Intent(activity, ActivityPhotos.class).putExtra(URL, url));
    }

    @Override
    public void onAlpha(float alpha) {
        group.setAlpha(alpha);
    }

    @Override
    public View getDragView() {
        return photos;
    }

    @Override
    public void onAnimationEnd(boolean mSlop) {
        if (mSlop) {
            finish();
            overridePendingTransition(0, 0);
        }
    }


    public void showToolbar(View view) {
        if (toolbar.getVisibility() == View.GONE) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.mainactivity_push_up_in));
        } else {
            toolbar.setVisibility(View.GONE);
            toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.mainactivity_push_up_out));
        }
    }
}
