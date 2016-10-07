package ru.anit.anitfresh.ui.page.catalogs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.PageCatalogBinding;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.ui.page.general.Page;


/**
 * Каталог
 */

public abstract class PageCatalogAbs extends Page implements IViewPageCatalog {

    PageCatalogBinding binding;
    IPresenterPageCatalog presenter;

    private RecyclerView mRecyclerView;
    private AdapterCatalog mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.page_catalog, container, false);
        View rootView = binding.getRoot();

        mRecyclerView = binding.rvList;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }


    @Override
    public void showList(List<Catalog> list) {

        if(mAdapter==null){
            mAdapter = new AdapterCatalog();
        }
        mAdapter.changeData(list);

        //mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
