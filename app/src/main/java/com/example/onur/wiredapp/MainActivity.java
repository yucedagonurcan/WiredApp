package com.example.onur.wiredapp;

import android.graphics.Color;
import android.media.Image;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {


    String[] Names = {"Onur","Mihri","Serdar","Okan","Berkay","Gamze","Oluculer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
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
}
