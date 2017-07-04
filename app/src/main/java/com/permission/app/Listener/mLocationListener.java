package com.permission.app.Listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.permission.app.Activity.MainActivity;

/**
 * Created by Pooya on 7/3/2017.
 */

public class mLocationListener implements LocationListener {

    private final MainActivity mContext;

    public mLocationListener(MainActivity mainActivity) {
        this.mContext = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        mContext.onChangeLocation(loc.getLatitude(), loc.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

}

