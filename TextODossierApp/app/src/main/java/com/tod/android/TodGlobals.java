package com.tod.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Simons project - Human Equation - http://www.equationhumaine.co
 * Created by yorouguidou on 15-11-07.
 */
public class TodGlobals {
    public static final String PREF_PATIENT_PATTERN="com.tod.prefs.patient.";
    public static final String PREF_TOD_DATA="com.tod.prefs";
    public static final String PREF_NAME_TAG="com.tod.prefs.name";
    public static final String PREF_TITLE_TAG="com.tod.prefs.title";
    public static final String TEXT_O_DOSSIER_NUMBER="+17786546006";//"+15148123755";

    public static final String NURSE="Nr.";
    public static final String DOCTOR="Dr.";
    public static final String DOC_USER_NAME="Dr. Brais";
    public static final String NURSE_USER_NAME="Brigitte";

    public static void saveUserIds(Context context, String namePreference,String titlePreference){
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(PREF_TOD_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_NAME_TAG, namePreference);
        editor.putString(PREF_TITLE_TAG, titlePreference);
        editor.commit();
    }
    public static void saveData(Context context, String tag,String value){
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(PREF_TOD_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(tag, value);
        editor.commit();
    }
    public static String readPreferences(Context context,String tag){
        SharedPreferences settings = context.getSharedPreferences(PREF_TOD_DATA, 0);
        return settings.getString(tag,"");
    }
    public static boolean isDoctor(Context context){
        return getUserTitle(context).equals(DOCTOR);
    }
    public static void initDoctorPreferences(Context context){
        saveUserIds(context, DOC_USER_NAME, DOCTOR);
    }
    public static void initNursePreferences(Context context){
        saveUserIds(context, NURSE_USER_NAME, NURSE);
    }
    public static String getUserTitle(Context context){
        return readPreferences(context,PREF_TITLE_TAG);
    }
    public static String getUserName(Context context){
        return readPreferences(context,PREF_NAME_TAG);
    }

    public static void sendSMS(Context context, String msg){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(TEXT_O_DOSSIER_NUMBER, null, msg, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
