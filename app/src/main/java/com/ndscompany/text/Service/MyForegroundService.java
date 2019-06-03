package com.ndscompany.text.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ndscompany.text.R;


public class MyForegroundService extends Service {

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_SUCCESS = "MyForegroundService.ACTION_PAUSE";

    public static final String ACTION_FAIL = "MyForegroundService.ACTION_FAIL";

    public static final String ACTION_BLOCK = "MyForegroundService.ACTION_BLOCK";

    public static final String ACTION_NEXT = "MyForegroundService.ACTION_NEXT";

    public static final String ACTION_PREV = "MyForegroundService.ACTION_PREV";

    public static final String VERSION_ID = "MyForegroundService.VERSION_ID";

    public MyForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
        {
            Log.d(TAG_FOREGROUND_SERVICE, "onStartCommand: " + intent);
            String action = intent.getAction();
            if(action != null) {
                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        long versionId = intent.getExtras().getLong(VERSION_ID);
                        startForegroundService();
                        Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        stopForegroundService();
                        Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_SUCCESS:
                        Toast.makeText(getApplicationContext(), "You click ACTION_SUCCESS button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_FAIL:
                        Toast.makeText(getApplicationContext(), "You click ACTION_FAIL button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_BLOCK:
                        Toast.makeText(getApplicationContext(), "You click ACTION_BLOCK button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_NEXT:
                        Toast.makeText(getApplicationContext(), "You click ACTION_NEXT button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_PREV:
                        Toast.makeText(getApplicationContext(), "You click ACTION_PREV button.", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /* Used to build and start foreground service. */
    private void startForegroundService()
    {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Make notification show big text.
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification);
        Intent btnPrev = new Intent(this, MyForegroundService.class);
        btnPrev.setAction(ACTION_PREV);
        PendingIntent btnPrevPending = PendingIntent.getService(this, 0, btnPrev, 0);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_prev,btnPrevPending);

        Intent btnSuccess = new Intent(this, MyForegroundService.class);
        btnSuccess.setAction(ACTION_SUCCESS);
        PendingIntent btnSuccessPending = PendingIntent.getService(this, 0, btnSuccess, 0);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_success,btnSuccessPending);
        builder.setCustomContentView(notificationLayout);

        Intent btnFail = new Intent(this, MyForegroundService.class);
        btnFail.setAction(ACTION_FAIL);
        PendingIntent btnFailPending = PendingIntent.getService(this, 0, btnFail, 0);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_fail,btnFailPending);
        builder.setCustomContentView(notificationLayout);

        Intent btnBlock = new Intent(this, MyForegroundService.class);
        btnBlock.setAction(ACTION_BLOCK);
        PendingIntent btnBlockPending = PendingIntent.getService(this, 0, btnBlock, 0);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_block,btnBlockPending);
        builder.setCustomContentView(notificationLayout);

        Intent btnNext = new Intent(this, MyForegroundService.class);
        btnNext.setAction(ACTION_NEXT);
        PendingIntent btnNextPending = PendingIntent.getService(this, 0, btnNext, 0);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_next,btnNextPending);
        builder.setCustomContentView(notificationLayout);

        NotificationCompat.DecoratedCustomViewStyle customView = new NotificationCompat.DecoratedCustomViewStyle();
        builder.setStyle(customView);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
//        builder.setLargeIcon(largeIconBitmap);
        // Make the notification max priority.
        builder.setPriority(Notification.PRIORITY_MAX);
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true);

        // Add Play button intent in notification.


        // Add Pause button intent in notification.
//        Intent pauseIntent = new Intent(this, MyForegroundService.class);
//        pauseIntent.setAction(ACTION_PAUSE);
//        PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
//        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent);
//        builder.addAction(prevAction);

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);
    }

    private void stopForegroundService()
    {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }
}
