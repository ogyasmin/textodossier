package com.tod.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tod.android.adapter.SubActionsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DoctorActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorActionsFragment extends Fragment {

    private static final String ARG_PARAM_ACTIONS = "com.tod.args.actions";
    private static final String ARG_PARAM_MASTER_ID = "com.tod.args.master_id";
    private static String[] mActions;
    private int mMasterId;
    private SubActionsAdapter mSubActionsAdapter;
    private SubActionsAdapter.SubActionsAdapterListener mSubActionsAdapterListener;
    @Bind(R.id.actions_list) protected ListView mActionsList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ActionsFragment.
     */
    public static DoctorActionsFragment newInstance(String[] actions, int masterid) {
        DoctorActionsFragment fragment = new DoctorActionsFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM_ACTIONS, actions);
        args.putInt(ARG_PARAM_MASTER_ID,masterid);
        fragment.setArguments(args);
        return fragment;
    }

    public DoctorActionsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActions = getArguments().getStringArray(ARG_PARAM_ACTIONS);
            mMasterId = getArguments().getInt(ARG_PARAM_MASTER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sub_actions, container, false);
        ButterKnife.bind(this, view);
        mSubActionsAdapter =  new SubActionsAdapter(mActions);
        mSubActionsAdapter.setSelectionListener(mSubActionsAdapterListener);
        mSubActionsAdapter.setMasterId(mMasterId);
        mActionsList.setAdapter(mSubActionsAdapter);
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void setSubActionsAdapterListener(SubActionsAdapter.SubActionsAdapterListener listener) {
        this.mSubActionsAdapterListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
