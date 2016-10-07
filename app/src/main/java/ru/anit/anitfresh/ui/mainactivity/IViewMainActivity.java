package ru.anit.anitfresh.ui.mainactivity;

import android.app.Activity;

import ru.anit.anitfresh.ui.general.IView;
import ru.anit.anitfresh.ui.page.general.Page;


/**
 * Created by Александр on 28.09.2016.
 */

public interface IViewMainActivity extends IView {
    void snackbarShow(String message);
    void setPage(Page page);
    void changeTitle(String str);
    Activity getActivity();


}
