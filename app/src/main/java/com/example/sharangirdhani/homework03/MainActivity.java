package com.example.sharangirdhani.homework03;

import android.databinding.DataBindingUtil;
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
    final static String COUNT_KEY="value";
    final static String WORDS_KEY="value2";
    final static int INTENT_KEY=50;

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
        progress.setMax(100);
        progress.setProgress(0);
        progress.setVisibility(View.INVISIBLE);

        imageButtonAddMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editTextwordsMain.getText().toString().trim().isEmpty())){
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
        TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
        TableRow.LayoutParams layoutParamsButton=new TableRow.LayoutParams(200,100,1f);
        TableRow.LayoutParams layoutParamsButtonMinus=new TableRow.LayoutParams(210,100,1f);
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
}
