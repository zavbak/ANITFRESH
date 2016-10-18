package ru.anit.anitfresh.ui.dialog.fielddialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.FragmentDialogFieldBinding;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 12.10.2016.11
 */

public class FragmentDialogField extends DialogFragment implements IViewFragmentDialogField {

    FragmentDialogFieldBinding binding;

    /**
     *  где брать список
     */
    IGetListDataItem mGetListDataItem;

    /**
     *  Что делать со списком
     */
    ICallBackSelectItem mCallBackSelectItem;


    private RecyclerView mRecyclerView;
    private AdapterDataItem mAdapter;
    private LinearLayoutManager mLayoutManager;

    /**
     * Bilder
     *
     * @param mGetListDataItem
     * @return
     */
    public static FragmentDialogField getInstance(IGetListDataItem mGetListDataItem,ICallBackSelectItem callBackSelectItem) {
        FragmentDialogField dialog = new FragmentDialogField();
        dialog.setGetListDataItem(mGetListDataItem);
        dialog.setCallBackSelectItem(callBackSelectItem);
        return dialog;
    }


    public void setCallBackSelectItem(ICallBackSelectItem mCallBackSelectItem) {
        this.mCallBackSelectItem = mCallBackSelectItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_dialog_field, null, false);



        mRecyclerView = binding.rvList;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        init();

        redraw();


        Dialog dialog = getDialog();


        // открываем клавиатуру
        binding.edSearch.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return binding.getRoot();

    }

    void setGetListDataItem(IGetListDataItem GetListDataItem) {
        this.mGetListDataItem = GetListDataItem;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    /**
     * Получаем данные
     */
    void redraw() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Observable
                .create(subscriber -> {
                    subscriber.onNext(mGetListDataItem.getList());
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    showList((List<IDataItem>) list);
                    binding.progressBar.setVisibility(View.GONE);
                });
    }


    /**
     * Выводим список
     *
     * @param list
     */
    private void showList(List<IDataItem> list) {

        if (mAdapter == null) {
            mAdapter = new AdapterDataItem(this);
        }
        mAdapter.changeData(list);

        //mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.filter(binding.edSearch.getText().toString());

    }

    /**
     * Выбор позиции
     *
     * @param item
     */
    @Override
    public void onSelectionPosition(IDataItem item) {
        mCallBackSelectItem.onSelectItem(item);
        getDialog().cancel();
    }

    void init() {

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                // прячем клавиатуру
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


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

        binding.ivCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

    }



}
