package com.tod.android;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tod.android.adapter.ActionsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment {

    private static final String ARG_PARAM_ACTIONS = "com.tod.args.actions";
    private static String[] mActions;

    private ActionsAdapter mActionsAdapter;

    @Bind(R.id.actions_list) protected ListView mActionsList;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ActionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionsFragment newInstance(String[] actions) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM_ACTIONS, actions);
        fragment.setArguments(args);
        return fragment;
    }

    public ActionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActions = getArguments().getStringArray(ARG_PARAM_ACTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actions, container, false);
        ButterKnife.bind(this,view);

        mActionsAdapter =  new ActionsAdapter(mActions);
        mActionsAdapter.setSelectionListener(mListener);
        mActionsList.setAdapter(mActionsAdapter);

        // Inflate the layout for this fragment
        return view;



    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void select(int id);
    }

}
