package ru.anit.anitfresh.ui.page.tasks.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databinding.PageTasksMainBinding;
import ru.anit.anitfresh.ui.page.general.Page;
import ru.anit.anitfresh.ui.page.tasks.lists.FragmentTasksListControl;
import ru.anit.anitfresh.ui.page.tasks.lists.FragmentTasksListDo;
import ru.anit.anitfresh.ui.page.tasks.lists.TASKS;


/**
 * Страница Задач общая
 */

public class PageTasksMain extends Page {

    PageTasksMainBinding binding;

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.page_tasks_main, container, false);
        View rootView = binding.getRoot();


        viewPager = (ViewPager) binding.pager;

        adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentTasksListDo(), TASKS.DO.getName());
        adapter.addFragment(new FragmentTasksListControl(), TASKS.CONTROL.getName());

        viewPager.setAdapter(adapter);

        tabLayout = binding.tabLayout;
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }


}
