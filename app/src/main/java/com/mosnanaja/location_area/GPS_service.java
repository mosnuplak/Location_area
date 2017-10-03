package com.mosnanaja.location_area;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by MSI on 3/10/2560.
 */

public class GPS_service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    LocationManager mLocationManager = null;
    GPSTracker myGPSTracker;
    long minTime = 10000;
    float minDistance = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplication(), "Start Service!", Toast.LENGTH_LONG).show();
        myGPSTracker = new GPSTracker();

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,myGPSTracker);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplication(), "Stop Service!", Toast.LENGTH_LONG).show();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myGPSTracker);
        }
    }
    private class GPSTracker implements android.location.LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            showNotification(location);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
    private void showNotification(Location location) {
        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        Notification notification =
                new NotificationCompat.Builder(this) // this is context
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("อยู่ที่ไหนซักแห่งบนโลกนี้แหละ")
                        .setContentText(latitude+"aaa"+longitude)
                        .setAutoCancel(true)
                        .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }
}

