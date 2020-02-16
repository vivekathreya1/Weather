package com.vivek.weather.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vivek.weather.R;
import com.vivek.weather.permissions.LocationPermissionListener;
import com.vivek.weather.permissions.MyErrorListener;
import com.vivek.weather.permissions.PermissionUtils;
import com.vivek.weather.utils.Constants;
import com.vivek.weather.utils.CustomAlertDialogListener;
import com.vivek.weather.utils.UiUtils;

import dagger.android.support.DaggerFragment;
import mumayank.com.airlocationlibrary.AirLocation;

public abstract class BaseFragment extends DaggerFragment implements CustomAlertDialogListener, LocationListener {

    private static final String TAG = "BaseFragment";

    private PermissionListener locationPermissionListener;
    private PermissionRequestErrorListener permissionRequestErrorListener;
    private PermissionToken permissionToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermissions();
    }

    public abstract void setLocation(Location location);

    public abstract void setLocationError();

    protected void getPermissions() {
        locationPermissionListener = new LocationPermissionListener(this);
        permissionRequestErrorListener = new MyErrorListener();
        PermissionUtils.getPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, locationPermissionListener, permissionRequestErrorListener);
    }

    public void showPermissionGranted(String permission) {
        getLocation();
    }

    private void getLocation(){
        new AirLocation(getActivity(), true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(Location location) {
                setLocation(location);
            }

            @Override
            public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
                setLocationError();
            }
        });
    }


    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
        setLocationError();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        permissionToken = token;
        UiUtils.showAlertDialog(this, getActivity(), getString(R.string.location_perm_Heading), getString(R.string.location_perm_rationale),
                getString(R.string.turn_on), getString(R.string.cancel), ContextCompat.getColor(getActivity(), R.color.white));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.GPS_REQUEST) {
//                splashViewModel.requestLocation();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
//            gotoMainActivity( setErrorLocation());
        }
    }

    @Override
    public void onOkClicked() {
        permissionToken.continuePermissionRequest();
    }

    @Override
    public void onCancelClicked() {
        permissionToken.cancelPermissionRequest();
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }
}
