package ru.anit.anitfresh.ui.mainactivity;


import ru.anit.anitfresh.ui.general.IPresenter;

/**
 *
 */

public interface IPresenterMainActivity extends IPresenter {
    void fabOnClick();
    void onNavigationItemSelected(int id);
    void onOptionsItemSelected(int id);


}
