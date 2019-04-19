package com.bitbytelab.cse225_ca3;

import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static NotificationManager notificationManager = null;

    JobScheduler jobScheduler;
    JobInfo jobInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        findViewById(R.id.btnCreateNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJobNotification();
            }
        });
        findViewById(R.id.btnCancelNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopJob();
            }
        });

    }
    public void startJobNotification(){
        Log.d(TAG,"startJobNotification called");
        jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this,NotificationJob.class);

        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(0,componentName);

        jobInfoBuilder.setRequiresCharging(true);
        jobInfoBuilder.setRequiresDeviceIdle(false);

        jobInfo = jobInfoBuilder.build();
        jobScheduler.schedule(jobInfo);

        Toast.makeText(this,"Job Scheduled.",Toast.LENGTH_SHORT).show();
    }
    public void stopJob(){
        if(jobScheduler!=null){
            jobScheduler.cancelAll();
            jobScheduler = null;
            Toast.makeText(this,"Scheduled job cancelled.",Toast.LENGTH_SHORT).show();
        }
        notificationManager.cancelAll();
    }

}
