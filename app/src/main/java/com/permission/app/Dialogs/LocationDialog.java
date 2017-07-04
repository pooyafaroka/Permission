package com.permission.app.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.permission.app.Activity.MainActivity;

/**
 * Created by Pooya on 7/3/2017.
 */

public class LocationDialog {

    private final Context mContext;
    private final MainActivity mActivity;

    public LocationDialog(MainActivity mainActivity) {
        this.mContext = mainActivity;
        this.mActivity = mainActivity;
    }

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

//    public void buildAlertMessageGetPermisson() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setMessage("This dialog just appear for explain reason of getting location by gps. Please CONVINCED user by this message.")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        mActivity.onGetPermissonFromUser();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }

}
