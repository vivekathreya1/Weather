package com.vivek.weather.permissions;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vivek.weather.ui.BaseFragment;

public class LocationPermissionListener implements PermissionListener {

    private final BaseFragment baseFragment;

    public LocationPermissionListener(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        baseFragment.showPermissionGranted(response.getPermissionName());
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        baseFragment.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        baseFragment.showPermissionRationale(token);
    }
}
