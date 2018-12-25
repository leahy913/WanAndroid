package com.leahy.utils.utils;

import android.support.design.widget.TabLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/9/3
 * Description: 修改TabLayout指示器颜色
 */

public class TabLayoutUtils {


    public static void setSelectedIndicatorColor(TabLayout tabLayout, int colors) {
        try {
            //拿到tabLayout的mTabStrip属性
            Field field = TabLayout.class.getDeclaredField("mTabStrip");
            field.setAccessible(true);
            //拿mTabStrip属性里面的值
            Object mTabStrip = field.get(tabLayout);
            //通过mTabStrip对象来获取class类，不能用field来获取class类，参数不能写Integer.class
            Method method = mTabStrip.getClass().getDeclaredMethod("setSelectedIndicatorColor", int.class);
            method.setAccessible(true);
            method.invoke(mTabStrip, CommonUtils.getColor(colors));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
