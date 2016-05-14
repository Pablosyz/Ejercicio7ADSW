package es.upm.dit.adsw.ej7;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import es.upm.dit.adsw.ej7.rss.FeedDownloader;
import es.upm.dit.adsw.ej7.rss.FeedParser;
import es.upm.dit.adsw.ej7.rss.RssContent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Spinner feedSpinner;
    private ProgressBar progressBar;
    private EditText words;
    private List<String> optionList;
    private List<String> urlList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private class ReadOnClickListener implements OnClickListener {

        public void onClick(View v) {
            try {
                String words = MainActivity.this.words.getText().toString();
                int position = feedSpinner.getSelectedItemPosition();
                String url = urlList.get(position);
                Log.d(MainActivity.TAG, "Palabras " + words);
                Log.d(MainActivity.TAG, "URL " + url);
                new RssRetrieveTask(null).execute(new String[]{url, words});
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "Error " + e.toString());
                Toast.makeText(MainActivity.this.getBaseContext(), "Error al recuperar las noticias", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RssRetrieveTask extends AsyncTask<String, Void, Void> {
        private RssRetrieveTask(Object o) {
        }

        protected void onPreExecute(){
            super.onPreExecute();
            MainActivity.this.progressBar.setVisibility(View.VISIBLE);
        }
        protected Void doInBackground(String... strings) {
            try {
                String site = strings[0];
                String words = strings[1];
                Log.d(MainActivity.TAG, "doInBackgroud " + site + " words " + words);
                List<RssContent.EntryRss> entries =  new FeedDownloader().loadXmlFromNetwork(site);
                FilteredRssFeed.reset(words);
                for (RssContent.EntryRss item : entries) {
                    FilteredRssFeed.add(item);
                }
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "Error RssRetrieveTask" + e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            MainActivity.this.progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MainActivity.this, RssListActivity.class);
            //intent.setFlags()
            MainActivity.this.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.feedSpinner = (Spinner) findViewById(R.id.main_spinner);
        this.words = (EditText) findViewById(R.id.main_editText);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.optionList = new ArrayList<>();
        this.urlList = new ArrayList<>();
        add("El País",
                "http://ep00.epimg.net/rss/elpais/portada.xml");
        add("SlashDat",
                "http://dat.etsit.upm.es/?q=/rss/all");
        add("NY Times - Technology",
                "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml");
        add("Xataka Android", "http://feeds.weblogssl.com/xatakandroid");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, optionList);
        this.feedSpinner.setAdapter(adapter);
        Button leerNoticias = (Button) findViewById(R.id.leer_button);
        leerNoticias.setOnClickListener(new ReadOnClickListener());
    }

    private void add(String site, String url) {
        optionList.add(site);
        urlList.add(url);
    }


}