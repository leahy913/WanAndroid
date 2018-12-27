package com.leahy.utils.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.leahy.utils.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
public class CommonUtils {

    private static Application application;

    public static void init(Application application) {
        CommonUtils.application = application;
    }

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }

    /**
     * 得到年月日的"日"
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("dd");
        return dateFm.format(date);
    }


    public static Drawable getDrawable(int resid) {
        return getResoure().getDrawable(resid);
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static Resources getResoure() {
        return application.getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

    private static List<Integer> colors;

    public static int getRandomColor() {
        if (colors == null) {
            colors = new ArrayList<>();
            colors.add(getResoure().getColor(R.color.color_1));
            colors.add(getResoure().getColor(R.color.color_2));
            colors.add(getResoure().getColor(R.color.color_3));
            colors.add(getResoure().getColor(R.color.color_4));
            colors.add(getResoure().getColor(R.color.color_5));
            colors.add(getResoure().getColor(R.color.color_6));
            colors.add(getResoure().getColor(R.color.color_7));
            colors.add(getResoure().getColor(R.color.color_8));
            colors.add(getResoure().getColor(R.color.color_9));
            colors.add(getResoure().getColor(R.color.color_10));
            colors.add(getResoure().getColor(R.color.color_11));
            colors.add(getResoure().getColor(R.color.color_12));
            colors.add(getResoure().getColor(R.color.color_13));
            colors.add(getResoure().getColor(R.color.color_14));
            colors.add(getResoure().getColor(R.color.color_15));
            colors.add(getResoure().getColor(R.color.color_16));
            colors.add(getResoure().getColor(R.color.color_17));
            colors.add(getResoure().getColor(R.color.color_18));
            colors.add(getResoure().getColor(R.color.color_19));
            colors.add(getResoure().getColor(R.color.color_20));
        }
        Collections.shuffle(colors);
        return colors.get(0);
    }


}
