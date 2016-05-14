package es.upm.dit.adsw.ej7;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Pablo on 14/05/2016.
 */
public class RssArrayAdapter extends ArrayAdapter {
    private List<RssItem> data;
    private int layoutId;
    private Context mContext;

    public RssArrayAdapter(Context mContext, int layoutId, List<RssItem> data) {
        super(mContext, layoutId, data);
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(this.layoutId, parent, false);
        }
        TextView dateView = (TextView) convertView.findViewById(R.id.rssItemDate);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.rssItemDescription);
        RssItem item = (RssItem) this.data.get(position);
        ((TextView) convertView.findViewById(R.id.rssItemTitle)).setText(item.title);
        dateView.setText(item.date);
        descriptionView.setText(Html.fromHtml(item.description));
        return convertView;
    }
}
