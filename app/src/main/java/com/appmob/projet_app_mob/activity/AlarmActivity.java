package com.appmob.projet_app_mob.activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appmob.projet_app_mob.R;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pending_intent;
    private TimePicker alarmTimePicker;
    private TextView alarmTextView;
    private AlarmReceiver alarm;
    private final Calendar calendar = Calendar.getInstance();
    private Intent myIntent;


    AlarmActivity inst;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);

        alarmTextView = (TextView) findViewById(R.id.alarmText);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);

        Button start_alarm= (Button) findViewById(R.id.start_alarm);

        Button stop_alarm= (Button) findViewById(R.id.stop_alarm);

    }

    public void startAlarm(View v){
        calendar.add(Calendar.SECOND, 3);
        final int hour = alarmTimePicker.getHour();
        final int minute = alarmTimePicker.getMinute();

        Log.e("MyActivity", "In the receiver with " + hour + " and " + minute);
        setAlarmText("You clicked a " + hour + " and " + minute);
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

        myIntent.putExtra("help", "lancement classe AlarmReceiver");
        pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
        setAlarmText("Alarm set to " + hour + ":" + minute);
    }

    public void stopAlarm(View v){
        alarmManager.cancel(pending_intent);
        setAlarmText("Alarm canceled");
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }
}
