package com.example.matthew.judex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Matthew on 2/11/2016.
 */
public class Receiver extends BroadcastReceiver {

    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String message = "";
        String sender = "";

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i=0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender += msgs[i].getOriginatingAddress();
                message += msgs[i].getMessageBody();
                message += "\n";
            }
        }
    }
}