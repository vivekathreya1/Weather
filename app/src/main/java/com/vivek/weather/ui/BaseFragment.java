package com.vivek.weather.ui;

import android.content.Intent;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dagger.android.support.DaggerFragment;
import mumayank.com.airlocationlibrary.AirLocation;

public abstract class BaseFragment extends DaggerFragment {
    protected AirLocation airLocation;

    protected void getLocation(){
        airLocation = new AirLocation(getActivity(), true, false, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(Location location) {
                setLocation(location);

            }

            @Override
            public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
               setLocationError(locationFailedEnum);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public abstract void setLocation(Location location);
    public abstract void setLocationError(AirLocation.LocationFailedEnum locationFailedEnum);

}
