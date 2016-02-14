package com.example.matthew.judex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final public EditText urgentText = (EditText) findViewById(R.id.urgentText);
    final public EditText blacklistText = (EditText) findViewById(R.id.blacklistText);

    public static List<String> urgentTextList;
    public static List<String> getUrgentTextList()
    {
        return urgentTextList;
    }

    public static List<String> blacklistTextList;
    public static List<String> getBlacklistTextList()
    {
        return blacklistTextList;
    }

    final public CardView startTime = (CardView) findViewById(R.id.startTime);
    final public TextView startTimeText = (TextView) findViewById(R.id.startTimeText);

    final public CardView endTime = (CardView) findViewById(R.id.endTime);
    final public TextView endTimeText = (TextView) findViewById(R.id.endTimeText);

    public static int startTimeInt;
    public static int endTimeInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startTime.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog setStartTime;
                setStartTime = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeText.setText(selectedHour + ":" + selectedMinute);
                        startTimeInt = 100 * selectedHour + selectedMinute;
                    }
                }, hour, minute, true);
                setStartTime.setTitle("Select Start Time");
                setStartTime.show();
            }
        });

        endTime.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog setEndTime;
                setEndTime = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeText.setText(selectedHour + ":" + selectedMinute);
                        endTimeInt = 100 * selectedHour + selectedMinute;
                    }
                }, hour, minute, true);
                setEndTime.setTitle("Select End Time");
                setEndTime.show();
            }
        });

        urgentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    urgentTextList = new ArrayList<String>(Arrays.asList(s.toString().split(",")));
                } else {
                    urgentTextList.clear();}
            }
        });

        blacklistText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    blacklistTextList = new ArrayList<String>(Arrays.asList(s.toString().split(",")));
                } else {
                    blacklistTextList.clear();}
            }
        });
    }

    public void notify(String sender, String text)
    {
        Notification notification  = new Notification.Builder(this)
                .setContentTitle("New message from: " + sender)
                .setContentText(text)
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
