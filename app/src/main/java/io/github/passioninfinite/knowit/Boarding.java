package io.github.passioninfinite.knowit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class Boarding extends AppCompatActivity {

    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreen = preferences.getBoolean("welcomeScreenShown", false);
        if (!welcomeScreen) {
            setContentView(R.layout.onboarding_main_layout);
            PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnBoarding(), getApplicationContext());

            engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
                @Override
                public void onRightOut() {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("welcomeScreenShown", true);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {

        PaperOnboardingPage scr1 = new PaperOnboardingPage("Job Fairs",
                "All job fairs are shown on the very first page.",
                Color.parseColor("#FF0000"), R.drawable.lists, R.drawable.lists);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Companies",
                "You can get information regarding which companies are coming to the job fair.",
                Color.parseColor("#008EEC"), R.drawable.company, R.drawable.company);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Company Table",
                "Go near to the company table and get information about the company.",
                Color.parseColor("#F97500"), R.drawable.table, R.drawable.table);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}
