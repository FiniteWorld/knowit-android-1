package io.github.passioninfinite.knowit;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gimbal.android.Gimbal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FairDetail extends AppCompatActivity {

    private String fairId;

    private static String URL = "https://api.knowit-app.com/fairs";

    public TextView name, location;

    public FairDetailAdapter fairDetailAdapter;

    private List<Company> companies = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_detail);
        Gimbal.start();
        KnowitApplication.manager.startListening();
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

                    Log.d("check_length", String.valueOf(companyList.length()));
                    for (int i=0; i< companyList.length();i++) {
                        JSONObject object = companyList.getJSONObject(i);
                        String name = object.getString("name");
                        String location = object.getString("branch");
                        String imageUrl = "";
                        if (object.has("image_url")) {
                            imageUrl = object.getString("image_url");
                        }
                        Log.d("check_name", imageUrl);
                        Company company = new Company(name, location, imageUrl);
                        companies.add(company);
                        Log.d("check_name", String.valueOf(companies.size()));
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

}


