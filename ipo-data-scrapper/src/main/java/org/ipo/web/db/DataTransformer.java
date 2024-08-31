package org.ipo.web.db;


import org.ipo.model.IPOData;
import org.ipo.web.scrapper.model.IPOTableData;

import java.util.List;

public class DataTransformer {
    public IPOData convertScrapToDBData(IPOTableData data){
        IPOData ipoData =new IPOData();

        ipoData.setIpoName(data.getIpoName());
        ipoData.setPrice(Integer.parseInt(data.getPrice()));
        ipoData.setGmp(Integer.parseInt(data.getGmp()));
        ipoData.setEstListing(Integer.parseInt(data.getEstListing()));
        ipoData.setLot(Integer.parseInt(data.getLot()));
        ipoData.setOpenDt(data.getOpenDt());
        ipoData.setCloseDt(data.getCloseDt());
        ipoData.setBoaDate(data.getBoaDate());
        ipoData.setListingDate(data.getListingDate());


        return ipoData;
    }

    public List<IPOData> convertScrapToDBData(List<IPOTableData> dataList){
        return dataList.stream().map(this::convertScrapToDBData).toList();
    }

}
