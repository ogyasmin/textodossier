package com.tod.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TodSmsActivity extends AppCompatActivity {
    @Bind(R.id.hello_msg)
    protected TextView mTvHelloMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        // Bind ui with ButterKnife
        ButterKnife.bind(this);
        // Check if there are some extras
        if(getIntent().getExtras()!=null){
            handleExtras(getIntent().getExtras());
        }
    }
    private void handleExtras(Bundle receivedExtras){
        String todMessage = receivedExtras.getString(TodSmsCatcher.TOD_MESSAGE);
        if(todMessage!=null){
            mTvHelloMsg.setText(todMessage);
        }
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
