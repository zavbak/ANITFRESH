package ru.anit.anitfresh.ui.dialog.datafielddialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.util.List;

import ru.anit.anitfresh.R;


/**
 * Created by Александр on 11.10.2016.
 */

public class FragmentDialogChoiseData extends DialogFragment {

    TextViewDelayAutoComplete actvCatalog;
    AlertDialog.Builder builder;
    View view;


    List<IDataChoice> mListDataField;
    String mTitle;
    ICallBackChoiseField callBackChoiseField;

    public FragmentDialogChoiseData() {

    }

    public static FragmentDialogChoiseData getDialoCatalog(List<IDataChoice> listDataField, String mTitle, ICallBackChoiseField callBackChoiseField) {

        FragmentDialogChoiseData dialogCatalog = new FragmentDialogChoiseData();

        dialogCatalog.setListDataField(listDataField);
        dialogCatalog.setTitle(mTitle);
        dialogCatalog.setCallBackChoiseField(callBackChoiseField);
        return dialogCatalog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_choice_metadata, null);
        actvCatalog = (TextViewDelayAutoComplete) view.findViewById(R.id.catalog_title);
        init();
        return builder.create();

    }

    void onSelection(IDataChoice dataField) {
        callBackChoiseField.onSelect(dataField);
        getDialog().cancel();
    }


    /**
     *  инициациализируем
     */
    void init() {

        builder.setTitle(mTitle)
                .setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        actvCatalog.setThreshold(2);


        actvCatalog.setAdapter(new AdapterDataFieldDialog(getActivity(), mListDataField));

        actvCatalog.setLoadingIndicator((ProgressBar) view.findViewById(R.id.progress_bar));
        actvCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                IDataChoice item = (IDataChoice) adapterView.getItemAtPosition(position);
                //actvCatalog.setText(item.getName());
                onSelection(item);
            }
        });
    }

    void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    void setListDataField(List<IDataChoice> mListDataField) {
        this.mListDataField = mListDataField;
    }



    public void setCallBackChoiseField(ICallBackChoiseField callBackChoiseField) {
        this.callBackChoiseField = callBackChoiseField;
    }
}
