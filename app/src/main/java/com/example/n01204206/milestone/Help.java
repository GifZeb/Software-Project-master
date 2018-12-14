package com.example.n01204206.milestone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Help extends AppCompatActivity {

    TextView question1;
    TextView answer1;
    TextView question2;
    TextView answer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        question1 = (TextView)findViewById(R.id.ques1);
        answer1 = (TextView)findViewById(R.id.ans1);
        question2 = (TextView)findViewById(R.id.ques2);
        answer2 = (TextView)findViewById(R.id.ans2);
    }
}
