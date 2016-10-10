package ru.anit.anitfresh.Interators.synchronizer;


import android.content.Intent;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.services.exchangeksoap.ServisExchangeKsoap;


/**
 * Created by 79900 on 17.09.2016.
 */
public class InteratorSynhronizerNotNotifi implements IInterator {

    @Override
    public boolean execute() {

        Intent intent = new Intent(App.getContext(), ServisExchangeKsoap.class);
        App.getContext().startService(intent.putExtra(ServisExchangeKsoap.START_WITH_NOTIFI, false));

        return true;
    }
}
