package com.permission.app.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.permission.app.Dialogs.LocationDialog;
import com.permission.app.Interface.iOnChangeLocation;
import com.permission.app.Listener.mLocationListener;
import com.permission.app.R;

public class MainActivity extends Activity implements iOnChangeLocation {

    private static final int PERMISSIONS_SEND_SMS = 11;
    private TextView tvLocation;
    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private EditText etPhone;
    private EditText etSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context mContext = MainActivity.this;

        //Defind Location Manager And a Dialog
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationDialog dialog = new LocationDialog(MainActivity.this);

        //Defind Components
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        Button btnLocation = (Button) findViewById(R.id.btnLocation);
        Button btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSMS = (EditText) findViewById(R.id.etSMS);

        //Defind Events
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    dialog.buildAlertMessageNoGps();
                } else {
                    boolean permissionGranted = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                    if(permissionGranted) {
                        mLocationListener locationListener = new mLocationListener(MainActivity.this);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
                    }
                }
            }
        });

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean permissionGranted = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
                if(permissionGranted) {
                    SendSMS();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_SEND_SMS);
                }
            }
        });


    }

    @Override
    public void onChangeLocation(double lat, double lng) {
        tvLocation.setText("Lat: " + String.valueOf(lat) + "\nLng: " + String.valueOf(lng));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    mLocationListener locationListener = new mLocationListener(MainActivity.this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            case PERMISSIONS_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    else {
                        SendSMS();
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
            return;
        }
    }

    private void SendSMS() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + etPhone.getText().toString()));
        intent.putExtra("sms_body", etSMS.getText().toString());
        startActivity(intent);
    }


}
