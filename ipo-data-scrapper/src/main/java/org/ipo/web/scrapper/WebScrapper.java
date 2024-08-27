package org.ipo.web.scrapper;

import org.ipo.web.scrapper.model.IPOTableData;

import java.util.List;

public interface WebScrapper {
    List<IPOTableData> tableScrap(String url, String status);

}
