package ru.anit.anitfresh.ui.dialog.taskdialog;

import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.User;

/**
 * Created by Александр on 11.10.2016.
 */

public interface IPresenterFragmentDialogTask {

    void setContractor(Contractor contractor);
    void setOtvetstvenniy(User user);
    void setTitle(String text);
    void onSetPositiveButton();


}
