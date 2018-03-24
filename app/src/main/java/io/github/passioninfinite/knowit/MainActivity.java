package io.github.passioninfinite.knowit;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gimbal.android.Gimbal;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url = "https://api.knowit-app.com/fairs";
    private RecyclerView recyclerView;
    private FairsAdapter fairsAdapter;
    private List<Fair> fairList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Permissions.check(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                }, "Location, Wifi and Bluetooth permissions are required to use this application.", new Permissions.Options()
                        .setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Permissions Required"),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBluetoothAdapter.isEnabled()) {
                            mBluetoothAdapter.enable();
                        }
                        statusCheck();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);
                        finish();
                        System.exit(0);
                    }
                }
        );
        setTheme(R.style.AppTheme);
        Gimbal.stop();
        KnowitApplication.manager.stopListening();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestData();

        recyclerView = findViewById(R.id.card_recycler_view);
        fairsAdapter = new FairsAdapter(fairList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fairsAdapter);
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager != null && !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null && connectivityManager.getActiveNetworkInfo() == null) {
            buildAlertMessageNoInternet();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageNoInternet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No internet connection")
                .setCancelable(false)
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void requestData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray fairs = jsonObject.getJSONArray("data");

                    for(int i=0; i< fairs.length(); i++) {
                        JSONObject object = fairs.getJSONObject(i);
                        String name = object.getString("name");
                        String location = object.getString("location");
                        String start_time = object.getString("start_time");
                        String end_time = object.getString("end_time");
                        String date = object.getString("date");
                        String id = object.getString("_id");
                        Fair fair = new Fair(id, name, location, start_time, end_time, date);
                        fairList.add(fair);
                        fairsAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    public void onResume() {
        super.onResume();
        requestData();
        KnowitApplication.manager.stopListening();
        Gimbal.stop();
    }
}