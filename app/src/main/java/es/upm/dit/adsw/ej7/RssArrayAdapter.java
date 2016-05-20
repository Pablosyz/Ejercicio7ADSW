package es.upm.dit.adsw.ej7;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.upm.dit.adsw.ej7.rss.RssContent;

/**
 * RssArrayAdapter
 *
 * @author Pablo Sánchez Yáñez <pablo.p.s@ieee.org> based in code given
 *         by proffessors and exercise directions.
 * @version 16.05.2016
 */
public class RssArrayAdapter extends ArrayAdapter {
    private List<RssContent.EntryRss> entryRssList;
    private int layoutId;
    private Context myContext;

    public RssArrayAdapter(Context mContext, int layoutId, List<RssContent.EntryRss> datos) {
        super(mContext, layoutId, datos);
        this.layoutId = layoutId;
        this.myContext = mContext;
        this.entryRssList = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RssContent.EntryRss entryRss = this.entryRssList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.rss_item_row, parent, false);
        }
        TextView titleView = (TextView) convertView.findViewById(R.id.rssItemTitle);
        TextView dateView = (TextView) convertView.findViewById(R.id.rssItemDate);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.rssItemDescription);
        titleView.setText(entryRss.title);
        dateView.setText(entryRss.published);
        descriptionView.setText(Html.fromHtml(entryRss.description));
        return convertView;
    }
}
