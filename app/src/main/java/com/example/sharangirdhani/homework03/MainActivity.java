package com.example.sharangirdhani.homework03;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.sharangirdhani.homework03.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayoutWords;
    EditText editTextwords;
    EditText editTextwordsMain;
    ImageButton imageButtonAdd;
    ImageButton imageButtonAddMain;
    ImageButton imageButtonMinusMain;
    ImageButton imageButtonMinus;
    ProgressBar progress;
    LinearLayout linearLayoutVertical;
    int buttonCount=1;
    int editTextCount=101;
    int lineaarLayoutCount=201;
    int buttonCountMinus=301 ;
    ArrayList<String> searchWords;
    final static String FINAL_DATA="MAINACT";
    final static int INTENT_KEY = 50;
    final static String BUNDLEDATA = "BUNDLE";

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        searchWords = new ArrayList<>();
        editTextwordsMain= binding.editTextWords;
        imageButtonAddMain= binding.imageButtonAdd;
        imageButtonMinusMain = binding.imageButtonMinus;

        linearLayoutVertical= binding.linearLayoutVertical;
        progress = binding.progressBar;
        progress.setVisibility(View.INVISIBLE);

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextwordsMain.getText().toString().trim().length() > 0) {
                    progress.setMax(searchWords.size() + 1);
                    progress.setProgress(0);
                    new SearchAsync().execute(searchWords);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please provide value to search",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonAddMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editTextwordsMain.getText().toString().trim().isEmpty())){
                    String key = editTextwordsMain.getText().toString();
                    if(!searchWords.contains(key)) {
                        searchWords.add(editTextwordsMain.getText().toString());
                        imageButtonAddMain.setVisibility(v.GONE);
                        imageButtonMinusMain.setVisibility(v.VISIBLE);
                        imageButtonMinusMain.setImageResource(R.drawable.remove);
                        //imageButtonMinusMain.setBackgroundResource(R.drawable.remove);
                        imageButtonMinusMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchWords.remove(editTextwordsMain.getText().toString());
                                LinearLayout temp= binding.linearLayoutWords;
                                temp.removeAllViews();
                                linearLayoutVertical.removeView(temp);
                            }
                        });
                        repeatControls();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Search keyword already present", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Search Value", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void repeatControls(){
        editTextwords=new EditText(this);
        imageButtonAdd=new ImageButton(this);
        linearLayoutWords=new LinearLayout(this);
        imageButtonMinus=new ImageButton(this);
        TableRow.LayoutParams layoutParams=new TableRow.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
        TableRow.LayoutParams layoutParamsButton=new TableRow.LayoutParams(220,110,1f);
        TableRow.LayoutParams layoutParamsButtonMinus=new TableRow.LayoutParams(220,110,1f);
        layoutParamsButton.setMargins(5,0,0,0);
        layoutParamsButtonMinus.setMargins(5,0,0,0);
        editTextwords.setLayoutParams(layoutParams);
        editTextwords.setId(editTextCount++);
        imageButtonMinus.setLayoutParams(layoutParamsButtonMinus);
        imageButtonMinus.setId(buttonCountMinus++);
        imageButtonMinus.setImageResource(R.drawable.remove);
        //imageButtonMinus.setBackgroundResource(R.drawable.remove);
        imageButtonMinus.setTag(R.drawable.remove);
        imageButtonMinus.setVisibility(View.GONE);
        imageButtonAdd.setLayoutParams(layoutParamsButton);
        imageButtonAdd.setId(buttonCount++);
        imageButtonAdd.setImageResource(R.drawable.add);
        //imageButtonAdd.setBackgroundResource(R.drawable.add);
        imageButtonAdd.setTag(R.drawable.add);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editTextwords.getText().toString().trim().isEmpty())){
                    searchWords.add(editTextwords.getText().toString());
                    imageButtonAdd.setVisibility(v.GONE);
                    imageButtonMinus.setVisibility(v.VISIBLE);
                    imageButtonMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText editTextRemove= (EditText) findViewById(v.getId()-200);
                            searchWords.remove(editTextRemove.getText().toString());
                            deleteControl(v.getId());
                        }
                    });
                    if(buttonCount<21){
                        repeatControls();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Search cannot exceed 20", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Search Value", Toast.LENGTH_SHORT).show();
                }
            }
        });
        linearLayoutWords.setId(lineaarLayoutCount++);
        linearLayoutWords.addView(editTextwords);
        linearLayoutWords.addView(imageButtonAdd);
        linearLayoutWords.addView(imageButtonMinus);
        linearLayoutVertical.addView(linearLayoutWords);
    }

    public void deleteControl(int id){
        LinearLayout temp= (LinearLayout) findViewById(id-100);
        temp.removeAllViews();
        linearLayoutVertical.removeView(temp);
    }

    class SearchAsync extends AsyncTask<ArrayList<String>,Integer,ArrayList<Occurence>>
    {
        Boolean isCaseSensitive = false;
        ArrayList<Occurence> data;
        ArrayList<String> fileData;
        final static String FILENAME = "textfile.txt";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            isCaseSensitive = binding.checkBoxMatchCase.isChecked();
        }

        @Override
        protected void onPostExecute(ArrayList<Occurence> finalData) {
            super.onPostExecute(finalData);
            progress.setVisibility(View.INVISIBLE);
            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(FINAL_DATA, finalData);
            intent.putExtra(BUNDLEDATA, bundle);
            startActivityForResult(intent, INTENT_KEY);
        }

        @Override
        protected ArrayList<Occurence> doInBackground(ArrayList<String>... params) {
            ArrayList<String> wordsToSearch = params[0];
            data = new ArrayList<>();
            TextSearchUtil.getDataFromFile(FILENAME, MainActivity.this);
            publishProgress(1);
            for (int i = 0; i < wordsToSearch.size(); i++) {
                data.addAll(TextSearchUtil.wordSearch(wordsToSearch.get(i), i%3, isCaseSensitive));
                publishProgress(i+2);
            }

            Collections.sort(data, Occurence.comp);

            publishProgress(wordsToSearch.size() + 1);
            return data;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int count = lineaarLayoutCount - 201;
        LinearLayout linearLayoutVertical = (LinearLayout) findViewById(R.id.linearLayoutVertical);
        linearLayoutVertical.removeViews(1,count);
        editTextwordsMain.setText("");
        searchWords.clear();
        imageButtonMinusMain.setVisibility(View.GONE);
        imageButtonAddMain.setVisibility(View.VISIBLE);
        buttonCount=1;
        editTextCount=101;
        lineaarLayoutCount=201;
        buttonCountMinus=301 ;
    }
}
