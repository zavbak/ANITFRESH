package ru.anit.anitfresh.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.Interators.puttoken.InteratorPutToken;
import ru.anit.anitfresh.Interators.synchronizer.InteratorSynhronizerNotNotifi;
import ru.anit.anitfresh.R;
import ru.anit.anitfresh.databus.EventBusMessageOnError;
import ru.anit.anitfresh.databus.EventBusMessageOnInform;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.ui.page.catalogs.PageCatalogContractors;
import ru.anit.anitfresh.ui.page.catalogs.PageCatalogUsers;
import ru.anit.anitfresh.ui.page.tasks.main.PageTasksMain;
import ru.anit.anitfresh.ui.settingactivity.PrefActivity;

/**
 *
 */

public class PresenterMainActivity implements IPresenterMainActivity {

    IViewMainActivity view;

    /**
     * page
     */
    STATE_PAGE statePage;


    // constructor

    public PresenterMainActivity(IViewMainActivity view) {
        this.view = view;
    }

    /**
     * установим фрагмент
     */
    protected void changePage() {
        switch (statePage) {

            case TASKS:
                new InteratorSynhronizerNotNotifi().execute();
                view.setPage(new PageTasksMain());
                break;
            case USERS:
                view.setPage(new PageCatalogUsers());
                break;
            case CONTRACTORS:
                view.setPage(new PageCatalogContractors());
                break;
        }

    }


    //  getters and setters

    public STATE_PAGE getStatePage() {
        return statePage;
    }

    public void setStatePage(STATE_PAGE statePage) {
        this.statePage = statePage;
        changePage();
        view.changeTitle(statePage.getPageName());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageOnInform event) {
        view.snackbarShow(event.getMessageText());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageOnError event) {
        view.snackbarShow(event.getMessageText());
    }

    //  Override

    // presenter

    @Override
    public void fabOnClick() {
        view.startNewTask();
    }

    @Override
    public void onNavigationItemSelected(int id) {

        if (id == R.id.nav_tasks) {
            setStatePage(STATE_PAGE.TASKS);
        } else if (id == R.id.nav_users) {

            setStatePage(STATE_PAGE.USERS);

        } else if (id == R.id.nav_contractors) {

            setStatePage(STATE_PAGE.CONTRACTORS);

        } else if (id == R.id.nav_synchronizer) {

            new InteratorSynhronizerNotNotifi().execute();

        } else if (id == R.id.nav_settings) {

            view.getActivity().startActivity(new Intent(view.getActivity(), PrefActivity.class));

        }else if (id == R.id.nav_test_reg1c) {

            IInterator interator = new InteratorPutToken();
            interator.execute();
        }else if(id == R.id.nav_test_crash) {

            FirebaseCrash.logcat(Log.DEBUG, LogHelper.LOG_TAG,"Test log");
            FirebaseCrash.report(new Exception("Test Error"));

        }else if(id == R.id.nav_test_task){
            
        }

    }

    @Override
    public void onOptionsItemSelected(int id) {
        view.snackbarShow("" + id);

        if (id == R.id.action_settings) {
            // return true;
        }
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            setStatePage(STATE_PAGE.getState(savedInstanceState.getInt(STATE_PAGE.class.toString())));
        }else{
            setStatePage(STATE_PAGE.TASKS);
        }
    }

    @Override
    public void saveState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_PAGE.class.toString(), statePage.getId());
    }


}