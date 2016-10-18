package ru.anit.anitfresh.ui.dialog.dialogtask1;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.DialogTask1Binding;
import ru.anit.anitfresh.databinding.DialogTaskBinding;
import ru.anit.anitfresh.metaobject.entities.Task;

/**
 * Created by 79900 on 18.10.2016.
 */

public class DialogTask extends DialogFragment implements IViewDialogTask {


    IPresenterDialogTask presenter;
    DialogTask1Binding binding;

    /**
     * instance dialog
     *
     * @param task
     * @return
     */
    public static DialogTask getInstance(Task task) {
        DialogTask dialog = new DialogTask();
        dialog.setPresenter(new PresenterDialogTask(task, dialog));
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_task1, null, false);

        binding.tvTitle.setText("Задача № 5487 от 04.09.1976");

        return binding.getRoot();
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

    }

    /**
     *
     * @param presenter
     */
    public void setPresenter(IPresenterDialogTask presenter) {
        this.presenter = presenter;
    }

}