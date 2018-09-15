package com.example.alper_arik.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //Variables
    private TextView resultTextView;
    private Button quitButton;
    private Button highScoresButton;
    private String info="";  //competitor's info
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Gets references
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        quitButton = (Button)findViewById(R.id.quitButton);
        highScoresButton = (Button) findViewById(R.id.highScoresButton);
        //gets database
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        //gets competitor from database
        Competitors competitor = db.getCompetitor(LoginActivity.currentCompetitor.get_id());
        //closing database
        db.close();

        info = "You answered all questions true!";
        //check  did user answer all questions true
        if(competitor.get_score() != 10){ // means user answer is false
            Intent intent = getIntent();
            String correctAnswer = intent.getStringExtra("CorrectAnswer");
            info = "Correct answer is \n"+correctAnswer;
        }
        //creates competitor info
        info +="\n\n\n"+competitor.get_name()+"\t"+competitor.get_surname();

        //Parsing birthday string
        String birthday[] = competitor.get_birthday().split(" ");
        try{
            int age = 2015 - Integer.parseInt(birthday[2]);
            info+="\nage "+age;
        }catch (ArrayIndexOutOfBoundsException e){
        }
        info+="\nscore "+ competitor.get_score()+"\n("+competitor.get_score()+"/10)";
        //Sets info to screen
        resultTextView.setText(info);

        //quit button listener
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intents main screen
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //high scores button listener
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intents high score screen
                Intent intent = new Intent(ResultActivity.this,HighScoresActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     *  If back key pressed , opens main screen
     */
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
