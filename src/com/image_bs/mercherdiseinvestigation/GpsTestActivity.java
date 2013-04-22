package com.image_bs.mercherdiseinvestigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GpsTestActivity extends Activity {
    //Set parameters for location updates
    private static final long minimumDistanceChangeBeforeUpdate = 10; //Meters
    private static final long minimumUpdatePeriod = 10000; //In Milliseconds

    protected LocationManager locationManager;
    protected Button gpsRequestButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        gpsRequestButton = (Button) findViewById(R.id.gpsRequestButton);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumUpdatePeriod, minimumDistanceChangeBeforeUpdate, new MyLocationListener());

    gpsRequestButton.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            showCurrentLocation();
        }
    });
    }   

    protected void showCurrentLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null){
            String alert = String.format("Location \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
            Toast.makeText(GpsTestActivity.this, alert, Toast.LENGTH_LONG).show();
        }
    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String alert = String.format("Location Updated \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
            Toast.makeText(GpsTestActivity.this, alert, Toast.LENGTH_LONG).show();
            //postData(location);
        }

        public void onStatusChanged(String s, int i, Bundle b){
            Toast.makeText(GpsTestActivity.this, "Provider Status Updated", Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(GpsTestActivity.this, "GPS Deactivated", Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(GpsTestActivity.this, "GPS Activated", Toast.LENGTH_LONG).show();
        }
        /*a
        public void postData(Location location) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("192.168.1.229/appTesting/gpsdb.php");
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("gpsLastKnown","location"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        } 
        */
}}