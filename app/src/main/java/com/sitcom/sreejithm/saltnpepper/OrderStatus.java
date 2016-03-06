package com.sitcom.sreejithm.saltnpepper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.sitcom.sreejithm.expandablelistview.ExpandableListAdapter;
import com.sitcom.sreejithm.helper.OrderData;
import com.sitcom.sreejithm.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SreejithM on 3/5/2016.
 */
public class OrderStatus extends AppCompatActivity {
    ExpandableListAdapter adapter;
    private int lastExpandedGroup = -1;
    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;
    private String SelectedGrp = "";
    SQLiteHandler db;

    ListView dishesOnTableList;
    ArrayAdapter dishListOnTableAdapter;

    List<String> tableGrp;
    List<String> tableOrderDetails;
    private HashMap<String, OrderData> orderMapping;
    Map<String , List<String>> orderOptionList;

    List<String> selectedCuisines= null;

    Button btnDeliveredOrder;
    Button btnCompeleteOrder;
    private long orderId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);
        db = new SQLiteHandler(getApplicationContext());

        expListView = (ExpandableListView) findViewById(R.id.tableOrderList);
        dishesOnTableList = (ListView) findViewById(R.id.dishesForTable);

        btnDeliveredOrder = (Button) findViewById(R.id.orderDelivered);
        btnCompeleteOrder = (Button) findViewById(R.id.orderCompleted);

        SetOrderMapping();

        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);

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
                String selectedTable = expListAdapter.getGroup(groupPosition).toString();
                orderId = orderMapping.get(selectedTable).OrderId;
                SetSelectedTableDish(orderId);
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                orderId = 0; // setting the order id to be default such that only the expanded orders
                //are made eligible for complete order.
                SetSelectedTableDish(orderId);
            }
        });

        dishesOnTableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        btnDeliveredOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderId != 0) {
                    int rowCnt = db.SetOrderServed(orderId);
                    orderId = 0;
                    SetOrderMapping();
                    SetSelectedTableDish(orderId);
                } else {
                    Toast.makeText(getApplicationContext(), "Select a table", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCompeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderId != 0) {
                    int rowCnt = db.SetOrderCompleted(orderId);
                    orderId = 0;
                    SetOrderMapping();
                    SetSelectedTableDish(orderId);
                } else {
                    Toast.makeText(getApplicationContext(), "Select a table", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SetOrderMapping() {
        orderMapping = db.getOrderDetails();
        CreateTableGrpList();

        expListAdapter = FetchExpandableListViewForOrders();

        expListView.setAdapter(expListAdapter);
    }

    /**
     * function to set the dishes of selected table
     * @param orderId
     */
    private void SetSelectedTableDish(long orderId){
        selectedCuisines=  new ArrayList<String>();
        if(orderId > 0){
            selectedCuisines  = db.GetDishList(orderId);
        }
        dishListOnTableAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview,selectedCuisines );
        dishesOnTableList.setAdapter(dishListOnTableAdapter);
    }

    private void CreateTableGrpList() {
        tableGrp = new ArrayList<String>();
        orderOptionList = new LinkedHashMap<String,List<String>>();

        for (String key:orderMapping.keySet()) {
            tableGrp.add(key);
            Log.println(Log.INFO,"Order Key :",key.toString());
            OrderData orderData = orderMapping.get(key);
            tableOrderDetails = GetTableDataList(orderData);
            orderOptionList.put(key, tableOrderDetails);
        }
    }

    private List<String> GetTableDataList(OrderData ordrData){
        List<String> OrderStringList = new ArrayList<String>();
        OrderStringList.add("Time : " + ordrData.OrderConfirmationTime);
        OrderStringList.add(ordrData.WaiterName);
        OrderStringList.add("Server name :" + ordrData.ServerName);
        OrderStringList.add(ordrData.GuestCount);
        String orderStatus = ordrData.OrderServedTime != ""&& ordrData.OrderServedTime != null ? "Served at " + ordrData.OrderServedTime : "Pending";
        OrderStringList.add(orderStatus);

        return OrderStringList;
    }


    private ExpandableListAdapter FetchExpandableListViewForOrders() {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, tableGrp, orderOptionList);
        return expListAdapter;
    }

}