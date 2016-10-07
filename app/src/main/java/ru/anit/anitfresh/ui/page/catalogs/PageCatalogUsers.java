package ru.anit.anitfresh.ui.page.catalogs;

/**
 * Created by Александр on 28.09.2016.
 */

public class PageCatalogUsers extends PageCatalogAbs {

    public PageCatalogUsers() {

        presenter = new PresenterPageCatalog(CATALOGS.USERS,this);
    }
}
