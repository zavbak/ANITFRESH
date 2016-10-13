package ru.anit.anitfresh.ui.dialog.taskdialog;

import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.ui.dialog.fielddialog.IDataItem;
import ru.anit.anitfresh.ui.dialog.fielddialog.IGetListDataItem;

/**
 * Created by Александр on 11.10.2016.
 */

public interface IPresenterFragmentDialogTask {

    void setContractor(Contractor contractor);
    void setOtvetstvenniy(User user);
    void setKontroler(User user);


    void setTitle(String text);
    void onSetPositiveButton();
    IGetListDataItem getListContractor();
    IGetListDataItem getListUser();






}
