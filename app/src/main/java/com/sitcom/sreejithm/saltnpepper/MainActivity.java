package com.sitcom.sreejithm.saltnpepper;

/**
 * Created by SreejithM on 3/3/2016.
 */

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sitcom.sreejithm.helper.SQLiteHandler;
import com.sitcom.sreejithm.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnChooseDish;
    private Button btnCheckOrder;
    private NumberPicker tablenum;
    private SQLiteHandler db;
    private SessionManager session;
    private int tableSelected;

    private String ServerName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnChooseDish = (Button) findViewById(R.id.buttonChooseDish);
        //btnCheckOrder =(Button) findViewById(R.id.chekOrder);
        tablenum = (NumberPicker) findViewById(R.id.numberPicker);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        tablenum.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        tablenum.setMaxValue(30);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        tablenum.setWrapSelectorWheel(true);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);
        
        //
        btnChooseDish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                takeorder();
                //chooseDish();
            }
        });
        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        /**
         * the following part is no longer used and commented for the version
         */
        /*
        btnCheckOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckOrderStatus();                
            }
        });
        */
        //Set a value change listener for NumberPicker
        tablenum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tableSelected = newVal;
            }
        });
    }

    private void CheckOrderStatus() {
        Intent intent = new Intent(MainActivity.this, OrderStatus.class);
        startActivity(intent);
        //finish();
    }

    private void chooseDish() {
        Intent intent = new Intent(MainActivity.this, ChooseDish.class);
        startActivity(intent);
        //finish();
    }


    public void takeorder(){
        if(tableSelected != 0) {
            Intent intent = new Intent(MainActivity.this, TakeOrder.class);
            intent.putExtra("total_guest", "Total Guest : 1");
            intent.putExtra("table_number", "Table Number : "+String.valueOf(tableSelected));
            intent.putExtra("waiter_assigned", "Waiter Assigned : Rocky");//+ServerName!= "" ? ServerName : "Rocky");
            intent.putExtra("server_name", "Server Assigned : Rocky");//"+ServerName!= "" ? ServerName : "Rocky");
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Table details missing", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false,"");

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}