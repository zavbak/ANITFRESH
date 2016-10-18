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

public class DialogTask extends DialogFragment implements IViewDialogTask, View.OnClickListener {


    IPresenterDialogTask presenter;
    DialogTask1Binding binding;


    /**
     *
     * @param guid
     * @return
     */
    public static DialogTask getInstance(String guid) {
        DialogTask dialog = new DialogTask();
        dialog.setPresenter(new PresenterDialogTask(dialog));
        return dialog;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_task1, null, false);
        binding.tvCancel.setOnClickListener(this);
        binding.tvOk.setOnClickListener(this);
        return binding.getRoot();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // НАДО СОХРАНЯТЬСЯ ????????????????????????????
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

    }

    /**
     *
     * @param presenter
     */
    public void setPresenter(IPresenterDialogTask presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTitle(String text) {
        binding.tvTitle.setText(text);
    }

    @Override
    public void setContractor(String text) {
        binding.etContractor.setText(text);
    }

    @Override
    public void setOtvetstvenniy(String text) {
        binding.etOtvetstvenniy.setText(text);
    }

    @Override
    public void setContraler(String text) {
       binding.etContraler.setText(text);
    }

    @Override
    public void setTitleTask(String text) {
        binding.etTitleTask.setText(text);
    }

    @Override
    public void canselDialog() {
        getDialog().cancel();
        //dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvOk:

                canselDialog();

                break;

            case R.id.tvCancel:


                canselDialog();
                break;
            default:
                break;


        }
    }
}