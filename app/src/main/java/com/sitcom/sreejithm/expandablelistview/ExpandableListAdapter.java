package com.sitcom.sreejithm.expandablelistview;

/**
 * Created by SreejithM on 2/12/2016.
 * This class uses BaseExpandableListAdapter for creating custom adapter for ExpandableListView.
 */
import com.sitcom.sreejithm.saltnpepper.R;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> saltnpepperlistCollections;
    private List<String> saltnpepperlists;
    public EditText selectOpt;
    public String optionSelected;
    public ExpandableListAdapter(Activity context, List<String> saltnpepperlists,
                                 Map<String, List<String>> saltnpepperlistCollections) {
        this.context = context;
        this.saltnpepperlistCollections = saltnpepperlistCollections;
        this.saltnpepperlists = saltnpepperlists;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return saltnpepperlistCollections.get(saltnpepperlists.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public String getGroupName(int groupPosition){
        return saltnpepperlists.get(groupPosition);
    }
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String saltnpepperList = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.saltnpepperlist);
        /*
        //ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        item.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> child =
                                        laptopCollections.get(laptops.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                if(v.getId()==R.id.saltnpepperlist)
                {
                    selectOpt = (EditText) v;
                    optionSelected = selectOpt.getText().toString(); // getting the string from the selected option.
                }

            }
        });
        */
        item.setText(saltnpepperList);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return saltnpepperlistCollections.get(saltnpepperlists.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return saltnpepperlists.get(groupPosition);
    }

    public int getGroupCount() {
        return saltnpepperlists.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String saltnpepperlistName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.saltnpepperlist);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(saltnpepperlistName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

