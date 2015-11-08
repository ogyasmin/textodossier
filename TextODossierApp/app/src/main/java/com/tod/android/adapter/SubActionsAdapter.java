package com.tod.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.tod.android.R;

import java.util.Arrays;
import java.util.List;

/**
 * Simons project - Human Equation - http://www.equationhumaine.co
 * Created by yorouguidou on 15-05-02.
 */
public class SubActionsAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    // Your custom values for the spinner (User)
    private List<String> mActions;
    private SubActionsAdapterListener mSelectionListener;
    private int mMasterId;

    public SubActionsAdapter(String[] values) {
        mActions = Arrays.asList(values);
    }

    public int getCount(){
        return mActions.size();
    }

    public String getItem(int position){
        return mActions.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    // View lookup cache
    private static class ViewHolder {
        CheckBox checkBox;
    }

    public void setSelectionListener(SubActionsAdapterListener listener) {
        this.mSelectionListener = listener;
    }

    public void setMasterId(int masterId) {
        this.mMasterId = masterId;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            final int index = position;
            viewHolder = new ViewHolder();
            mInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.sub_item_select, parent, false);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.title_label);
            convertView.setTag(viewHolder);

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSelectionListener.subActionSelect(mMasterId,index, isChecked);
                }
            });

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setText(mActions.get(position));

        // And finally return your dynamic (or custom) view for each spinner item
        return convertView;
    }
    public interface SubActionsAdapterListener{
        void subActionSelect(int masterId,int index, boolean checked);
    }
}