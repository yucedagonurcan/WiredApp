package com.example.onur.wiredapp;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {


    List<String> data;
    Context mContext;

    String[] Names = {"Onur","Mihri","Serdar","Okan","Berkay","Gamze","Oluculer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mContext = this;
        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        new ParsePage().execute();





    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return Names.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_desc = (TextView)view.findViewById(R.id.textView_description);
            /*imageView.setImageResource();
            */
            textView_desc.setText(Names[i]);
            textView_desc.setBackgroundColor(getResources().getColor(R.color.white));
            return view;
        }
    }

    class ParsePage extends AsyncTask<String,Void,String>{



        @Override
        protected String doInBackground(String... strings) {
            //load the document !
            Document doc;
            try{
                //Connect to the website and get the HTML!
                doc = Jsoup.connect("https://www.wired.com").get();
                Element element = doc.getElementById("div");
                Log.d("Element :",element.toString());


            }catch (IOException e){

                e.printStackTrace();
            }
            return "Executed";
        }
        protected void onPostExecute(String result){

        }
        protected void onPreExecute(String res){

        }
    }
}
