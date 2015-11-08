package com.tod.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tod.android.adapter.SubActionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorActivity extends AppCompatActivity implements SubActionsAdapter.SubActionsAdapterListener {

    @Bind(R.id.tabs) protected TabLayout tabLayout;
    @Bind(R.id.viewpager) protected ViewPager viewPager;
    @Bind(R.id.user_name) protected TextView mUsername;

    public static String[] DOCTOR_ACTIONS ={"Nouvelle ordonnance", "Signes vitaux","Evaluations"};
    public static String[] DOCTOR_ACTIONS_CODE ={"NO", "SV","EV"};
    public static String[] ORDONNANCE_SUB_ACTIONS ={"Antihypertenseur", "Per OS"};
    public static String[] ORDONNANCE_SUB_ACTIONS_CODE ={"ATHT", "PO"};
    public static String[] SV_SUB_ACTION ={"Pression Artérielle", "Pouls","Température","Saturations"};
    public static String[] SV_SUB_ACTION_CODE ={"PA", "PL","TP","SAO2"};
    public static String[] EVA_SUB_ACTION ={"Douleurs"};
    public static String[] EVA_SUB_ACTION_CODE ={"DL"};
    private HashMap<String,String> mSelections;
    private String mPatientId;
    public static HashMap<String,String> getDictionnary(){
         HashMap<String,String> dict = new HashMap<>();

        dict.put(ORDONNANCE_SUB_ACTIONS_CODE[0],ORDONNANCE_SUB_ACTIONS[0]);
        dict.put(ORDONNANCE_SUB_ACTIONS_CODE[1],ORDONNANCE_SUB_ACTIONS[1]);
        dict.put(SV_SUB_ACTION_CODE[0],SV_SUB_ACTION[0]);
        dict.put(SV_SUB_ACTION_CODE[1],SV_SUB_ACTION[1]);
        dict.put(SV_SUB_ACTION_CODE[2],SV_SUB_ACTION[2]);
        dict.put(EVA_SUB_ACTION_CODE[0],EVA_SUB_ACTION[0]);

        return dict;
    }
    public static int getDoctorActionInde(String code){

        return Arrays.asList(DOCTOR_ACTIONS).indexOf(code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ButterKnife.bind(this);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        if(getIntent().getExtras()!=null){
            String patientId = getIntent().getExtras().getString(MainActivity.PATIENT_ID);
            if(patientId!=null){
                mPatientId = patientId;
            }
        }

        String username = TodGlobals.getUserName(this);
        mSelections = new HashMap<>();

        if(username== null){
            TodGlobals.initDoctorPreferences(this);
        }
        mUsername.setText(TodGlobals.getUserName(this));
    }

    private void setupViewPager(ViewPager viewPager) {

        DoctorActionsFragment mFragNouvOrdonnance = DoctorActionsFragment.newInstance(ORDONNANCE_SUB_ACTIONS,0);
        DoctorActionsFragment mFragSignesVitaux = DoctorActionsFragment.newInstance(SV_SUB_ACTION,1);
        DoctorActionsFragment mFragEvaluations = DoctorActionsFragment.newInstance(EVA_SUB_ACTION,2);

        mFragNouvOrdonnance.setSubActionsAdapterListener(this);
        mFragSignesVitaux.setSubActionsAdapterListener(this);
        mFragEvaluations.setSubActionsAdapterListener(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(mFragNouvOrdonnance, DOCTOR_ACTIONS[0], 0);
        adapter.addFragment(mFragSignesVitaux, DOCTOR_ACTIONS[1], 1);
        adapter.addFragment(mFragEvaluations, DOCTOR_ACTIONS[2],2);
        viewPager.setAdapter(adapter);
    }
    @OnClick(R.id.confirm)
    protected void onButtonClicked(){
        String completeMessage = mPatientId;
        for (String item:mSelections.values()) {
            completeMessage = completeMessage + "," + item;
        }
        Log.v("TAG", "completeMessage = " + completeMessage);

        TodGlobals.sendSMS(this,completeMessage);

        Intent intent  = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finish();
    }
    @Override
    public void subActionSelect(int masterId,int index, boolean checked) {
        String msg =DOCTOR_ACTIONS_CODE[masterId] ;
        String key =masterId + "-" + index;
        Log.v("TAG", "masterId = " + masterId);

        if(checked){
            switch (masterId){
                case 0:
                    msg =  msg + "-" + ORDONNANCE_SUB_ACTIONS_CODE[index];
                    break;
                case 1:
                    msg =  msg + "-" + SV_SUB_ACTION_CODE[index];
                    break;

                case 2:
                    msg =  msg + "-" + EVA_SUB_ACTION_CODE[index];
                    break;

            }
            mSelections.put(key,msg + "-die");
            Log.v("TAG", "Key = " + key + " Msg = " + msg);
        }
        else{
            mSelections.remove(key);
            Log.v("TAG", "remove Key = " + key);

        }
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private final SparseArrayCompat<Fragment> mFragmentList = new SparseArrayCompat<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title,int position) {
            mFragmentList.put(position,fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}