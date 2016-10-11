package ru.anit.anitfresh.ui.dialog.taskdialog;

import org.json.JSONException;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.Interators.sendtask.InteratorSendTask;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;

/**
 * Created by Александр on 11.10.2016.
 */

public class PresenterFragmentDialogTask implements IPresenterFragmentDialogTask {

    Task mTask;

    IViewFragmentDialogTask view;

    public PresenterFragmentDialogTask(IViewFragmentDialogTask view, Task task) {
        this.view = view;

        if (task != null) {
            this.mTask = task;
        } else {
            mTask = new Task();
        }
    }


    @Override
    public void setContractor(Contractor contractor) {
        mTask.setGuidContractor(contractor.getGuid());
        view.setTextContractor(contractor.getName());
    }

    @Override
    public void setOtvetstvenniy(User user) {
        mTask.setGuidOtvetstvenniy(user.getGuid());
        view.setTextUser(user.getName());
    }

    @Override
    public void setTitle(String text) {
        mTask.setTitle(text);
    }

    @Override
    public void onSetPositiveButton() {

        //LogHelper.d(mTask.getJson().toString());
        IInterator sendTask = new InteratorSendTask(mTask);
        sendTask.execute();

    }
}
