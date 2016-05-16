package es.upm.dit.adsw.ej7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import es.upm.dit.adsw.ej7.rss.RssContent;

/**
 * Created by Pablo on 14/05/2016.
 */
public class ListViewActivity extends AppCompatActivity {
    public static final String TAG = ListViewActivity.class.getName();

    private class OnItemClickListenerListViewItem implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Log.i(ListViewActivity.TAG, String.format("[%d] %s",
                    new Object[]{Integer.valueOf(position), (FilteredRssFeed.getEntries().get(position)).title}));
            ListViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(RssContent.EntryRss.link)));
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_rss_list);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new RssArrayAdapter(ListViewActivity.this, R.layout.rss_item_row, FilteredRssFeed.getEntries()));
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }
}
