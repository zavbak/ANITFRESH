package ru.anit.anitfresh.ui.dialog.taskdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.anit.anitfresh.R;

import ru.anit.anitfresh.metaobject.entities.Contractor;

import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;

import ru.anit.anitfresh.ui.dialog.fielddialog.FragmentDialogField;
import ru.anit.anitfresh.ui.dialog.fielddialog.ICallBackSelectItem;
import ru.anit.anitfresh.ui.dialog.fielddialog.IDataItem;


/**
 * Created by Александр on 11.10.2016.
 */

public class FragmentDialogTask extends DialogFragment implements IViewFragmentDialogTask, View.OnClickListener {

    AlertDialog.Builder builder;
    View container;

    IPresenterFragmentDialogTask presenter;

    EditText etContractor;
    EditText etOtvetstvenniy;
    EditText etKontroler;
    EditText etTitle;


    ICallBackSelectItem selectContractor = new ICallBackSelectItem() {
        @Override
        public void onSelectItem(IDataItem item) {
            presenter.setContractor((Contractor) item);
        }
    };

    ICallBackSelectItem selectOtvetstvenniy = new ICallBackSelectItem() {
        @Override
        public void onSelectItem(IDataItem item) {
            presenter.setOtvetstvenniy((User) item);
        }
    };

    ICallBackSelectItem selectKontroler = new ICallBackSelectItem() {
        @Override
        public void onSelectItem(IDataItem item) {
            presenter.setKontroler((User) item);
        }
    };




    public static FragmentDialogTask getDialogTask(Task task) {
        FragmentDialogTask dialog = new FragmentDialogTask();
        dialog.setTask(task);
        return dialog;
    }

    @Override
    public void setTask(Task task) {
        presenter = new PresenterFragmentDialogTask(this, task);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        container = inflater.inflate(R.layout.dialog_task, null);


        final TextInputLayout tilContractor = (TextInputLayout) container.findViewById(R.id.tilContractor);
        etContractor = tilContractor.getEditText();
        etContractor.setOnClickListener(this);


        final TextInputLayout tilOtvetstvenniy = (TextInputLayout) container.findViewById(R.id.tilOtvetstvenniy);
        etOtvetstvenniy = tilOtvetstvenniy.getEditText();
        etOtvetstvenniy.setOnClickListener(this);


        final TextInputLayout tilKontroler = (TextInputLayout) container.findViewById(R.id.tilKontroler);
        etKontroler = tilKontroler.getEditText();
        etKontroler.setOnClickListener(this);



        final TextInputLayout tillTitle = (TextInputLayout) container.findViewById(R.id.tilTitle);
        etTitle = tillTitle.getEditText();




        init();
        return builder.create();
    }


    /**
     * инициациализируем
     */
    void init() {

        builder.setTitle("Задача")
                .setView(container)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setTitle(etTitle.getText().toString());
                        presenter.onSetPositiveButton();
                        dialog.cancel();
                    }
                });

    }


    @Override
    public void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ;

        switch (view.getId()) {

            case R.id.etContractor:

                FragmentDialogField dialogContractor = FragmentDialogField.getInstance(presenter.getListContractor(), selectContractor);
                dialogContractor.show(fm, "tag_dialog_contractor");
                break;

            case R.id.etOtvetstvenniy:

                FragmentDialogField dialogOtvetstvenniy = FragmentDialogField.getInstance(presenter.getListUser(), selectOtvetstvenniy);
                dialogOtvetstvenniy.show(fm, "tag_dialog_contractor");
                break;

            case R.id.etKontroler:

                FragmentDialogField dialogKontroler = FragmentDialogField.getInstance(presenter.getListUser(), selectKontroler);
                dialogKontroler.show(fm, "tag_dialog_contractor");
                break;


            default:
                break;


        }
    }

    @Override
    public void setTextContractor(String text) {
        etContractor.setText(text);

    }

    @Override
    public void setTextUser(String text) {
        etOtvetstvenniy.setText(text);
    }

    @Override
    public void setTextKontroler(String text) {
        etKontroler.setText(text);
    }


}
