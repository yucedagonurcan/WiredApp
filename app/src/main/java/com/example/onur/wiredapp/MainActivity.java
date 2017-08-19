package com.example.onur.wiredapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> data;


    articleModel [] articleModelsArray = new articleModel[5];// We initialize the array of articles !



    String[] Names = {"Onur","Mihri","Serdar","Okan","Berkay","Gamze","Oluculer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        new ParsePage().execute("https://www.wired.com");
       // for (int i = 0 ; i < 5 ; i++){
        //    new ParseContentPage(articleModelsArray[i]).execute(articleModelsArray[i].articleLink);
        //}





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

        articleModel currentArticle;

        //I am creating a constructor for setting the value of this variable.
        //Just because I want to send this task the current article.
        public ParseContentPage(articleModel currentArticle){

            this.currentArticle=currentArticle;
        }



        @Override
        protected String doInBackground(String... strings) {

            //load the document !
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
        protected void onPostExecute(String result){

            for (int i = 0 ; i < 5 ; i ++){
                articleModelsArray[i].printArticleModel();

            }


        }

    }
}
