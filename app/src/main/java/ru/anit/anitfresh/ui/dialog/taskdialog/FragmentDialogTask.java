package ru.anit.anitfresh.ui.dialog.taskdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import ru.anit.anitfresh.ui.dialog.datafielddialog.FragmentDialogChoiseData;
import ru.anit.anitfresh.ui.dialog.datafielddialog.ICallBackChoiseField;
import ru.anit.anitfresh.ui.dialog.datafielddialog.IDataChoice;


/**
 * Created by Александр on 11.10.2016.
 */

public class FragmentDialogTask extends DialogFragment implements IViewFragmentDialogTask, View.OnClickListener {


    AlertDialog.Builder builder;
    View container;

    IPresenterFragmentDialogTask presenter;


    EditText etContractor;
    EditText etOtvetstvenniy;
    EditText etTitle;

    ICallBackChoiseField selectContractor = new ICallBackChoiseField() {
        @Override
        public void onSelect(IDataChoice dataField) {
             presenter.setContractor((Contractor) dataField);
        }
    };

    ICallBackChoiseField selecttilOtvetstvenniy = new ICallBackChoiseField() {
        @Override
        public void onSelect(IDataChoice dataField) {
            presenter.setOtvetstvenniy((User) dataField);
        }
    };


    public static FragmentDialogTask getDialogTask(Task task) {
        FragmentDialogTask dialog = new FragmentDialogTask();
        dialog.setTask(task);
        return dialog;
    }

    @Override
    public void setTask(Task task) {
        presenter = new PresenterFragmentDialogTask(this,task);
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


    List<IDataChoice> getListDialog(TYPE_ENTITIES type){


        Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                type.getNameFromBase()}, null);

        List<IDataChoice> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())

        {
            do {

                ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                IDataChoice item = (IDataChoice) BuilderHelper.getObject(objectSyncTable);

                list.add(item);


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }


        return list;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();;

        switch (view.getId()){

            case R.id.etContractor:


                FragmentDialogChoiseData dialogContractor = FragmentDialogChoiseData.getDialoCatalog(getListDialog(TYPE_ENTITIES.CONTRACTOR), "Выбрать Ответственного:", selectContractor);
                dialogContractor.show(fm, "tag_dialog_contractor");

                break;

            case R.id.etOtvetstvenniy:

                FragmentDialogChoiseData dialogOtvetstvenniy = FragmentDialogChoiseData.getDialoCatalog(getListDialog(TYPE_ENTITIES.USER), "Выбрать Ответственного:", selecttilOtvetstvenniy);
                dialogOtvetstvenniy.show(fm, "tag_dialog_otvetstvenniy");

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


}
