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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;

import static junit.framework.Assert.assertEquals;

public class articlePageModel extends AppCompatActivity{

    Map<String, String> translatedWords = new HashMap<>();//HashMap to hold the words and their translations.

    String [] variablesOfArticle ;
    String topFiveWordsString = "Translation Of Top Five Occurences Words";
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

        Map<String, Integer> wordsOccurencesMap = new HashMap<String, Integer>();

        for (int i = 0 ; i < articleWords.size() ; i++){
            wordsOccurencesMap.put(articleWords.get(i),Collections.frequency(articleWords,articleWords.get(i)));
        }

        MapUtil mapUtil = new MapUtil();
        Map<String, Integer> wordsOccurencesMap_Sorted = new HashMap<String, Integer>();
        wordsOccurencesMap_Sorted = mapUtil.crunchifySortMap(wordsOccurencesMap);
        Iterator<Map.Entry<String, Integer>> it = wordsOccurencesMap_Sorted.entrySet().iterator();

        int i = 0;
        while (it.hasNext() && i < 5) {
            Map.Entry<String, Integer> pair = it.next();
            translatedWords.put(pair.getKey(),translateText(pair.getKey()));//Translate the words in order and add into the hashMap!
            i++;
        }




















        //
        // Set the Content of the Article after you finish the translation of the topFiveArticleWords !
        //
        articleContentTextView.setText(variablesOfArticle[1]);









    }

    public String translateText (final String textToTranslate) {

        final TextView topFiveWordsTextView = (TextView)findViewById(R.id.topFiveWordsTextView);

        urlYandexTranslate urlYandexTranslateObject = new urlYandexTranslate(textToTranslate); // Create a object with the desired text to translate.

        String translationUrl = urlYandexTranslateObject.getUrl();//Get the entire URL for this request.

        String translatedString = "null";
        RequestQueue queue = Volley.newRequestQueue(this); // this = context

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, translationUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        // display response
                        Log.d("Response", response.toString());
                        String translatedString = (String) response.getJSONArray("text").get(0);

                        topFiveWordsString = topFiveWordsString + "\n" +  textToTranslate + " = "  + translatedString ;
                        topFiveWordsTextView.setText(topFiveWordsString);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", textToTranslate);
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);














        return translatedString;

    }




    public class urlYandexTranslate {

        String entryUrl ;
        String textToTranslate;
        String apiKey ;
        String lang ;

        urlYandexTranslate(String textToTranslateString){
            this.entryUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
            this.apiKey = "key=trnsl.1.1.20170821T113600Z.abc385b492600154.3fb34f603072872e5ddf02ce9b91397b1ac3e459";
            this.lang = "&lang=en-tr";
            this.textToTranslate = "&text="+textToTranslateString;
        }

        String getUrl (){

            String url = entryUrl+apiKey+textToTranslate+lang;
            return url;

        }


    }










}
