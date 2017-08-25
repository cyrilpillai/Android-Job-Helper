package com.cyrilpillai.android_job_helper.jobs;

import android.support.annotation.NonNull;

import com.cyrilpillai.annotation.IsJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

@IsJob
public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here
        return Result.SUCCESS;
    }

    public static void scheduleJob() {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .build()
                .schedule();
    }
}