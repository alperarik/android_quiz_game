package com.example.alper_arik.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *  Launcher Activity
 */
public class MainActivity extends AppCompatActivity {
    //Variables
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets referecnes
        loginButton = (Button) findViewById(R.id.MainLoginButton);
        registerButton = (Button) findViewById(R.id.MainregisterButton);

        //Login button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Register button listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     *  If back key pressed , opens Dialog message
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If back key pressed , opens Dialog message
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to exit the application?");
            
            //Dismiss dialog message and close finishes MainActivity
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            //Dismiss dialog message
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //Creates dialog message
            builder.create().show();
        }

        //DEBUG PURPOSE ONLY
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
            db.deleteDB();
            Toast.makeText(this, "DB deleted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
