package io.github.passioninfinite.knowit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gimbal.android.Gimbal;

public class CompanyDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        Gimbal.stop();
        KnowitApplication.manager.stopListening();
        Intent intent = getIntent();
    }
}
