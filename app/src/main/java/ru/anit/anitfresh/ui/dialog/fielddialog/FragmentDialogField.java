package ru.anit.anitfresh.ui.dialog.fielddialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.databinding.FragmentDialogFieldBinding;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import ru.anit.anitfresh.ui.dialog.datafielddialog.TextViewDelayAutoComplete;
import ru.anit.anitfresh.ui.page.catalogs.AdapterCatalog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 12.10.2016.
 */

public class FragmentDialogField extends DialogFragment implements IViewFragmentDialogField {


    FragmentDialogFieldBinding binding;

    private RecyclerView mRecyclerView;
    private AdapterDataItem mAdapter;
    private LinearLayoutManager mLayoutManager;

    public static FragmentDialogField getInstance() {
        FragmentDialogField dialog = new FragmentDialogField();
        return dialog;
    }


    private List<IDataItem> getList() {

        Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                TYPE_ENTITIES.CONTRACTOR.getNameFromBase()}, null);

        List<IDataItem> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())

        {
            do {

                ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                IDataItem item = (IDataItem) BuilderHelper.getObject(objectSyncTable);

                list.add(item);


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return list;

    }


    void redraw() {

        binding.progressBar.setVisibility(View.VISIBLE);

        Observable
                .create(subscriber -> {
                    subscriber.onNext(getList());
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    showList((List<IDataItem>) list);
                    binding.progressBar.setVisibility(View.GONE);
                });

    }

    private void showList(List<IDataItem> list) {

        if (mAdapter == null) {
            mAdapter = new AdapterDataItem(this);
        }
        mAdapter.changeData(list);

        //mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.filter(binding.edSearch.getText().toString());

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_dialog_field, null, false);

        mRecyclerView = binding.rvList;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //Toast.makeText(getActivity(),"onInterceptTouchEvent", Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Toast.makeText(getActivity(),"onTouchEvent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                Toast.makeText(getActivity(),"onRequestDisallowInterceptTouchEvent", Toast.LENGTH_SHORT).show();
            }
        });


        //binding.edSearch.requestFocus();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);



        binding.edSearch.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        binding.edSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (mAdapter != null) {
                    mAdapter.filter(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        redraw();



        return new AlertDialog.Builder(getActivity())
                .setView(binding.getRoot())
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .create();

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onSelectionPosition(IDataItem item) {
        Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
    }
}
