package com.tod.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String PATIENT_ID  = "PATIENT_ID";
    public static final String SCHEME_SETTING  = "setting://";
    public static final String SCHEME_PATIENT  = "patient://";

    @Bind(R.id.hello_msg)
    protected TextView mTvHelloMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bind ui with ButterKnife
        ButterKnife.bind(this);
        System.out.print("Got this far!");
        //TodGlobals.initDoctorPreferences(this);
        scanQRCode2();
    }
    private void scanQRCode2(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                mTvHelloMsg.setText(contents);
                Log.v("Scan",contents);
                if(contents.startsWith(SCHEME_PATIENT)) routeWithPatientId(contents.substring(SCHEME_PATIENT.length()));
                if(contents.startsWith(SCHEME_SETTING)) {
                    routeSettingUserTitle(contents.substring(SCHEME_SETTING.length()));
                }
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                finish();
            }
        }

    }
    private void routeSettingUserTitle(String title){
        Log.v("Scan",title);
        String name="";
        String titleToSet="";
        if(title.equals(TodGlobals.NURSE)) {
            name = TodGlobals.NURSE_USER_NAME;
            titleToSet =TodGlobals.NURSE;
        }
        if(title.equals(TodGlobals.DOCTOR)) {
            name = TodGlobals.DOC_USER_NAME;
            titleToSet =TodGlobals.DOCTOR;
        }

        TodGlobals.saveUserIds(this,name,titleToSet);
        mTvHelloMsg.setText(String.format(" Setting:  %s as a %s ",name, titleToSet));
    }
    private void routeWithPatientId(String patient){
        Log.v("Scan",patient);

        Intent intent ;
        if(TodGlobals.getUserTitle(this).equals(TodGlobals.NURSE))
            intent = new Intent(this, NurseActivity.class);
        else
            intent = new Intent(this, DoctorActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle mBundle = new Bundle();
        mBundle.putString(PATIENT_ID, patient);
        intent.putExtras(mBundle);
        startActivity(intent);

        finish();

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
