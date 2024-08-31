package org.ipo.web.db;


import org.ipo.model.IPOData;
import org.ipo.web.scrapper.model.IPOTableData;

import java.util.List;

public class DataTransformer {
    public IPOData convertScrapToDBData(IPOTableData data){
        IPOData IPOData =new IPOData();

        IPOData.setIpoName(data.getIpoName());
        IPOData.setPrice(Integer.parseInt(data.getPrice()));
        IPOData.setGmp(Integer.parseInt(data.getGmp()));
        IPOData.setEstListing(Integer.parseInt(data.getEstListing()));
        IPOData.setLot(Integer.parseInt(data.getLot()));
        IPOData.setOpenDt(data.getOpenDt());
        IPOData.setCloseDt(data.getCloseDt());
        IPOData.setBoaDate(data.getBoaDate());
        IPOData.setListingDate(data.getListingDate());


        return IPOData;
    }

    public List<IPOData> convertScrapToDBData(List<IPOTableData> dataList){
        return dataList.stream().map(this::convertScrapToDBData).toList();
    }

}
