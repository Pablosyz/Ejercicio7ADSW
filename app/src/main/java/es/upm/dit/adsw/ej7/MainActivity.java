package es.upm.dit.adsw.ej7;

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
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.adsw.ej7.rss.FeedDownloader;
import es.upm.dit.adsw.ej7.rss.RssContent;

/**
 * RssArrayAdapter
 *
 * @author Pablo Sánchez Yáñez <pablo.p.s@ieee.org> based in code given
 *                              by proffessors and exercise directions.
 * @version 16.05.2016
 */

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
                String palabras = MainActivity.this.words.getText().toString();
                int position = feedSpinner.getSelectedItemPosition();
                String url = urlList.get(position);
                //Para analizar lo que va sucediendo pasamos al log las tareas realizadas:
                Log.d(MainActivity.TAG, "Palabras " + palabras);
                Log.d(MainActivity.TAG, "URL " + url);
                new RssRetrieveTask(null).execute(new String[]{url, palabras});
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "Error " + e.toString());
                Toast.makeText(MainActivity.this.getBaseContext(), "Error intentar cargar las noticias:", Toast.LENGTH_SHORT).show();
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
                String sitio = strings[0];
                String palabras = strings[1];
                //Para analizar lo que va sucediendo pasamos al log la tarea realizada:
                Log.d(MainActivity.TAG, "doInBackgroud " + sitio + "palabras" + palabras);
                List<RssContent.EntryRss> entradasRss =  new FeedDownloader().loadXmlFromNetwork(sitio);
                FilteredRssFeed.reset(palabras);
                for (RssContent.EntryRss item : entradasRss) {
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
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
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