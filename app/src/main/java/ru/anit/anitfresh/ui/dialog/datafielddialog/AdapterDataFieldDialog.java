package ru.anit.anitfresh.ui.dialog.datafielddialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.R;


/**
 * Created by Александр on 10.10.2016.
 */

public class AdapterDataFieldDialog extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private final Context   mContext;
    private List<IDataChoice> mResults;
    private List<IDataChoice> mFullList;


    public AdapterDataFieldDialog(Context context, List<IDataChoice> mFullList) {
        this.mContext = context;
        this.mResults = new ArrayList<IDataChoice>();
        this.mFullList = mFullList;
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public IDataChoice getItem(int index) {
        return mResults.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment_catalog_item, parent, false);
        }
        IDataChoice field = getItem(position);
        ((TextView) convertView.findViewById(R.id.tvCatalogName)).setText(field.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<IDataChoice> list = findField(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = list;
                    filterResults.count  = list.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<IDataChoice>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<IDataChoice> findField(String strSearch) {

        String up_search = strSearch.toUpperCase();
        List<IDataChoice> list = new ArrayList<>();

        int i = 0;
        for (IDataChoice item : mFullList) {

            if (item.filter(up_search)) {
                i++;
                list.add(item);
                if (i == MAX_RESULTS) {
                    break;
                }
            }
        }

        return list;
    }
}
