package com.leahy.utils.bean;


import com.leahy.utils.network.ParamNames;

import java.util.List;

/**
 * company ：创序信息科技有限公司
 * Author: leahy
 * Time:   2018/9/28
 * Description: CityBean
 */

public class CityBean {

    @ParamNames("citycode")
    private List<String> cityCode;
    @ParamNames("adcode")
    private String adCode;
    private String id;
    private String name;
    private String center;
    private String level;
    private List<CityBean> districts;

    public List<String> getCityCode() {
        return cityCode;
    }

    public void setCityCode(List<String> cityCode) {
        this.cityCode = cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setCenter(String center) {
        this.center = center;
    }

    public String getCenter() {
        return center;
    }


    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }


    public void setDistricts(List<CityBean> districts) {
        this.districts = districts;
    }

    public List<CityBean> getDistricts() {
        return districts;
    }

}