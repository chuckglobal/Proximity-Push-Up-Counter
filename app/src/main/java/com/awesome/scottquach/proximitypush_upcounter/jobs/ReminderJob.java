package com.awesome.scottquach.proximitypush_upcounter.jobs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.awesome.scottquach.proximitypush_upcounter.Constants;
import com.awesome.scottquach.proximitypush_upcounter.R;
import com.awesome.scottquach.proximitypush_upcounter.activities.TrackerActivity;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Calendar;

import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Scott Quach on 11/23/2017.
 */

public class ReminderJob extends Job {

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        //PendingIntent to open app when notification is clicked
        Intent openApp = new Intent(getContext(), TrackerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 10 , openApp, PendingIntent.FLAG_CANCEL_CURRENT);
        //Retreive default notification sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Create notification
        Notification mBuilder = new Notification.Builder(getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getContext().getString(R.string.notification_title))
                .setContentText(getContext().getString(R.string.notification_description))
                .setContentIntent(pendingIntent)
                .setSound(sound)
                .setAutoCancel(true)
                .build();

        //Notify
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(101, mBuilder);

        return Result.SUCCESS;
    }

    public static int scheduleJob(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());

        if (calendar.before(currentTime)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        int jobId = new JobRequest.Builder(Constants.REMINDER_JOB)
                .setExact(calendar.getTimeInMillis() - currentTime.getTimeInMillis())
                .build()
                .schedule();
        return jobId;
    }

    public static void cancelJob() {
        JobManager.instance().cancelAllForTag(Constants.REMINDER_JOB);
    }
}
