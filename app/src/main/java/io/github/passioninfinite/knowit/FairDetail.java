package io.github.passioninfinite.knowit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FairDetail extends AppCompatActivity {

    private String fairId;

    private static String URL = "https://api.knowit-app.com/fairs";

    public BeaconEventListener beaconListener;

    public TextView name, location;

    public FairDetailAdapter fairDetailAdapter;

    private List<Company> companies = new ArrayList<>();

    private RecyclerView recyclerView;

    private String stickerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_detail);

        addListenerAndStartListening();

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.company_recycler_view);
        name = findViewById(R.id.fair_title);
        location = findViewById(R.id.fair_location);

        this.fairId = intent.getStringExtra("clicked_fair");

        requestData();

        fairDetailAdapter = new FairDetailAdapter(this.companies);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fairDetailAdapter);
    }

    public void requestData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/" + this.fairId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONObject fair = jsonObject.getJSONObject("data");
                    name.setText(fair.getString("name"));
                    location.setText(fair.getString("location"));

                    JSONArray companyList = fair.getJSONArray("companies");

                    for (int i=0; i< companyList.length();i++) {
                        JSONObject object = companyList.getJSONObject(i);
                        String name = object.getString("name");
                        String location = object.getString("branch");
                        String imageUrl = "";
                        if (object.has("image_url")) {
                            imageUrl = object.getString("image_url");
                        }
                        JSONObject sticker = new JSONObject();
                        if (object.has("sticker")) {
                            if (object.isNull("sticker")) {
                                sticker = new JSONObject();
                            } else {
                                sticker = object.getJSONObject("sticker");
                            }
                        }
                        Company company = new Company(name, location, imageUrl, sticker);
                        companies.add(company);
                    }
                    fairDetailAdapter.notifyDataSetChanged();
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

    private void addListenerAndStartListening() {
        Gimbal.start();
        beaconListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);
                String beaconId = String.valueOf(beaconSighting.getBeacon().getUuid());

                for(int i=0; i < companies.size(); i++) {
                    Company company = companies.get(i);
                    JSONObject sticker = company.getSticker();
//                    Log.d("check_response", sticker.toString());
                    try {
                        String stickerId = sticker.getString("_id");
                        if (stickerId != null && stickerId.equals(beaconId)) {
                            if (getStickerId() == null) {
                                setStickerId(beaconId);
                            }
                            if (!getStickerId().equals(beaconId)) {
                                Toast.makeText(getApplicationContext(), "Checked!", Toast.LENGTH_SHORT).show();
                                setStickerId(beaconId);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        KnowitApplication.manager.addListener(beaconListener);
        KnowitApplication.manager.startListening();
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
        Intent intent = new Intent(this, CompanyDetail.class);
        intent.putExtra("sticker_id", this.stickerId);
        KnowitApplication.manager.stopListening();
        Gimbal.stop();
        startActivity(intent);
    }

    public void setStickerNull() {
        this.stickerId = null;
    }

    public String getStickerId() {
        return this.stickerId;
    }

    public void onResume() {
        super.onResume();
        setStickerNull();
        addListenerAndStartListening();
    }
}
