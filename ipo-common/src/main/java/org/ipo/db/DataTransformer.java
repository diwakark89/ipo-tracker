package org.ipo.db;


import org.ipo.log.LogTracker;
import org.ipo.model.IPOData;
import org.ipo.model.IPOTableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.ipo.db.DBConstant.*;

public class DataTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(DataTransformer.class);

    public IPOData convertScrapToDBData(IPOTableData data) {
        IPOData.Builder builder = new IPOData.Builder();
        IPOData ipoData = null;
        try {
            ipoData = builder
                    .ipoName(data.getIpoName())
                    .price(parseIntOrDefault(data.getPrice(), 0))
                    .gmp(parseIntOrDefault(data.getGmp(), 0))
                    .estListing(parseIntOrDefault(data.getEstListing(), 0))
                    .lot(parseIntOrDefault(data.getLot(), 0))
                    .openDt(data.getOpenDt())
                    .closeDt(data.getCloseDt())
                    .boaDate(data.getBoaDate())
                    .listingDate(data.getListingDate())
                    .ipoSize(parseDoubleOrDefault(data.getIpoSize(), 0.0))
                    .ipoStatus(data.getStatus())
                    .updateDate(LocalDateTime.now().toString())
                    .listedPrice(parseDoubleOrDefault(data.getListedPrice(), 0.0))
                    .build();

            LOG.info("Data transformed: {}", ipoData);
        } catch (Exception ex) {
            String message = String.format("Exception occurred while converting: %s due to: %s", data, ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }

        return ipoData;
    }

    public List<IPOData> dbResponseTOBean(QueryResponse queryResponse) {
        List<IPOData> list = new ArrayList<>();

        queryResponse.items().forEach(item -> {
            IPOData.Builder builder = new IPOData.Builder();
            IPOData ipoData;
            try {

                ipoData = builder
                        .ipoName(item.get(IPO_NAME).s())
                        .openDt(item.get(OPEN_DT).s())
                        .closeDt(item.get(CLOSE_DT).s())
                        .boaDate(item.get(BOA_DATE).s())
                        .listingDate(item.get(LISTING_DATE).s())
                        .price(parseIntOrDefault(item.get(PRICE).n(), 0))
                        .gmp(parseIntOrDefault(item.get(GMP).n(), 0))
                        .estListing(parseIntOrDefault(item.get(EST_LISTING).n(), 0))
                        .lot(parseIntOrDefault(item.get(LOT).n(), 0))
                        .ipoSize(parseDoubleOrDefault(item.get(IPO_SIZE).n(), 0.0))
                        .ipoStatus(item.get(STATUS).s())
                        .listedPrice(parseDoubleOrDefault(item.get(LISTED_PRICE).n(), 0))
                        .updateDate(item.get(UPDATE_DATE).s())
                        .build();

                list.add(ipoData);
            } catch (Exception ex) {
                String message = String.format("Exception occurred while converting: %s due to: %s", item, ex.getMessage());
                LogTracker.error(message);
                LOG.error(message, ex);
            }
        });


        return list;
    }

    public List<IPOData> convertScrapToDBData(List<IPOTableData> dataList) {
        return dataList.stream().map(this::convertScrapToDBData).toList();
    }

    public static int parseIntOrDefault(String value, int defaultValue) {
        try {
            if (value != null) {
                return Integer.parseInt(value);
            }
            return defaultValue;

        } catch (NumberFormatException e) {
            return defaultValue; // Return default if parsing fails
        }
    }

    public static double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            if (value != null) {
                return Double.parseDouble(value);
            }
            return defaultValue;

        } catch (NumberFormatException e) {
            return defaultValue; // Return default if parsing fails
        }
    }


}
