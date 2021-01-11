package com.tdc.edu.vn.heathcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import com.tdc.edu.vn.heathcareapp.intro.MainIntroActivity;
import com.tdc.edu.vn.heathcareapp.utils.CustomExceptionHandler;

public class MainActivity extends AppCompatActivity {
    public static String WEIGHT = "Weight";
    public static String BODYTRACKING = "BodyTracking";
    public static String BODYTRACKINGDETAILS = "BodyTrackingDetail";
    public static String ABOUT = "About";
    public static String SETTINGS = "Settings";
    public static String MACHINES = "Machines";
    public static String MACHINESDETAILS = "MachinesDetails";
    public static String WORKOUTS = "Workouts";
    public static String WORKOUTPAGER = "WorkoutPager";
    public static String PREFS_NAME = "prefsfile";
    private final int REQUEST_CODE_INTRO = 111;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1001;
    private static final String SP = "SP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySharedPrefences sharedPrefences= new MySharedPrefences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPrefences.getBooleanValue(SP)){
                    StartActivity(LoginActivity.class);
                }
                else {
                    StartActivity(MainIntroActivity.class);
                    sharedPrefences.putBooleanValue(SP, true);
                }
            }
        },2000);
    }
    private void StartActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
