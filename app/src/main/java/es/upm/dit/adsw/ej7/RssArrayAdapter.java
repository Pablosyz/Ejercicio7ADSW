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
 * Created by Pablo on 14/05/2016.
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService( myContext.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(this.layoutId, parent, false);
        }
        TextView dateView = (TextView) convertView.findViewById(R.id.rssItemDate);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.rssItemDescription);
        RssContent.EntryRss entryRss = this.entryRssList.get(position);
        ((TextView) convertView.findViewById(R.id.rssItemTitle)).setText(entryRss.title);
        dateView.setText(entryRss.date);
        descriptionView.setText(Html.fromHtml(entryRss.description));
        return convertView;
    }
}
