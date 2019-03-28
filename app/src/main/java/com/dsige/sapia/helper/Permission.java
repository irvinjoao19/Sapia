package com.dsige.sapia.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class Permission {

    public static int PERMISSION_ALL = 1;

    public static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO};

    public static int CAMERA_REQUEST = 1;
    public static int GALERY_REQUEST = 2;
    public static int SPEECH_REQUEST_DETALLE = 3;
    public static int PERSONAL_REQUEST = 4;
    public static int PERSONAL_INSERT_REQUEST = 5;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
