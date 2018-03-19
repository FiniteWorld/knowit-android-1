package io.github.passioninfinite.knowit;

import android.app.Application;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

/**
 * Created by passioninfinite on 18/3/18.
 */

public class KnowitApplication extends Application{

    public static String API_KEY = "e2504169-8374-4477-9b0e-c56d4a566be1";

    private BeaconEventListener beaconListener;

    private BeaconManager manager;

    public void onCreate() {
        super.onCreate();
        Gimbal.setApiKey(this, API_KEY);

        manager = new BeaconManager();
        beaconListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);

                Toast.makeText(getApplicationContext(), String.valueOf(beaconSighting.getBeacon().getUuid()), Toast.LENGTH_SHORT).show();
            }
        };
        manager.addListener(beaconListener);
        manager.startListening();
    }

    public void onTerminate() {
        super.onTerminate();
        manager.removeListener(beaconListener);
        manager.stopListening();
    }
}
