package com.leahy.utils.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.leahy.utils.bean.CityBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CityDataUtils {


    /**
     * 获取城市json数据
     */
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 获取详细地址数据
     */
    public static List<CityBean> getCityBean(Context context) {
        String JsonData = getJson(context, "area.json");
        //解析数据
        List<CityBean> jsonBean = JSON.parseArray(JsonData, CityBean.class);
        return jsonBean;
    }

    /**
     * 获取省份
     */
    public static List<String> getProvince(Context context) {
        String JsonData = getJson(context, "area.json");
        //解析数据
        List<CityBean> jsonBean = JSON.parseArray(JsonData, CityBean.class);
        List<String> array = new ArrayList<>();
        for (CityBean cityBean : jsonBean) {
            array.add(cityBean.getName());
        }
        return array;
    }


    private static List<String> options1Items = new ArrayList<>();
    private static List<List<String>> options2Items = new ArrayList<>();
    private static List<List<List<String>>> options3Items = new ArrayList<>();

    public static List<CityBean> initCityOptions(Context context) {
        String JsonData = getJson(context, "area.json");
        //解析数据
        List<CityBean> jsonBean = JSON.parseArray(JsonData, CityBean.class);
        for (CityBean cityBean : jsonBean) {
            options1Items.add(cityBean.getName());
            List<String> city = new ArrayList<>();
            List<List<String>> area = new ArrayList<>();
            for (CityBean bean : cityBean.getDistricts()) {
                //添加城市
                city.add(bean.getName());
                //该城市的所有地区列表
                ArrayList<String> cityArray = new ArrayList<>();
                if (cityBean.getDistricts() == null || bean.getDistricts() == null) {
                    cityArray.add("");
                } else {
                    for (CityBean cityBean1 : bean.getDistricts()) {
                        //添加该城市所有地区数据
                        cityArray.add(cityBean1.getName());
                    }
                }
                area.add(cityArray);
            }
            options2Items.add(city);
            options3Items.add(area);
        }

        return jsonBean;
    }

    public static List<String> getProvinces() {
        return options1Items;
    }

    public static List<List<String>> getCitys() {
        return options2Items;
    }

    public static List<List<List<String>>> getAreas() {
        return options3Items;
    }


    public static String getCityName(List<CityBean> city, int pos1, int pos2, int pos3) {
        String name;
        if (city.get(pos1).getName().equals("北京市") || city.get(pos1).getName().equals("天津市") || city.get(pos1).getName().equals("上海市") || city.get(pos1).getName().equals("重庆市")) {
            name = city.get(pos1).getName() + " - " + city.get(pos1).getDistricts().get(pos2).getDistricts().get(pos3).getName();
        } else {
            name = city.get(pos1).getName() + " - " + city.get(pos1).getDistricts().get(pos2).getName() + " - " + city.get(pos1).getDistricts().get(pos2).getDistricts().get(pos3).getName();
        }
        return name;
    }
}
