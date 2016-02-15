package com.sitcom.sreejithm.saltnpepper;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.sitcom.sreejithm.expandablelistview.ExpandableListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChooseDish extends AppCompatActivity{

    ExpandableListAdapter adapter;
    private int lastExpandedGroup = -1;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> optionCollection;
    ExpandableListView expListView;
    Map<String,String> selectedOptionDict;
    String selectedgrp = "";//setting the default selected value
    TextView waiterAssigned,totalguest,tableNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dish);


        TextView logingUserName = (TextView) findViewById(R.id.servername);
        totalguest = (TextView) findViewById(R.id.total_guest);
        tableNumber = (TextView) findViewById(R.id.table_number);
        waiterAssigned = (TextView) findViewById(R.id.waiter_assigned);

        Intent intent = getIntent();
        String name = "Welcome ," + intent.getStringExtra("login");
        logingUserName.setText(name);
        SetTextViewValues("");//setting the default values
        createGroupList();
        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, optionCollection);
        expListView.setAdapter(expListAdapter);

        selectedOptionDict = new LinkedHashMap<String,String>();
        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                //Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                //.        .show();
                selectedgrp = groupList.get(groupPosition);
                selectedOptionDict.put(selectedgrp, selected);
                SetTextViewValues(selected);
                return true;
            }

        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //collapsing the last expanded group
                if(lastExpandedGroup != -1 && groupPosition != lastExpandedGroup){
                    expListView.collapseGroup(lastExpandedGroup);
                }
                lastExpandedGroup = groupPosition;
                //Toast.makeText(getApplicationContext(), groupList.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(),groupList.get(groupPosition)+ " Collapsed",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SetTextViewValues(String selected) {
        switch(selectedgrp)
        {
            case "Total Guest":
                totalguest.setText( selectedgrp + " : " + selected );
                break;
            case "Table Number":
                tableNumber.setText( selectedgrp + " : " + selected );
                break;
            case "Waiter Assigned":
                waiterAssigned.setText( selectedgrp + " : " + selected );
                break;
            default:
                totalguest.setText("Total Guest : 0");
                tableNumber.setText("Table Number : Not decided");
                waiterAssigned.setText("Waiter Assigned : Not decided");
                break;
        }
    }


    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Total Guest");
        groupList.add("Table Number");
        groupList.add("Waiter Assigned");
        /*
        * commenting the below list for future modifications
        groupList.add("");
        groupList.add("");
        groupList.add("");
        */
    }

    private void createCollection() {
        // preparing options collection(child)
        String[] totalGuest = {"1","2","3","4","5","6","7","8"}; //this portion will be modified to take values from a text box
        String[] tableNumber = { "A","B","C","D","E","F","G","H" }; // this portion will be modified in future releases.
        String[] waiterAssigned = { "Ramesh","Suresh","Clint","Pranav","Avani","Anju" };
        /*
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };
        */

        optionCollection = new LinkedHashMap<String, List<String>>();

        for (String option : groupList) {
            if (option.equals("Total Guest")) {
                loadChild(totalGuest);
            }
            else if (option.equals("Table Number")) {
                loadChild(tableNumber);
            }
            else //(option.equals("Waiter Assigned"))
            {
                loadChild(waiterAssigned);
            }
            /*
            else if (option.equals("HCL"))
                loadChild(hclModels);
            else if (option.equals("Samsung"))
                loadChild(samsungModels);
            else
                loadChild(lenovoModels);
            */
            optionCollection.put(option, childList);
        }
    }

    private void loadChild(String[] valueList) {
        childList = new ArrayList<String>();
        for (String model : valueList)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void takeorder(View view){
        Intent intent = new Intent(this, TakeOrder.class);
        intent.putExtra("total_guest",totalguest.getText().toString());
        intent.putExtra("table_number",tableNumber.getText().toString());
        intent.putExtra("waiter_assigned", waiterAssigned.getText().toString());
        startActivity(intent);
    }

}
