package com.leahy.utils.utils;

import java.util.HashMap;
import java.util.List;


public class EventMsg {

    private String key;
    private String strVal;
    private int intVal;
    private boolean booVal;
    private String[] strsVal;
    private List<String> arrayVal;
    private HashMap<String, String> mapVal;


    public EventMsg(String key) {
        this.key = key;
    }


    public EventMsg(String key, String strVal) {
        this.key = key;
        this.strVal = strVal;
    }

    public EventMsg(String key, int intVal) {
        this.key = key;
        this.intVal = intVal;
    }

    public EventMsg(String key, boolean booVal) {
        this.key = key;
        this.booVal = booVal;
    }

    public EventMsg(String key, String... strsVal) {
        this.key = key;
        this.strsVal = strsVal;
    }

    public EventMsg(String key, List<String> arrayVal) {
        this.key = key;
        this.arrayVal = arrayVal;
    }

    public EventMsg(String key, HashMap<String, String> mapVal) {
        this.key = key;
        this.mapVal = mapVal;
    }


    /**
     * </================================================================>
     */
    public boolean isKey(String key) {
        return key.equals(this.key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * </================================================================>
     */
    public String getStrVal() {
        return strVal;
    }

    public String getStrVal(String key) {
        return key.equals(this.key) ? strVal : null;
    }


    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    /**
     * </================================================================>
     */
    public int getIntVal() {
        return intVal;
    }

    public int getIntVal(String key) {
        return key.equals(this.key) ? intVal : 0;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    /**
     * </================================================================>
     */

    public boolean isBooVal() {
        return booVal;
    }

    public boolean isBooVal(String key) {
        return key.equals(this.key) && booVal;
    }

    public void setBooVal(boolean booVal) {
        this.booVal = booVal;
    }

    /**
     * </================================================================>
     */

    public String[] getStrsVal() {
        return strsVal;
    }

    public void setStrsVal(String[] strsVal) {
        this.strsVal = strsVal;
    }

    /**
     * </================================================================>
     */

    public List<String> getArrayVal() {
        return arrayVal;
    }

    public List<String> getArrayVal(String key) {
        return key.equals(this.key) ? arrayVal : null;
    }

    public void setArrayVal(List<String> arrayVal) {
        this.arrayVal = arrayVal;
    }

    /**
     * </================================================================>
     */
    public HashMap<String, String> getMapVal() {
        return mapVal;
    }

    public HashMap<String, String> getMapVal(String key) {
        return key.equals(this.key) ? mapVal : null;
    }

    public void setMapVal(HashMap<String, String> mapVal) {
        this.mapVal = mapVal;
    }

}
