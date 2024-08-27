package org.ipo.scrapper;

import org.ipo.scrapper.model.IPOTableData;

import java.util.List;

public interface WebScrapper {
    List<IPOTableData> tableScrap(String url);

}
