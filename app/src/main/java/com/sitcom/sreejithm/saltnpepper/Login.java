package com.sitcom.sreejithm.saltnpepper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sitcom.sreejithm.activity.RegisterActivity;
import com.sitcom.sreejithm.helper.Contact;
import com.sitcom.sreejithm.helper.SessionManager;
import com.sitcom.sreejithm.helper.SQLiteHandler;
import com.sitcom.sreejithm.app.AppConfig;
import com.sitcom.sreejithm.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Login extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;

    public EditText inputEmail;
    public EditText passwordText;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            Log.println(Log.INFO,"Loging in user : " + session.getUserName(),"");
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("login",session.getUserName());
            startActivity(intent);
            finish();
        }
        inputEmail.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View view) {
                                              inputEmail.setText("");
                                          }
                                      }
        );

        passwordText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                passwordText.setText("");
            }
        });


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {

                    // login user
                    //MySQL Datalogin
                    checkLogin(email, password); //commenting MySql DB Connection and and fetching inorder to fetch from
                    //SQLite Login
                    //loginThroughSqlite(email,password);

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    /**
     * Private constructor
     */
    private Login(){

    }

    /***
     * Singleton instance
     */
    private static Login instance;
    /**
     * Singleton initialization
     * @return
     */
    public static Login getInstance()
    {
        if(instance == null){
            instance = new Login();
        }
        return instance;
    }

    private void checkLogin(final String email,final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {


                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true,name);

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);
                        Log.println(Log.INFO, "Loging in user : " + session.getUserName(), "");
                        // Launch main activity
                        Intent intent = new Intent(Login.this,MainActivity.class);

                        intent.putExtra("login",name);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void fetchDish(final String email,final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Fetching dish ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_FETCH_DISH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Fetching dish: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {


                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true,name);

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);
                        Log.println(Log.INFO, "Loging in user : " + session.getUserName(), "");
                        // Launch main activity
                        Intent intent = new Intent(Login.this,MainActivity.class);

                        intent.putExtra("login",name);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     *
     * @param email
     * @param password
     */
    private void loginThroughSqlite(final String email, final String password) {
        Contact loginContact = db.checkLogin(email, password);
        Date current = new Date();
        if (loginContact != null) {
            // Create login session
            session.setLogin(true, loginContact.loggedInUser);

            // Inserting row in users table
            db.addUser(loginContact.loggedInUser, email, "EMP001", dateFormat.format(current).toString());
            Log.println(Log.INFO, "Loging in user : " + session.getUserName(), "");
            // Launch main activity
            Intent intent = new Intent(Login.this, MainActivity.class);

            intent.putExtra("login", loginContact.loggedInUser);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Incorrect login !!!", Toast.LENGTH_LONG).show();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
