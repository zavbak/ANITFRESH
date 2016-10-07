package ru.anit.anitfresh.ui.general;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.anit.anitfresh.R;


/**
 * Created by 79900 on 18.09.2016.
 */
public class AdapterTest extends RecyclerView.Adapter<AdapterTest.VH>{


    List<String> list;

    public AdapterTest(List<String> list) {
        this.list = list;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_catalog_item, parent, false);

        final VH vh = new VH(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        String name = list.get(position);
        holder.tvCatalogName.setText(name);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public ImageView imIsFolder;
        public TextView tvCatalogName;

        public VH(View v) {
            super(v);
            imIsFolder    = (ImageView) v.findViewById(R.id.imIsFolder);
            tvCatalogName = (TextView) v.findViewById(R.id.tvCatalogName);
        }
    }
}
