package org.ipo.scrapper.scrapper;

import org.ipo.scrapper.scrapper.model.IPOTableData;

import java.util.List;

public interface WebScrapper {
    List<IPOTableData> tableScrap(String url);

}
