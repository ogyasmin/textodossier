package com.tod.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NurseActivity extends AppCompatActivity{


    private static final String TAG = "NurseActivity" ;
    @Bind(R.id.user_name) protected TextView mUsername;
    @Bind(R.id.ll_row_1) protected LinearLayout llRow1;
    @Bind(R.id.ll_row_2) protected LinearLayout llRow2;
    @Bind(R.id.ll_row_3) protected LinearLayout llRow3;

    @Bind(R.id.ll_traitements) protected LinearLayout llTraitement;
    @Bind(R.id.ll_traitements_else) protected LinearLayout llTraitementElse;

    @Bind(R.id.label_row_1) protected TextView labelRow1;
    @Bind(R.id.label_row_2) protected TextView labelRow2;
    @Bind(R.id.label_row_3) protected TextView labelRow3;


    @Bind(R.id.ed_row_1) protected EditText mEditRow1;
    @Bind(R.id.ed_row_2) protected EditText mEditRow2;
    @Bind(R.id.ed_row_3) protected EditText mEditRow3;

    @Bind(R.id.confirm) protected Button btConfirm;

    private String mPatientId;
    private String mCodePostal;
    private String[] mActions;
    private ArrayList<NurseOperation> mNurseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);
        ButterKnife.bind(this);

        if(getIntent().getExtras()!=null){
            String msg = getIntent().getExtras().getString(TodSmsCatcher.TOD_MESSAGE);
            if(msg!=null){
                doctorDirectiveReceived(msg);
            }else{
                String patient_data = getIntent().getExtras().getString(MainActivity.PATIENT_ID);
                String[] data = patient_data.split(",");
                mPatientId = data[1];
                if(mPatientId !=null){
                    String savetag = TodGlobals.PREF_PATIENT_PATTERN + mPatientId;
                    msg = TodGlobals.readPreferences(this,savetag);
                    if(msg.length()>0)
                        doctorDirectiveReceived(msg);
                }
            }
        }

        String username = TodGlobals.getUserName(this);
        if(username== null){
            TodGlobals.initNursePreferences(this);
        }
        mUsername.setText(TodGlobals.getUserName(this));
    }

    private void doctorDirectiveReceived(String msg){

        mActions = msg.split(",");
        mCodePostal = mActions[0];
        mPatientId = mActions[1];
        String savetag = TodGlobals.PREF_PATIENT_PATTERN + mPatientId;
        TodGlobals.saveData(this,savetag,msg);
        mNurseOperations = new ArrayList<>();
        for (int i = 2; i < mActions.length ; i++) {
            String[] nurseInt = mActions[i].split("-");
            if(nurseInt.length>=2){
                mNurseOperations.add(new NurseOperation(nurseInt[1], DoctorActivity.getDictionnary().get(nurseInt[1])));
            }
        }

        if(mNurseOperations.size()>0) {
            llTraitement.setVisibility(View.VISIBLE);
            llTraitementElse.setVisibility(View.GONE);
        }
        else{
            llTraitement.setVisibility(View.GONE);
            llTraitementElse.setVisibility(View.VISIBLE);
        }

        llRow1.setVisibility(View.GONE);
        llRow2.setVisibility(View.GONE);
        llRow3.setVisibility(View.GONE);

        if(mNurseOperations.size()>0){
            labelRow1.setText(mNurseOperations.get(0).getOperation());
            llRow1.setVisibility(View.VISIBLE);
        }
        if(mNurseOperations.size()>1){
            labelRow2.setText(mNurseOperations.get(1).getOperation());
            llRow2.setVisibility(View.VISIBLE);
        }
        if(mNurseOperations.size()>2){
            labelRow3.setText(mNurseOperations.get(2).getOperation());
            llRow3.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.confirm)
    protected void onBtConfirmClicked(){
        int index = 0;
        String msg="TOD:" + mCodePostal + "," + mPatientId ;
        if(labelRow1.getVisibility() == View.VISIBLE){
            msg = msg + "," + mNurseOperations.get(index++).getCode() + "=" + mEditRow1.getText().toString().replace(",",".");
        }
        if(labelRow2.getVisibility() == View.VISIBLE){
            msg = msg + "," + mNurseOperations.get(index++).getCode() + "=" + mEditRow1.getText().toString().replace(",", ".");
        }
        if(labelRow3.getVisibility() == View.VISIBLE){
            msg = msg + "," + mNurseOperations.get(index++).getCode() + "=" + mEditRow1.getText().toString().replace(",",".");
        }
        Log.v(TAG,msg);

        Intent intent  = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        TodGlobals.sendSMS(this, msg);
        finish();

    }

    class NurseOperation{
        private String code;
        private String operation;
        private String value;

        public NurseOperation(String code, String operation) {
            this.code = code;
            this.operation = operation;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}