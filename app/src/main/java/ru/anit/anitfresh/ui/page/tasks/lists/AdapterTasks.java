package ru.anit.anitfresh.ui.page.tasks.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databus.EventBusMessageOnInform;
import ru.anit.anitfresh.general.general.DataUtils;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.ui.dialog.dialogtask1.DialogTask;


/**
 * Created by 79900 on 18.09.2016.
 */
public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.VH>{

    List<Task> list;
    HashMap<String,MetaObject> cashObjects;

    IViewFragmentTaskList mViewFragmentTaskList;

    public AdapterTasks(IViewFragmentTaskList viewFragmentTaskList) {
        this.mViewFragmentTaskList = viewFragmentTaskList;

    }


    public void changeData(CashList cashList){
        this.list = cashList.getList();
        this.cashObjects = cashList.getObjectHashMap();
        notifyDataSetChanged();
    }



    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tasks_list_item, parent, false);

        final VH vh = new VH(v);
        return vh;
    }


    private String getCatalogText(String guid){


        MetaObject metaObject = cashObjects.get(guid);

        if(metaObject==null){
            return "";
        }

        return ((Catalog) metaObject).getName();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        Task task = list.get(position);
        holder.tvTitle.setText(task.getTitle());

        long dataEnd = DataUtils.dataJsonToLong(task.getEndTask());

        holder.tvDataItem_tasks.setText(DataUtils.getDate(dataEnd));

        holder.tvContractorItem_Task.setText(getCatalogText(task.getGuidContractor()));
        holder.tvOtvetstveniyItem_tasks.setText(getCatalogText(task.getGuidOtvetstvenniy()));

        // открываем диалог
        holder.itemView.setOnClickListener(view -> {
            mViewFragmentTaskList.showDialogTask(task);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvDataItem_tasks;
        public TextView tvContractorItem_Task;
        public TextView tvOtvetstveniyItem_tasks;

        public VH(View v) {
            super(v);
            tvTitle                 = (TextView) v.findViewById(R.id.tvTitleItem_Task);
            tvDataItem_tasks        = (TextView) v.findViewById(R.id.tvDataItem_tasks);
            tvContractorItem_Task   = (TextView) v.findViewById(R.id.tvContractorItem_Task);
            tvOtvetstveniyItem_tasks   = (TextView) v.findViewById(R.id.tvOtvetstveniyItem_tasks);
        }
    }




}
