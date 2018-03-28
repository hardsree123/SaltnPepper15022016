package com.sitcom.sreejithm.saltnpepper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sitcom.sreejithm.expandablelistview.ExpandableListAdapter;
import com.sitcom.sreejithm.helper.SQLiteHandler;


public class TakeOrder extends RestaurantMenu {
    ExpandableListAdapter adapter;
    private int lastExpandedGroup = -1;
    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;
    ArrayAdapter listViewAdapter;
    ListView listView;

    SQLiteHandler orderDataDb;

    Button btnConfirmOrder;
    private
    String SelectedGrp = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        orderDataDb = new SQLiteHandler(getApplicationContext());

        final Intent intent = getIntent();
        final String totalGuest = intent.getStringExtra("total_guest");
        final String tableNumber = intent.getStringExtra("table_number");
        final String waiterName = intent.getStringExtra("waiter_assigned");
        final String serverName = intent.getStringExtra("server_name");

        expListView = (ExpandableListView) findViewById(R.id.chooseDish);

        listView = (ListView) findViewById(R.id.selectedCuisines);

        btnConfirmOrder = (Button) findViewById(R.id.confirmOrder);
        /*
        createGroupList();
        createCollection();

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, optionCollection);
        expListView.setAdapter(expListAdapter);
        */
        //selectedOptionDict = new LinkedHashMap<String,String>();
        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                //Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                //.        .show();
                //selectedgrp = groupList.get(groupPosition);
                //selectedOptionDict.put(selectedgrp, selected);
                //SetTextViewValues(selected);
                SetTextViewValues(selected);
                SetListView();
                return true;
            }

        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //collapsing the last expanded group
                if (lastExpandedGroup != -1 && groupPosition != lastExpandedGroup) {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) listView.getItemAtPosition(position);
                RemoveTextViewValues(selected); // removing the selected text item.
                SetListView(); // resetting the list once the selected item is removed.
            }
        });

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (selectedCuisines.size() > 0) {
                        long orderId = orderDataDb.addOrder(serverName, waiterName, tableNumber, totalGuest);

                        if (orderId > 0) {
                            Toast.makeText(getApplicationContext(), "Order confirmed", Toast.LENGTH_SHORT).show();
                            long dishId = orderDataDb.addDishData(selectedCuisines, orderId);
                            if (dishId > 0) {
                                Log.println(Log.INFO, "Dish data inserted", Integer.toString((int) dishId));
                            } else {
                                Log.println(Log.INFO, "Error inserting", null);
                            }
                            GoToMainActivity();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Select dish", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Log.println(Log.ERROR, "Order error !", ex.getMessage().toString());
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void GoToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void SetTextViewValues(String selected) {
        selected = selected + "\t" + "100";
        initiatePopupWindow();
        selectedCuisines.add(selected);
    }

    private void RemoveTextViewValues(String selected) {
        selectedCuisines.remove(selected);
    }

    private void SetListView() {
        listViewAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, selectedCuisines);
        listView.setAdapter(listViewAdapter);
    }

    public void vegClicked(View view) {
        CreateVegOption();
        expListAdapter = FetchExpandableListVegAdapter(view);
        expListView.setAdapter(expListAdapter);
    }

    public void nonvegClicked(View view) {
        CreateNonVegOption();
        expListAdapter = FetchExpandableListNonVegAdapter(view);
        expListView.setAdapter(expListAdapter);
    }

    public void beverageClicked(View view) {
        CreateBeverageOption();
        expListAdapter = FetchExpandableListBeverageAdapter(view);
        expListView.setAdapter(expListAdapter);
    }

    public void desertClicked(View view) {
        CreateDesertOption();
        expListAdapter = FetchExpandableListDesertAdapter(view);
        expListView.setAdapter(expListAdapter);
    }


    private PopupWindow pwindo;
    Button btnClosePopup;
    Button btnCancelOrder;
    TextView dishName;
    EditText quantity;
    private void initiatePopupWindow() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater)TakeOrder.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.quantity_price_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 450, 350, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
        }
    };


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TakeOrder Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sitcom.sreejithm.saltnpepper/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TakeOrder Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.sitcom.sreejithm.saltnpepper/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
