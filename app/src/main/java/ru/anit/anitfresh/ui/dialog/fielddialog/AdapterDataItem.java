package ru.anit.anitfresh.ui.dialog.fielddialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.anit.anitfresh.R;


/**
 * Created by 79900 on 18.09.2016.
 */
public class AdapterDataItem extends RecyclerView.Adapter<AdapterDataItem.VH> {


    List<IDataItem> mListFull;
    List<IDataItem> mList;

    IViewFragmentDialogField mDialog;

    public AdapterDataItem(IViewFragmentDialogField dialog) {
        this.mDialog = dialog;
    }

    public void changeData(List<IDataItem> list) {
        this.mListFull = list;
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_catalog_item, parent, false);

        final VH vh = new VH(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        IDataItem item = mList.get(position);
        holder.tvCatalogName.setText(item.getName());

        holder.itemView.setOnClickListener(view -> {
            mDialog.onSelectionPosition(mList.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mList = new ArrayList<IDataItem>();
        if (charText.length() == 0) {

            mList.addAll(mListFull);

        } else {
            for (IDataItem item : mListFull) {
                if (item.filter(charText)) {
                    mList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class VH extends RecyclerView.ViewHolder{
        public ImageView imIsFolder;
        public TextView tvCatalogName;

        public VH(View v) {
            super(v);
            imIsFolder    = (ImageView) v.findViewById(R.id.imIsFolder);
            tvCatalogName = (TextView)  v.findViewById(R.id.tvCatalogName);

        }


    }
}
