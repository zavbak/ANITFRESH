package ru.anit.anitfresh.ui.page.tasks.lists;


import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.ui.general.IView;

/**
 * Created by Александр on 28.09.2016.
 */

public interface IViewFragmentTaskList extends IView {
    void showProgress();
    void hideProgress();
    void showTvText(String text);
    void showList(CashList cashList);
    void showDialogTask(Task task);
}
