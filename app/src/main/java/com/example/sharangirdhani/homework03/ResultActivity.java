package com.example.sharangirdhani.homework03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras().getBundle(MainActivity.BUNDLEDATA);
        ArrayList<Occurence> data = (ArrayList<Occurence>) b.getSerializable(MainActivity.FINAL_DATA);

        int n = data.size();

        for (Occurence o : data) {
            Log.d("demo", o.toString());
            Toast.makeText(this, o.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
