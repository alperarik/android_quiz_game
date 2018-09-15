package com.example.alper_arik.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {
    //Variables
    private TextView questionNumberTextView;
    private TextView questionAreaTextView;
    private Button answer0Button;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;

    private int count = 0; //stores question number
    private int score = 0; //stores score

    private String[] questions = null;  //stores questions
    private String[] answers = null;    //stores parsable answers
    private String[] correctAnswers = null; //stores correct answers
    private String[] parsedAnswers = null; // stores parsed answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Gets references
        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        questionAreaTextView = (TextView) findViewById(R.id.questionAreaTextView);
        answer0Button = (Button) findViewById(R.id.answer0Button);
        answer1Button = (Button) findViewById(R.id.answer1Button);
        answer2Button = (Button) findViewById(R.id.answer2Button);
        answer3Button = (Button) findViewById(R.id.answer3Button);

        //Gets values from string.xml
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        correctAnswers = getResources().getStringArray(R.array.correctAnswers);

        //Button listeners
        answer0Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(count,0);
            }
        });
        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(count,1);
            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(count,2);
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(count,3);
            }
        });

        //initialize question field
        initialize(count);
    }

    /**
     * Initializes question field
     * @param i question number
     */
    public void initialize(int i){
        //if there is not remaining question
        if(i == questions.length){
            //Gets current competitor from LoginActivity
            Competitors c = LoginActivity.currentCompetitor;

            //gets database and sets score to db
            DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
            db.setScore(c.get_id(),score);
            db.close();

            //Intents ResultActivity
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            startActivity(intent);
            finish();

            //finishes this method
            return;
        }
        //Sets question number to screen
        String qNumber="Question "+Integer.toString(i+1);
        questionNumberTextView.setText(qNumber);
        //Sets question to screen
        questionAreaTextView.setText(questions[i]);
        //Parsing answers
        parsedAnswers = answers[i].split(":");
        //Sets parsed answers to selection buttons
        answer0Button.setText(parsedAnswers[0]);
        answer1Button.setText(parsedAnswers[1]);
        answer2Button.setText(parsedAnswers[2]);
        answer3Button.setText(parsedAnswers[3]);
    }

    /**
     * Checks answer is true or false
     * @param i question number
     * @param selected  selected button's id
     */
    public void checkAnswer(int i,int selected) {
        //if selected answer is correct
        if(selected == Integer.parseInt(correctAnswers[i])){
            //Creates dialog message
            final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            //customizing
            LayoutInflater factory = LayoutInflater.from(this);
            View customDialogView = factory.inflate(R.layout.custom_dialog_message, null);

            //sets builder custom view , and positive button
            builder.setView(customDialogView);
            builder.setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //increases question number and score and calls initialize method
                    count++;
                    score++;
                    initialize(count);
                }
            });

            //Creates dialog message
            builder.create().show();
        }
        //if selected answer is not correct
        else{
            //Gets current competitor from LoginActivity
            Competitors c = LoginActivity.currentCompetitor;
            //gets database and saves score in database
            DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
            db.setScore(c.get_id(), score);
            //closing database
            db.close();

            //Intent to ResultActivity
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("CorrectAnswer",parsedAnswers[Integer.parseInt(correctAnswers[i])]);
            startActivity(intent);
            finish();
        }
    }

    /**
     *  If back key pressed , opens main screen
     */
    public void onBackPressed() {
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
