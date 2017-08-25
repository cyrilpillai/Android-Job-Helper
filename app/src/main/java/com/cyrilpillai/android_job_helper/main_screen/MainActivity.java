package com.cyrilpillai.android_job_helper.main_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyrilpillai.android_job_helper.R;
import com.cyrilpillai.android_job_helper.jobs.DemoSyncJob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DemoSyncJob.scheduleJob();
    }
}
