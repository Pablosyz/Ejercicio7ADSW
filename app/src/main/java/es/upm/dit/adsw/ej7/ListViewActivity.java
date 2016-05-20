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

import java.util.List;

import es.upm.dit.adsw.ej7.rss.RssContent;

/**
 * ListViewActivity
 *
 * @author Pablo Sánchez Yáñez <pablo.p.s@ieee.org> based in code given
 *         by proffessors and exercise directions.
 * @version 16.05.2016
 */
public class ListViewActivity extends AppCompatActivity {
    public static final String TAG = ListViewActivity.class.getName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_rss_list);

        listView = (ListView) findViewById(R.id.listview);

        List<RssContent.EntryRss> items = FilteredRssFeed.getEntries();
        RssArrayAdapter adapter = new RssArrayAdapter(ListViewActivity.this, R.layout.rss_item_row, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());

    }

    private class OnItemClickListenerListViewItem implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            RssArrayAdapter adapter = (RssArrayAdapter) listView.getAdapter();
            RssContent.EntryRss item = (RssContent.EntryRss) adapter.getItem(position);
            ListViewActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.link)));
        }

    }

}
