package com.example.onur.wiredapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Context context = this ;
    List<String> data;
    articleModel [] articleModelsArray = new articleModel[5];// We initialize the array of articles !

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0 ; i < 5 ; i ++){

            articleModel tempArticleModel = new articleModel();
            articleModelsArray[i] = tempArticleModel ;
        }
        listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if(articleModelsArray[position].articleContent != ""){ // Don't load the next page if the articles aren't downloaded !

                    Intent myIntent = new Intent(view.getContext(), articlePageModel.class);
                    String [] articleVariables = {articleModelsArray[position].articleName,
                            articleModelsArray[position].articleContent,
                            articleModelsArray[position].articleImageLink};
                    myIntent.putExtra("variablesOfArticle",articleVariables);
                    startActivityForResult(myIntent, 0);

                }

            }
        });

        new ParsePage().execute("https://www.wired.com");
    }
    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return articleModelsArray.length;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_desc = (TextView)view.findViewById(R.id.textView_description);
            if (articleModelsArray[i].articleImageLink != ""){
                Picasso
                        .with(context)
                        .load(articleModelsArray[i].articleImageLink)
                        .fit()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Success","Compelete");
                            }

                            @Override
                            public void onError() {

                            }
                        });

            }
            textView_desc.setText(articleModelsArray[i].articleName);

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
                doc = Jsoup.connect(strings[0]).get();
                Elements elements = doc.getElementsByClass("secondary-grid-component");
                Elements articleNames  = elements.select("h5"); // articleNames !!
                Elements articleLinks = elements.select("a"); // articleLinks !!

                for (int i = 0 ; i < 5 ; i ++){
                    articleModel tempArticleModel = new articleModel();
                    tempArticleModel.articleName = articleNames.get(i).text().toString();
                    tempArticleModel.articleLink = strings[0] + articleLinks.get(i).attr("href");
                    articleModelsArray[i] = tempArticleModel;
                    articleModelsArray[i].printArticleModel();

                }

            }catch (IOException e){

                e.printStackTrace();
            }
            return "Executed";
        }
        protected void onPostExecute(String result){

            for (int i = 0 ; i < 5 ; i ++){
                articleModelsArray[i].printArticleModel();
                new ParseContentPage(articleModelsArray[i]).execute(articleModelsArray[i].articleLink);
            }

        }
        protected void onPreExecute(String res){

        }
    }

    class ParseContentPage extends  AsyncTask<String,Void,String>{

        //This currentArticle variable will be my parameter to send current article from ParsePage class.
        //Because I need to change the values of articleImage and articleContent using another page !
        articleModel currentArticle = new articleModel();

        //I am creating a constructor for setting the value of this variable.
        //Just because I want to send this task the current article.
        public ParseContentPage(articleModel currentArticle){

            this.currentArticle=currentArticle;
        }
        @Override
        protected String doInBackground(String... strings) {
            //Load the document !
            Document doc;
            try{
                //Connect to the website and get the HTML!
                doc = Jsoup.connect(strings[0]).get();
                Elements elements = doc.select("article");// Article Contents Outer !
                Elements articleContentElements = elements.select("p");//Article Contents (Just Strings !)
                currentArticle.articleContent = articleContentElements.text().toString();
                Elements meta = doc.select("meta");// Article Images !
                for (int i = 0 ; i < meta.size() ; i++){

                    String metaProperty = meta.get(i).attr("property").toString();

                    if( metaProperty.equals("og:image")){
                        String articleImageLink = meta.get(i).attr("content");//Image Link !
                        currentArticle.articleImageLink = articleImageLink ;
                    }
                }
            }catch (IOException e){

                e.printStackTrace();
            }
            return "Executed";
        }
        protected void onPostExecute(final String result){
            listView.invalidateViews();
        }
    }

}