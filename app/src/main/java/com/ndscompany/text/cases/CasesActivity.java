package com.ndscompany.text.cases;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;

import com.ndscompany.text.R;
import com.ndscompany.text.Service.MyForegroundService;
import com.ndscompany.text.app.App;
import com.ndscompany.text.cases.adddialog.AddTestCaseDialog;
import com.ndscompany.text.classes.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CasesActivity extends AppCompatActivity implements CasesAdapter.TestCaseListener {
    public static final String VERSION_ID = "CasesActivity.VERSION_ID";
    public static final String CHANNEL_ID = "TEST_LINE_CHANNEL_ID";

    private long versionId;
    private RecyclerView recyclerView;
    private List<TestCase> testCases;
    private CasesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases_activity);
        getExtras();
        setTitle("Версия "+String.valueOf(versionId));
        recyclerView = findViewById(R.id.rv_cases_test_cases);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CasesAdapter();
        testCases = App.getInstanse().getDatabase().testCaseDao().getTestCasesByVersionId(versionId);
        adapter.setTestCases(testCases);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        Intent intent = new Intent(this, MyForegroundService.class);
        intent.setAction(MyForegroundService.ACTION_START_FOREGROUND_SERVICE);
        Bundle bundle = new Bundle();
        bundle.putLong(MyForegroundService.VERSION_ID, versionId);
        intent.putExtras(bundle);
        Log.d("FOREGROUND_SERVICE", "onCreate: start service");
//        startService(intent);
    }

    private void getExtras(){
        if(getIntent().getExtras()!=null){
            versionId = getIntent().getExtras().getLong(VERSION_ID);
        }
    }

    @Override
    public void save(TestCase testCase) {
        App.getInstanse().getDatabase().testCaseDao().update(testCase);
    }

    @Override
    public void create() {
        AddTestCaseDialog dialog = new AddTestCaseDialog();
        dialog.setAddTestCaseDialogInterface(new AddTestCaseDialog.AddTestCaseDialogInterface() {
            @Override
            public void saveName(TestCase testCase) {
                testCase.setVersionId(versionId);
                App.getInstanse().getDatabase().testCaseDao().insert(testCase);
                testCases = App.getInstanse().getDatabase().testCaseDao().getTestCasesByVersionId(versionId);
                adapter.setTestCases(testCases);
            }
        });
        dialog.show(getSupportFragmentManager(), AddTestCaseDialog.TAG);
    }
}
