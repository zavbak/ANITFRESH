package ru.anit.anitfresh.ui.page.tasks.lists;

/**
 * Created by Александр on 28.09.2016.
 */

public class FragmentTasksListDo extends FragmentTasksListAbs {

    public FragmentTasksListDo() {
        presenter = new PresenterFragmentTasksList(TASKS.DO,this);
    }
}
