package com.ndscompany.text.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ndscompany.text.R;
import com.ndscompany.text.app.App;
import com.ndscompany.text.classes.TestCase;

import java.util.List;


public class MyForegroundService extends Service {

    private static final int NOTIFICATION_TEST_CASE_ID = 1;

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_SUCCESS = "MyForegroundService.ACTION_PAUSE";

    public static final String ACTION_FAIL = "MyForegroundService.ACTION_FAIL";

    public static final String ACTION_BLOCK = "MyForegroundService.ACTION_BLOCK";

    public static final String ACTION_NEXT = "MyForegroundService.ACTION_NEXT";

    public static final String ACTION_PREV = "MyForegroundService.ACTION_PREV";

    public static final String VERSION_ID = "MyForegroundService.VERSION_ID";

    long versionId;
    private int casePosition =0;
    private List<TestCase> testCases;

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
                        versionId = intent.getExtras().getLong(VERSION_ID);
                        startForegroundService();
                        Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        stopForegroundService();
                        Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_SUCCESS:
                        nextTestCase();
                        Toast.makeText(getApplicationContext(), "You click ACTION_SUCCESS button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_FAIL:
                        nextTestCase();
                        Toast.makeText(getApplicationContext(), "You click ACTION_FAIL button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_BLOCK:
                        Toast.makeText(getApplicationContext(), "You click ACTION_BLOCK button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_NEXT:
                        nextTestCase();
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
        casePosition = 0;
//        Bundle bundle = new Bundle();
//        bundle.putpa
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        Log.d(TAG_FOREGROUND_SERVICE, String.valueOf(App.getInstanse() == null));
        testCases = App.getInstanse().getDatabase().testCaseDao().getTestCasesByVersionId(versionId);
        Log.d(TAG_FOREGROUND_SERVICE, "startForegroundService: " + testCases.size());
        // Create notification_test_case default intent.
        showTestCaseNotification(testCases.get(casePosition));
    }

    private void stopForegroundService()
    {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification_test_case.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    private void nextTestCase(){
        casePosition++;
        if(casePosition < testCases.size()) {
            showTestCaseNotification(testCases.get(casePosition));
        } else {
            showResultNotification();
        }
    }

    private void showTestCaseNotification(TestCase testCase){
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification_test_case builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        RemoteViews testCaseNotificationSmall = new RemoteViews(getPackageName(), R.layout.notification_test_case_small);

        Intent btnSuccessSmall = new Intent(this, MyForegroundService.class);
        btnSuccessSmall.setAction(ACTION_SUCCESS);
        PendingIntent btnSuccessPendingSmall = PendingIntent.getService(this, 0, btnSuccessSmall, 0);
        testCaseNotificationSmall.setOnClickPendingIntent(R.id.btn_notification_testcase_small_success,btnSuccessPendingSmall);

        Intent btnFailSmall = new Intent(this, MyForegroundService.class);
        btnFailSmall.setAction(ACTION_FAIL);
        PendingIntent btnFailPendingSmall = PendingIntent.getService(this, 0, btnFailSmall, 0);
        testCaseNotificationSmall.setOnClickPendingIntent(R.id.btn_notification_testcase_small_fail,btnFailPendingSmall);

        testCaseNotificationSmall.setTextViewText(R.id.tv_notification_testcase_small_title, testCase.getName());
        testCaseNotificationSmall.setTextViewText(R.id.tv_notification_testcase_small_description, testCase.getDescription());

        // Make notification_test_case show big text.
        RemoteViews testCasesNotificationLarge = new RemoteViews(getPackageName(), R.layout.notification_test_case);

        testCasesNotificationLarge.setTextViewText(R.id.tv_notification_title, testCase.getName());
        testCasesNotificationLarge.setTextViewText(R.id.tv_notification_message, testCase.getDescription());

        Intent btnPrev = new Intent(this, MyForegroundService.class);
        btnPrev.setAction(ACTION_PREV);
        PendingIntent btnPrevPending = PendingIntent.getService(this, 0, btnPrev, 0);
        testCasesNotificationLarge.setOnClickPendingIntent(R.id.btn_notification_prev,btnPrevPending);

        Intent btnSuccess = new Intent(this, MyForegroundService.class);
        btnSuccess.setAction(ACTION_SUCCESS);
        PendingIntent btnSuccessPending = PendingIntent.getService(this, 0, btnSuccess, 0);
        testCasesNotificationLarge.setOnClickPendingIntent(R.id.btn_notification_success,btnSuccessPending);

        Intent btnFail = new Intent(this, MyForegroundService.class);
        btnFail.setAction(ACTION_FAIL);
        PendingIntent btnFailPending = PendingIntent.getService(this, 0, btnFail, 0);
        testCasesNotificationLarge.setOnClickPendingIntent(R.id.btn_notification_fail,btnFailPending);

        Intent btnBlock = new Intent(this, MyForegroundService.class);
        btnBlock.setAction(ACTION_BLOCK);
        PendingIntent btnBlockPending = PendingIntent.getService(this, 0, btnBlock, 0);
        testCasesNotificationLarge.setOnClickPendingIntent(R.id.btn_notification_block,btnBlockPending);

        Intent btnNext = new Intent(this, MyForegroundService.class);
        btnNext.setAction(ACTION_NEXT);
        PendingIntent btnNextPending = PendingIntent.getService(this, 0, btnNext, 0);
        testCasesNotificationLarge.setOnClickPendingIntent(R.id.btn_notification_next,btnNextPending);

        builder.setCustomBigContentView(testCasesNotificationLarge);

        NotificationCompat.DecoratedCustomViewStyle customView = new NotificationCompat.DecoratedCustomViewStyle();
//        builder.setStyle(customView);
        builder.setCustomContentView(testCaseNotificationSmall);
        builder.setCustomBigContentView(testCasesNotificationLarge);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        builder.setFullScreenIntent(pendingIntent, true);
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(NOTIFICATION_TEST_CASE_ID, notification);
    }

    private void showResultNotification(){
        NotificationCompat.Builder resultNotificationBuilder = new NotificationCompat.Builder(this);
    }
}
