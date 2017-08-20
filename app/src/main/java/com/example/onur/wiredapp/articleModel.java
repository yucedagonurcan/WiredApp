package com.example.onur.wiredapp;

import android.util.Log;

/**
 * Created by onur on 20.08.2017.
 */

public class articleModel{


    String articleName ;
    String articleLink ;
    String articleImageLink;
    String articleContent ;

    articleModel(){

        this.articleContent = "";
        this.articleLink = "";
        this.articleName = "";
        this.articleImageLink = "";


    }


    public void printArticleModel(){

        Log.d("Article Name = " , this.articleName);
        Log.d("Article Link = " , this.articleLink);
        Log.d("Article Content = " , this.articleContent);
        Log.d("Article ImageLink = " , this.articleImageLink);

    }
}
