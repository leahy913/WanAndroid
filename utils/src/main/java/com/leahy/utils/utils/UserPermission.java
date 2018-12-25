package com.leahy.utils.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 * <p>
 * 权限控制
 */

public class UserPermission {

    private static final int WRITE_PERMISSION_REQ_CODE = 100;


    /**
     * 存储权限
     */
    public static boolean storagePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)) {
//                permissions.add(Manifest.permission.CAMERA);
//
//            }

//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }

//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity_guide_index, Manifest.permission.READ_PHONE_STATE)) {
//                permissions.add(Manifest.permission.READ_PHONE_STATE);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity_guide_index, Manifest.permission.CALL_PHONE)) {
//                permissions.add(Manifest.permission.CALL_PHONE);
//            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(activity, (String[]) permissions.toArray(new String[0]), WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    /**
     * 录音权限
     */
    public static boolean audioPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(activity,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    /**
     * 相机
     */
    public static boolean cameraPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(activity, (String[]) permissions.toArray(new String[0]), WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }
}
