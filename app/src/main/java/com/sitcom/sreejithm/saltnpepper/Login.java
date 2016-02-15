package com.sitcom.sreejithm.saltnpepper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Login extends AppCompatActivity {
    public EditText usernameText;
    public EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final View usernameView = findViewById(R.id.username);
        final View passwordView = findViewById(R.id.password);
        usernameText = (EditText) usernameView;
        passwordText = (EditText) passwordView;

        usernameText.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                usernameText.setText("");
                                            }
                                        }
        );

        passwordText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                passwordText.setText("");
            }
        });
    }

    public void loginattempt(View view) {
        Intent intent = new Intent(this, ChooseDish.class);
        String message = usernameText.getText().toString();// editText.getText().toString();
        intent.putExtra("login", message);
        startActivity(intent);
    }

}
