package com.example.alper_arik.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //Variables
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText birthdayEditText;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    //temp Competitors object
    private Competitors competitor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Gets references
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        birthdayEditText = (EditText) findViewById(R.id.birthdayEditText);
        userNameEditText = (EditText) findViewById(R.id.registerUsernameEditText);
        passwordEditText = (EditText) findViewById(R.id.registerPasswordEditText);
        registerButton = (Button) findViewById(R.id.registerButton);

        //register button listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });

        //Clears field when user touches
        nameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameEditText.setText("");
                return false;
            }
        });
        surnameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                surnameEditText.setText("");
                return false;
            }
        });
        birthdayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                birthdayEditText.setText("");
                return false;
            }
        });
        userNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userNameEditText.setText("");
                return false;
            }
        });
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passwordEditText.setText("");
                return false;
            }
        });
    }

    //Register button event handler
    public void buttonClicked(){
        //Gets infos from fields
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String birthday = birthdayEditText.getText().toString();
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //All fields must be filled
        if(name.isEmpty() || surname.isEmpty() || birthday.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please enter all informations!",Toast.LENGTH_SHORT).show();
            return;
        }

        //Creates new competitor
        Competitors c = new Competitors(name, surname, birthday, username.toLowerCase(), password.toLowerCase(), 0);

        // username password checking
        //gets database
        DatabaseHelper db = DatabaseHelper.getInstance(this.getApplicationContext());
        competitor = db.getCompetitor(username);
        if(competitor != null){
            Toast.makeText(RegisterActivity.this,"Try Another username!",Toast.LENGTH_SHORT).show();
            return;
        }
        //Adds new competitor in database
        db.addCompetitor(c);
        //closing database
        db.close();

        //makes toast message
        Toast.makeText(RegisterActivity.this,"Register successfully",Toast.LENGTH_SHORT).show();

        //Intent to main screen
        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    /**
     *  If back key pressed , opens main screen
     */
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
