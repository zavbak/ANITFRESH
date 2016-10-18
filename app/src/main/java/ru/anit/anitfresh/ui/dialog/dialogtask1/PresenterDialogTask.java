package ru.anit.anitfresh.ui.dialog.dialogtask1;

import ru.anit.anitfresh.metaobject.entities.Task;

/**
 * Created by 79900 on 18.10.2016.
 */

public class PresenterDialogTask implements IPresenterDialogTask {

    Task task;
    IViewDialogTask view;

    public PresenterDialogTask(Task task, IViewDialogTask view) {
        this.task = task;
        this.view = view;
    }
}
