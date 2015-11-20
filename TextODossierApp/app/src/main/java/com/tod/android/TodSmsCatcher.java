package com.tod.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by yorouguidou on 15-11-07.
 */
public class TodSmsCatcher extends BroadcastReceiver {
    public static final String TAG = "TOD_SMS_CATCHER";
    private final String   ACTION_RECEIVE_SMS  = "android.provider.Telephony.SMS_RECEIVED";
    public static final String   TOD_PATTERN  = "TOD:";
    public static final String   TOD_MESSAGE  = "TOD_MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG,"SMS RECEIVED");
        if (intent.getAction().equals(ACTION_RECEIVE_SMS)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    final String mess = messages[0].getDisplayMessageBody();
                    final String phoneNumber = messages[0].getDisplayOriginatingAddress();
                    Log.v(TAG,"Message Received:" + mess);
                    if (mess.startsWith(TOD_PATTERN)) {
                        handleTodSms(mess.substring(TOD_PATTERN.length()),context);
                    }
                }
            }
        }
    }

    private void handleTodSms(String todsms,Context context){
        Log.v(TAG, "SMS RECEIVED-- This SMS is for TOD");
        Intent intent ;
        if(TodGlobals.isDoctor(context))
            intent = new Intent(context, DoctorActivity.class);
        else
            intent = new Intent(context, NurseActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle mBundle = new Bundle();
        mBundle.putString(TOD_MESSAGE, todsms);
        intent.putExtras(mBundle);
        context.startActivity(intent);
    }
}
