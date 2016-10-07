package ru.anit.anitfresh.ui.page.catalogs;


import java.util.List;

import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.ui.general.IView;

/**
 * Created by Александр on 28.09.2016.
 */

public interface IViewPageCatalog extends IView {
    void showProgress();
    void hideProgress();
    void showTvText(String text);
    public void showList(List<Catalog> list);

}
