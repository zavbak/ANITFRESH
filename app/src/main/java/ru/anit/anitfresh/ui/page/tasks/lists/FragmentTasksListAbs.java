package ru.anit.anitfresh.ui.page.tasks.lists;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.FragmentTasksListBinding;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.ui.dialog.dialogtask1.DialogTask;
import ru.anit.anitfresh.ui.dialog.taskdialog.FragmentDialogTask;


/**
 * Created by Александр on 28.09.2016.
 */

public abstract class FragmentTasksListAbs extends Fragment implements IViewFragmentTaskList{


    FragmentTasksListBinding binding;
    IPresenterFragmentTaskList presenter;

    private RecyclerView mRecyclerView;
    private AdapterTasks mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks_list, container, false);
        View rootView = binding.getRoot();

        mRecyclerView = binding.rvList;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    @Override
    public void showList(CashList cashList) {

        if(mAdapter == null){
            mAdapter = new AdapterTasks(this);
        }
        mAdapter.changeData(cashList);

        //mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void showDialogTask(Task task) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        DialogTask dialog = DialogTask.getInstance(task);
        dialog.show(ft, "tag_dialog_task");
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showTvText(String text) {
        binding.tvText.setText(text);
    }

    @Override
    public void showProgress() {
        binding.rvList.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
        binding.rvList.setVisibility(View.VISIBLE);
    }

}
