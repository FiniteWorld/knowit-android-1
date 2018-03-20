package io.github.passioninfinite.knowit;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class CompanyDetail extends AppCompatActivity {

    private static String URL = "https://api.knowit-app.com/stickers";

    private List<Job> jobsList = new ArrayList<>();

    private TextView name;

    public JobsAdapter jobsAdapter;

    public RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        Gimbal.stop();
        KnowitApplication.manager.stopListening();
        Intent intent = getIntent();
        String stickerId = intent.getStringExtra("sticker_id");

        recyclerView = findViewById(R.id.company_details_recycler);
        name = findViewById(R.id.company_textName);


        requestData(stickerId);

        jobsAdapter = new JobsAdapter(this.jobsList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(jobsAdapter);
    }

    public void requestData(String stickerId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/" + stickerId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.d("check_response", response);
                try {
                    jsonObject = new JSONObject(response);
                    JSONObject sticker = jsonObject.getJSONObject("data");
                    JSONObject company = sticker.getJSONObject("company");
                    JSONArray jobs = company.getJSONArray("jobs");
                    name.setText(company.getString("name"));
                    String companyEmail = company.getString("email");
                    for (int i=0; i< jobs.length();i++) {
                        JSONObject object = jobs.getJSONObject(i);
                        String name = object.getString("position");
                        String description = object.getString("description");
                        String experience = object.getString("experience");
                        String salary = object.getString("salary_per_hour");
                        String prerequisite = object.getString("prerequisite");
                        String[] type = {};
                        Job job = new Job(name, description, prerequisite, experience, salary, type, companyEmail);
                        jobsList.add(job);
                    }

                    jobsAdapter.notifyDataSetChanged();
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
