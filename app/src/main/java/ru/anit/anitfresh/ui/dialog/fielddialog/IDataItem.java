package ru.anit.anitfresh.ui.dialog.fielddialog;

/**
 * Created by Александр on 12.10.2016.
 */

public interface IDataItem {

    String getName();
    Boolean filter(String strSearch);

}
