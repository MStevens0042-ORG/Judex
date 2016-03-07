package com.example.matthew.judex;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Matthew on 2/11/2016.
 */
public class Receiver extends BroadcastReceiver {

    public Receiver() {
    }

    boolean isUrgent = false;
    boolean isBlacklisted = false;
    boolean isAutoreply = false;

    Calendar currentTime = Calendar.getInstance();
    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
    int minute = currentTime.get(Calendar.MINUTE);

    int nowTime = 100 * hour + minute;

    boolean quietHours = false;

    public List<String> messageList;

    @Override
    public void onReceive(Context context, Intent intent) {

        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        context.registerReceiver(this, filter);

        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String message = "";
        String sender = "";

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender += msgs[i].getOriginatingAddress();
                message += msgs[i].getMessageBody();
            }
        }

        messageList = new ArrayList<>(Arrays.asList(message.split(" ")));

        for(int i = 0; i < MainActivity.getUrgentTextList().size(); i++)
        {
            for(int j = 0; j < messageList.size(); j++)
            {
                if(MainActivity.getUrgentTextList().get(i).equals(messageList.get(j)))
                {
                    isUrgent = true;
                }
            }
        }

        for(int i = 0; i < MainActivity.getBlacklistTextList().size(); i++)
        {
            for(int j = 0; j < messageList.size(); j++)
            {
                if(MainActivity.getBlacklistTextList().get(i).equals(messageList.get(j)) && !isUrgent)
                {
                    isBlacklisted = true;
                }
            }
        }

        for(int i = 0; i < MainActivity.getAutoreplyTextList().size(); i++)
        {
            for(int j = 0; j < messageList.size(); j++)
            {
                if(MainActivity.getAutoreplyTextList().get(i).equals(messageList.get(j)))
                {
                    isAutoreply = true;
                }
            }
        }

        for(int i = 0; i < MainActivity.getUrgentContactList().size(); i++)
        {
                if(MainActivity.getUrgentContactList().get(i).equals(sender))
                {
                    isUrgent = true;
                }
        }

        for(int i = 0; i < MainActivity.getBlacklistContactList().size(); i++)
        {
                if(MainActivity.getBlacklistContactList().get(i).equals(sender) && !isUrgent)
                {
                    isBlacklisted = true;
                }
        }

        for(int i = 0; i < MainActivity.getAutoreplyContactList().size(); i++)
        {
                if(MainActivity.getAutoreplyContactList().get(i).equals(sender))
                {
                    isAutoreply = true;
                }
        }

        if(MainActivity.endTimeInt < MainActivity.startTimeInt)
        {
            if(nowTime > MainActivity.startTimeInt || nowTime < MainActivity.endTimeInt)
            {
                quietHours = true;
            }
        } else if(nowTime < MainActivity.endTimeInt && nowTime > MainActivity.startTimeInt)
        {
            quietHours = true;
        }

        MainActivity main = new MainActivity();

        if(isUrgent)
        {
            main.notify(sender, message);
        } else if(!quietHours && !isBlacklisted)
        {
            main.notify(sender, message);
        }

        if(isAutoreply)
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sender, null, MainActivity.response, null, null);
        }

    }


}