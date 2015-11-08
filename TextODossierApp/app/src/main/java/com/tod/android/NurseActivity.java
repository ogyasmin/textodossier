package com.tod.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NurseActivity extends AppCompatActivity{


    @Bind(R.id.user_name) protected TextView mUsername;
    @Bind(R.id.ll_row_1) protected LinearLayout llRow1;
    @Bind(R.id.ll_row_2) protected LinearLayout llRow2;
    @Bind(R.id.ll_row_3) protected LinearLayout llRow3;
    @Bind(R.id.label_row_1) protected TextView labelRow1;
    @Bind(R.id.label_row_2) protected TextView labelRow2;
    @Bind(R.id.label_row_3) protected TextView labelRow3;
    private static String[] DOCTOR_ACTIONS ={"Nouvelle ordonnance", "Signes vitaux","Evaluations"};
    private static String[] DOCTOR_ACTIONS_CODE ={"NO", "SV","EV"};
    private static String[] ORDONNANCE_SUB_ACTIONS ={"Antihypertenseur", "Per OS"};
    private static String[] ORDONNANCE_SUB_ACTIONS_CODE ={"ATHT", "PO"};
    private static String[] SV_SUB_ACTION ={"Pression Artérielle", "Pouls","Température","Saturations"};
    private static String[] SV_SUB_ACTION_CODE ={"PA", "PL","TP","SAO2"};
    private static String[] EVA_SUB_ACTION ={"Douleurs"};
    private static String[] EVA_SUB_ACTION_CODE ={"DL"};
    private String mPatientId;
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
                mActions = msg.split(",");
                mPatientId = mActions[1];
                mNurseOperations = new ArrayList<>();
                for (int i = 2; i < mActions.length ; i++) {
                    String[] nurseInt = mActions[i].split("-");
                    if(nurseInt.length>=2){
                        if(nurseInt[i].equals(DoctorActivity.DOCTOR_ACTIONS_CODE[0])){
                            mNurseOperations.add(new NurseOperation(DoctorActivity.ORDONNANCE_SUB_ACTIONS_CODE[0],DoctorActivity.ORDONNANCE_SUB_ACTIONS[0]));
                        }
                        if(nurseInt[i].equals(DoctorActivity.DOCTOR_ACTIONS_CODE[1])){
                            mNurseOperations.add(new NurseOperation(DoctorActivity.SV_SUB_ACTION_CODE[1],DoctorActivity.SV_SUB_ACTION[1]));
                        }
                        if(nurseInt[i].equals(DoctorActivity.DOCTOR_ACTIONS_CODE[2])){
                            mNurseOperations.add(new NurseOperation(DoctorActivity.EVA_SUB_ACTION_CODE[2],DoctorActivity.EVA_SUB_ACTION[2]));
                        }
                    }
                }

                llRow1.setVisibility(View.GONE);
                llRow2.setVisibility(View.GONE);
                llRow3.setVisibility(View.GONE);

                if(mNurseOperations.size()>=0){
                    labelRow1.setText(mNurseOperations.get(0).getOperation());
                    llRow1.setVisibility(View.VISIBLE);
                }
                if(mNurseOperations.size()>=1){
                    labelRow2.setText(mNurseOperations.get(1).getOperation());
                    llRow2.setVisibility(View.VISIBLE);
                }
                if(mNurseOperations.size()>=2){
                    labelRow3.setText(mNurseOperations.get(2).getOperation());
                    llRow3.setVisibility(View.VISIBLE);
                }
            }
        }

        String username = TodGlobals.getUserName(this);
        if(username== null){
            TodGlobals.initPreferences(this);
        }
        mUsername.setText(TodGlobals.getUserName(this));
    }
    class NurseOperation{
        private String code;
        private String operation;
        private String value;

        public NurseOperation() {
        }

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