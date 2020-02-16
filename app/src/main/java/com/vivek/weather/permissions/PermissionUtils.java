package com.vivek.weather.permissions;

import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class PermissionUtils {

    public static void getPermission(Activity activity, String permission, PermissionListener listener, PermissionRequestErrorListener errorListener){
        Dexter.withActivity(activity).withPermission(permission).withListener(listener).withErrorListener(errorListener).onSameThread().check();

    }

}
