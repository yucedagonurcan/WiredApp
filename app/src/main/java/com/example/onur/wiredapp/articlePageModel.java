package com.example.onur.wiredapp;

import android.icu.text.BreakIterator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;

import static junit.framework.Assert.assertEquals;

public class articlePageModel extends AppCompatActivity{




    public class wordsAndOccurencesModel {

        String word;
        Integer occurence;

        wordsAndOccurencesModel(){
            this.word = "";
            this.occurence = 0;
        }

    }


    String [] variablesOfArticle ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_page);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService
                (this.LAYOUT_INFLATER_SERVICE);
        RelativeLayout customLayoutReference = (RelativeLayout) inflater.inflate(R.layout.customlayout, null);



        Bundle extras = getIntent().getExtras();
        if (extras != null){
             variablesOfArticle = extras.getStringArray("variablesOfArticle");
        }

        //Set the Views of the layout .
        ImageView articlePageImageView = (ImageView)findViewById(R.id.imageViewArticlePage);
        TextView articleContentTextView = (TextView)findViewById(R.id.articleContentTextView);
        TextView articleNameTextView = (TextView)findViewById(R.id.articleNameTextView);


        Picasso
                .with(this)
                .load(variablesOfArticle[2])
                .fit()
                .into(articlePageImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Success","Complete");
                    }

                    @Override
                    public void onError() {

                    }
                });

        articleContentTextView.setText(variablesOfArticle[1]);
        articleNameTextView.setText(variablesOfArticle[0]);

        List<String> articleWords = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(variablesOfArticle[1]);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) { // If the size of words are not equal to lastIndex , we can continue.
            int firstIndex = lastIndex; //We are defining first index of each word beginning with lastIndex we used.
            lastIndex = breakIterator.next(); //We are iterating the last index to end of the current word.
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(variablesOfArticle[1].charAt(firstIndex))) { // If we didn't
                articleWords.add(variablesOfArticle[1].substring(firstIndex, lastIndex).toLowerCase());
            }
        }
        Integer[] articleWordsOccurences = new Integer[articleWords.size()];

















        Map<String, Integer> wordsOccurencesMap = new HashMap<String, Integer>();
        for (int i = 0 ; i < articleWords.size() ; i++){


            wordsOccurencesMap.put(articleWords.get(i),Collections.frequency(articleWords,articleWords.get(i)));

        }



        MapUtil mapUtil = new MapUtil();
        Map<String, Integer> wordsOccurencesMap_Sorted = new HashMap<String, Integer>();


        wordsOccurencesMap_Sorted = mapUtil.crunchifySortMap(wordsOccurencesMap);












    }





}
