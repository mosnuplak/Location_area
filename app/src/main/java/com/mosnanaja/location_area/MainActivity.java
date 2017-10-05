package com.mosnanaja.location_area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.btnStart);
        stop = (Button)findViewById(R.id.btnStop);
        text = (TextView)findViewById(R.id.textView);

        Double latitudeSJ = 13.8092349;
        Double longitudeSJ = 100.55937;
        Double latitudeSunPlaza = 13.8072369;
        Double longitudeSunPlaza = 100.5576056;
        text.setText(distance(latitudeSJ,longitudeSJ,latitudeSunPlaza,longitudeSunPlaza)+"");
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, GPS_service.class));
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, GPS_service.class));
            }
        });
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
}
