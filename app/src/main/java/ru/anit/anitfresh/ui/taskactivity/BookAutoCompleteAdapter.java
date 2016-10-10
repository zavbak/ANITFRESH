package ru.anit.anitfresh.ui.taskactivity;

import android.content.Context;
import android.database.Cursor;
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
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;

/**
 * Created by Александр on 10.10.2016.
 */

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;

    private final Context mContext;
    private List<Catalog> mResults;
    private List<Catalog> mFullList;


    private List<Catalog> getList() {

        Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                TYPE_ENTITIES.CONTRACTOR.getNameFromBase()}, null);

        List<Catalog> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())

        {
            do {

                ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                Catalog item = (Catalog) BuilderHelper.getObject(objectSyncTable);

                list.add(item);


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }


        return list;

    }

    public BookAutoCompleteAdapter(Context context) {
        mContext  = context;
        mResults  = new ArrayList<Catalog>();
        mFullList = getList();


    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Catalog getItem(int index) {
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
        Catalog catalog = getItem(position);
        ((TextView) convertView.findViewById(R.id.tvCatalogName)).setText(catalog.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Catalog> books = findCatalog(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<Catalog>) results.values;
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
    private List<Catalog> findCatalog(String nameCatalog) {
        // GoogleBooksService is a wrapper for the Google Books API
        //GoogleBooksService service = new GoogleBooksService (mContext, MAX_RESULTS);


        List<Catalog> catalogs = new ArrayList<>();

        String up_search = nameCatalog.toUpperCase();

        int i = 0;
        for (Catalog item: mFullList) {

            String up_name  = item.getName().toUpperCase();

            if(up_name.contains(up_search)){

                i++;
                catalogs.add(item);
                if(i == MAX_RESULTS){
                    break;
                }


            }

        }

        return catalogs;
    }
}
