package ru.anit.anitfresh.ui.page.catalogs;

/**
 * Created by Александр on 28.09.2016.
 */

public class PageCatalogContractors extends PageCatalogAbs {

    public PageCatalogContractors() {
        presenter = new PresenterPageCatalog(CATALOGS.CONTRACTORS,this);

    }
}
