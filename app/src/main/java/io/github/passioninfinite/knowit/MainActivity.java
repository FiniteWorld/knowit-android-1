package io.github.passioninfinite.knowit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    String url = "https://api.knowit-app.com/fairs";
    private RecyclerView recyclerView;
    private FairsAdapter fairsAdapter;
    private List<Fair> fairList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}