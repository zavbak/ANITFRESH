package ru.anit.anitfresh.ui.general;

import android.os.Bundle;

/**
 *
 */


public interface IPresenter {

    void onStart();
    void onStop();
    void loadState(Bundle savedInstanceState);
    void saveState(Bundle savedInstanceState);


}
