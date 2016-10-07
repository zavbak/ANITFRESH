package ru.anit.anitfresh.ui.page.tasks.lists;

/**
 * Created by Александр on 28.09.2016.
 */

public class FragmentTasksListControl extends FragmentTasksListAbs {
    public FragmentTasksListControl() {
        presenter = new PresenterFragmentTasksList(TASKS.CONTROL,this);
    }
}
