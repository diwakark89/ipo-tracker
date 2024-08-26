package org.ipo.scrapper;

import org.ipo.scrapper.model.IPOTableData;

public interface WebScrapper {
    IPOTableData tableScrap(String url);

}
