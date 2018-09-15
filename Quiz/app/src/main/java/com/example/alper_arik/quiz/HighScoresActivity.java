package com.example.alper_arik.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoresActivity extends AppCompatActivity {

    //Variables
    private ListView highScoresListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        //gets reference
        highScoresListView = (ListView) findViewById(R.id.highScoresListView);

        //gets database
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        //gets all existing competitors from database
        ArrayList<Competitors> al = db.getAllCompetitorScores();
        //closing database
        db.close();

        //Sorts competitors by score
        Collections.sort(al, new Comparator<Competitors>() {
            @Override
            public int compare(Competitors lhs, Competitors rhs) {
                return rhs.get_score() - lhs.get_score();
            }
        });

        //allocetes String array
        String [] competitors = new String[al.size()];
        //Converts arrayList to String array
        for(int i = 0; i < al.size(); i++){
            competitors[i] = al.get(i).get_name()+"  "+al.get(i).get_surname()+"  score: "+Integer.toString(al.get(i).get_score());
        }

        //Creates custom array adapter
        ArrayAdapter adapter = new CustomAdapter(this,competitors);
        //sets adapter to ListView
        highScoresListView.setAdapter(adapter);
    }

    /**
     *  If back key pressed , opens main screen
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighScoresActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
