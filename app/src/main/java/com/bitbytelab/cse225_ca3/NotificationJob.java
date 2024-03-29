package com.bitbytelab.cse225_ca3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class NotificationJob extends JobService {

    public static final String TAG = NotificationJob.class.getSimpleName();
    NotificationManager notificationManager = MainActivity.notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Job started");

        Toast.makeText(this,"Job Scheduler Started.",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Scheduled Pending Intent Notification");
        builder.setContentText("Scheduler Notification when the device is charging!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        notificationManager.notify(0,builder.build());

        jobFinished(params,false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
