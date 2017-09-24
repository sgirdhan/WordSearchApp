package com.example.sharangirdhani.homework03;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultActivity extends AppCompatActivity {

    final static String[] colors = {"Red" , "Blue", "Green"};
    TextView textViewWordAddFrequencies;
    LinearLayout linearLayoutWordFrequencies;
    Button buttonFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras().getBundle(MainActivity.BUNDLEDATA);
        ArrayList<Occurence> data = (ArrayList<Occurence>) b.getSerializable(MainActivity.FINAL_DATA);

        linearLayoutWordFrequencies= (LinearLayout) findViewById(R.id.linearLayoutWordFrequencies);
        buttonFinish= (Button) findViewById(R.id.buttonFinish);

        if(data.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.nothing_found), Toast.LENGTH_SHORT).show();
        }

        for (Occurence o : data) {
            Log.d("demo", o.toString());
            int n = o.getFinalValue().length();
            SpannableString hashText = new SpannableString(o.getFinalValue());
            hashText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, n-1, 0);
            String temp = o.getFinalValue().toLowerCase();
            Matcher matcher = Pattern.compile(o.getKeyword().toLowerCase()).matcher(temp);
            while (matcher.find())
            {
                if(colors[o.getColor()].equals("Red")){
                    hashText.setSpan(new ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(), 0);
                }
                if(colors[o.getColor()].equals("Blue")){
                    hashText.setSpan(new ForegroundColorSpan(Color.BLUE), matcher.start(), matcher.end(), 0);
                }
                if(colors[o.getColor()].equals("Green")){
                    hashText.setSpan(new ForegroundColorSpan(Color.GREEN), matcher.start(), matcher.end(), 0);
                }
            }
            textViewWordAddFrequencies=new TextView(this);
            textViewWordAddFrequencies.setTextSize(18);
            textViewWordAddFrequencies.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            textViewWordAddFrequencies.setText(hashText);
            textViewWordAddFrequencies.setPadding(100, 15, 0, 15);
            linearLayoutWordFrequencies.addView(textViewWordAddFrequencies);
        }
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}