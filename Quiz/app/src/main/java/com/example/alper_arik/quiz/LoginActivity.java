package com.example.alper_arik.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //Variables
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    //Current competitor's infos (not include username nad password)
    public static Competitors currentCompetitor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Gets references
        usernameEditText = (EditText) findViewById(R.id.loginUsernameEditText);
        passwordEditText = (EditText) findViewById(R.id.loginPasswordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        //clear fields
        usernameEditText.setText("");
        passwordEditText.setText("");
        //Login button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });
    }

    //Login button event handler
    public void buttonClicked(){
        //Gets username and password from login screen
        String username = usernameEditText.getText().toString().toLowerCase();
        String password = passwordEditText.getText().toString().toLowerCase();

        //gets database
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());

        //Gets competitor
        currentCompetitor = db.getCompetitor(username, password);
        //closing database
        db.close();

        //Checks is there a competitor which has got these username and password
        if(currentCompetitor != null){
            //make toast message
            Toast.makeText(LoginActivity.this,"Welcome!",Toast.LENGTH_SHORT).show();
            //logs current competitor's id
            Log.d("TAG","current competitor id : "+Integer.toString(currentCompetitor.get_id()));
            //clean fields
            usernameEditText.setText("");
            passwordEditText.setText("");

            //make intent to quiz activity
            Intent intent = new Intent(LoginActivity.this,QuizActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            //make toast message
            Toast.makeText(LoginActivity.this,"Incorrect!",Toast.LENGTH_LONG).show();
        }
    }

    /**
     *  If back key pressed , opens main screen
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
