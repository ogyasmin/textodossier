package com.tod.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tod.android.adapter.ActionsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActionsActivity extends AppCompatActivity {

    @Bind(R.id.actions_list)
    protected ListView mActionsList;
    private ActionsAdapter mActionsAdapter;
    private static String[] DOCTOR_ACTIONS ={"Nouvelle ordonnance", "Signes vitaux","Evaluations"};
    private SelectionListener mSelectionListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        ButterKnife.bind(this);

        mActionsAdapter =  new ActionsAdapter(DOCTOR_ACTIONS);
        mActionsList.setAdapter(mActionsAdapter);
    }

    public void setSelectionListener(SelectionListener mSelectionListener) {
        this.mSelectionListener = mSelectionListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actions, menu);
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
    public interface SelectionListener{
        void select(int id);
    }
}
