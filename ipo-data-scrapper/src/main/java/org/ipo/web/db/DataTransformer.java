package org.ipo.web.db;


import org.ipo.model.IPOData;
import org.ipo.web.scrapper.model.IPOTableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class DataTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(DataTransformer.class);

    public IPOData convertScrapToDBData(IPOTableData data) {
        IPOData ipoData = new IPOData();
        try {
            ipoData.setIpoId(UUID.randomUUID().toString());
            ipoData.setIpoName(data.getIpoName());
            ipoData.setPrice(parseIntOrDefault(data.getPrice(), 0));
            ipoData.setGmp(parseIntOrDefault(data.getGmp(), 0));
            ipoData.setEstListing(parseIntOrDefault(data.getEstListing(), 0));
            ipoData.setLot(parseIntOrDefault(data.getLot(), 0));
            ipoData.setOpenDt(data.getOpenDt());
            ipoData.setCloseDt(data.getCloseDt());
            ipoData.setBoaDate(data.getBoaDate());
            ipoData.setListingDate(data.getListingDate());
            ipoData.setIpoSize(parseDoubleOrDefault(data.getListingDate(), 0.0));
            ipoData.setStatus(data.getStatus());
            LOG.info("Data transformed: {}", ipoData);
        } catch (Exception ex) {
            LOG.error("Exception occurred for: {}: {}", data, ex.getMessage(), ex);
        }


        return ipoData;
    }

    public List<IPOData> convertScrapToDBData(List<IPOTableData> dataList) {
        return dataList.stream().map(this::convertScrapToDBData).toList();
    }

    public static int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue; // Return default if parsing fails
        }
    }

    public static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue; // Return default if parsing fails
        }
    }


}
