package org.ipo.web.scrapper;

import org.ipo.log.LogTracker;
import org.ipo.model.IPOTableData;
import org.ipo.web.scrapper.util.DataCleaner;
import org.ipo.web.scrapper.util.FilterData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScrapperImpl implements WebScrapper{


    private static final Logger LOG = LoggerFactory.getLogger(WebScrapperImpl.class);

    private final DataCleaner dataCleaner;
    private final FilterData filterData;

    public WebScrapperImpl(DataCleaner dataCleaner, FilterData filterData){

        this.dataCleaner = dataCleaner;
        this.filterData = filterData;
    }

    @Override
    public List<IPOTableData> tableScrap(String url, String status) {
        List<IPOTableData> dataList=new ArrayList<>();
        try {

            // Connect to the website and get the HTML document
            // Replace with the URL you want to scrape
            Document doc = Jsoup.connect(url).get();

            // Select the table by its id, class, or any other identifier
            Element table = doc.select("table#mainTable").first(); // Adjust selector if needed (e.g., "table.myTableClass")

            // Get all rows in the table
            assert table != null;
            Elements rows = table.select("tr");

            // Iterate through each row
            for (Element row : rows) {
                // Get all cells (td elements) in the row
                Elements cells = row.select("td");
                IPOTableData data=new IPOTableData();
                // Iterate through each cell and print the text
                for(int i=0 ; i<cells.size(); i++){
                   setDataBean(i, cells.get(i),data);
                }
                if(filterData.shouldItBeAdded(data)){
                    dataList.add(data);
                    data.setStatus(status);
                }
            }
        } catch (IOException ex) {
            String message = String.format("Unable to extract string due: %s", ex.getMessage());
            LogTracker.error(message);
            LOG.error(message, ex);
        }
        return dataList;
    }

    private void setDataBean(int i, Element element,IPOTableData data) {

        switch (i){
            case 0:
                data.setIpoName(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 1:
                data.setPrice(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 2:
                data.setGmp(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 3:
                data.setEstListing(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 5:
                data.setIpoSize(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 6:
                data.setLot(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 7:
                data.setOpenDt(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 8:
                data.setCloseDt(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 9:
                data.setBoaDate(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 10:
                data.setListingDate(dataCleaner.cleanData(i, new StringBuilder(element.text())));
                break;
            case 4,11:
                break;
            default:
                LOG.info("Exceed the Range fix it");

        }
    }

}
