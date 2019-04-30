package com.example.voicecalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textResult;
    private static final int requestCode = 1234;

    private TextView resultTEXT;
    private TextView answerTEXT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTEXT = (TextView) findViewById(R.id.textResult);
        answerTEXT = (TextView) findViewById(R.id.answer);
       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceInput(view);
            }
        });
    }
    public void voiceInput(View view) {
        if (view.getId() == R.id.fab) {
            speech();
        }
    }

    public void speech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Toasting...");

        try {
            startActivityForResult(intent, 100);
        }
        catch(ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "Input Unrecognizable", Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code, int result_code, Intent intent) {
        super.onActivityResult(request_code, result_code, intent);

        switch(request_code) {
            case 100: if (result_code == RESULT_OK && intent != null) {
                ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String toShow = result.get(0);
                if (!(toShow.contains("+")) && !(toShow.contains("-")) && !(toShow.contains("*")) && !(toShow.contains("/"))) {
                    resultTEXT.setText("Invalid input, try again.");
                    answerTEXT.setText("");
                } else {
                    resultTEXT.setText(toShow);
                }
                spokenWord(toShow);
            }
            break;
        }
    }

    public void spokenWord(String result) {
        String newResult = result.replace("\\s+", "");
        if (result.contains("+")) {
            int separationIndex = newResult.indexOf("+");
            String sub1 = newResult.substring(0, separationIndex);
            String sub2 = newResult.substring(separationIndex + 1, newResult.length());
            addition(sub1, sub2);
        }
        if (result.contains("-")) {
            int separationIndex = newResult.indexOf("-");
            String sub1 = newResult.substring(0, separationIndex);
            String sub2 = newResult.substring(separationIndex + 1, newResult.length());
            subtraction(sub1, sub2);
        }
        if (result.contains("*")) {
            int separationIndex = newResult.indexOf("*");
            String sub1 = newResult.substring(0, separationIndex);
            String sub2 = newResult.substring(separationIndex + 1, newResult.length());
            multiplication(sub1, sub2);
        }
        if (result.contains("/")) {
            int separationIndex = newResult.indexOf("/");
            String sub1 = newResult.substring(0, separationIndex - 1);
            String sub2 = newResult.substring(separationIndex + 1, newResult.length());
            division(sub1, sub2);
        }
    }
    public void addition(String one, String two) {
        double first = Double.parseDouble(one);
        double second = Double.parseDouble(two);
        double answer = first + second;
        answerTEXT.setText(Double.toString(answer));

    }
    public void subtraction(String one, String two) {
        double first = Double.parseDouble(one);
        double second = Double.parseDouble(two);
        double answer = first - second;
        answerTEXT.setText(Double.toString(answer));
    }
    public void multiplication(String one, String two) {
        double first = Double.parseDouble(one);
        double second = Double.parseDouble(two);
        double answer = first * second;
        answerTEXT.setText(Double.toString(answer));
    }
    public void division(String one, String two) {
        double first = Double.parseDouble(one);
        double second = Double.parseDouble(two);
        double answer = first / second;
        answerTEXT.setText(Double.toString(answer));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
