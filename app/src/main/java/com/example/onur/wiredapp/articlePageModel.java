package com.example.onur.wiredapp;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class articlePageModel extends AppCompatActivity{

//    articlePageModel(){
//
//        Log.d("articlePageModel","Loaded !!!!");
//    }

    String [] variablesOfArticle ;

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









    }


}
