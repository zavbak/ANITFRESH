package ru.anit.anitfresh.ui.dialog.taskdialog;

import ru.anit.anitfresh.metaobject.entities.Task;

/**
 * Created by Александр on 11.10.2016.
 */

public interface IViewFragmentDialogTask {

    void setTextContractor(String text);
    void setTextUser(String text);
    void setTask(Task task);
}
