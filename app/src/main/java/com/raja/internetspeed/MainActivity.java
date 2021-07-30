package com.raja.internetspeed;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raja.internetspeed.Support.Connectivity;
import com.raja.internetspeed.Support.TrafficUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "SpeedDetails";

    EditText mob_num;

    Button btn_submit;

    Button btn_history;

    WifiManager wifiManager;

    ConnectivityManager cm;

    NetworkInfo ni;

    NetworkCapabilities nc;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference ref;

    TextView speed_tv;
    TextView txt_clock;
    ImageView src_iv;
    WebView webview;

    SimpleDateFormat dateFormat;

    int a = 1;

    String NWSpeed = "";
    String ConnectionState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init() {

        try {
            initializeLayouts();

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Connectivity.isConnected(getApplicationContext())) {
                        if (mob_num.getText().toString().trim().length() == 10) {

                            ref = database.getReference();


                            ref.child("Details").child(mob_num.getText().toString()).child("State").setValue(ConnectionState);
                            ref.child("Details").child(mob_num.getText().toString()).child("Speed").setValue("" + NWSpeed);
                            ref.child("Details").child(mob_num.getText().toString()).child("timestamp").setValue("" + dateFormat.format(new Date()));

                            Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Turn ON Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getApplicationContext(), History.class));

                }
            });

            RunHandler();

        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }


    }

    public void RunHandler() {

        try {
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {

                    txt_clock.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

                    CheckSpeed();
                }
            }, 500);

        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    public void CheckSpeed() {

        try {
            if (Connectivity.isConnected(getApplicationContext())) {


                webview.loadUrl("http://www.youtube.com");

                NWSpeed = "" + TrafficUtils.getNetworkSpeed();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                    NWSpeed = NWSpeed + ", " + Connectivity.getNetworkType(getApplicationContext());

                }

                Log.d("Traffic_NS", "" + NWSpeed);

                src_iv.setVisibility(View.VISIBLE);

                speed_tv.setText("Speed: " + NWSpeed);

                if (Connectivity.isConnectedMobile(getApplicationContext())) {

                    ConnectionState = "Mobile";

                    src_iv.setImageDrawable(getDrawable(R.drawable.mobile_data));

//                    Log.d(TAG, "" + Connectivity.CheckDataSpeed(ni.getSubtype()));

                } else if (Connectivity.isConnectedWifi(getApplicationContext())) {

                    ConnectionState = "WiFi";

                    src_iv.setImageDrawable(getDrawable(R.drawable.wifi));

                    /*int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                    int level = WifiManager.calculateSignalLevel(linkSpeed, 5);

                    Log.d("wifi_level", "" + level);*/
                }

            } else {

                src_iv.setVisibility(View.GONE);

                speed_tv.setText("Speed: 0.0 Kbps");

            }

            RunHandler();

        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }

    }

    public void initializeLayouts() {

        try {
            btn_submit = (Button) findViewById(R.id.btn_submit);
            btn_history = (Button) findViewById(R.id.btn_history);

            mob_num = (EditText) findViewById(R.id.mob_num);

            src_iv = (ImageView) findViewById(R.id.src_iv);

            webview = (WebView) findViewById(R.id.webview);

            webview.setWebViewClient(new WebViewClient());

            speed_tv = (TextView) findViewById(R.id.speed_tv);
            txt_clock = (TextView) findViewById(R.id.txt_clock);

            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());

            ni = cm.getActiveNetworkInfo();

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);

        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(MainActivity.this, "Enable Permission to Read Phone State", Toast.LENGTH_SHORT).show();
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}