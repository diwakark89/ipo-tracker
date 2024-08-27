package org.ipo.scrapper;

import org.ipo.scrapper.model.IPOTableData;
import org.ipo.scrapper.util.DataCleaner;
import org.ipo.scrapper.util.FilterData;
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

    private static final String WEBSITE_CURRENT_URL="https://www.investorgain.com/report/live-ipo-gmp/331/current/";
    private static final String WEBSITE_CLOSE_URL="https://www.investorgain.com/report/live-ipo-gmp/331/close/";
    private static final Logger LOG = LoggerFactory.getLogger(WebScrapperImpl.class);

    private final DataCleaner dataCleaner;
    private final FilterData filterData;
    public WebScrapperImpl(DataCleaner dataCleaner, FilterData filterData){

        this.dataCleaner = dataCleaner;
        this.filterData = filterData;
    }

    @Override
    public List<IPOTableData> tableScrap(String url) {
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
                }

                LOG.info(data.toString());
                System.out.println(); // Newline after each row
            }
        } catch (IOException e) {
          LOG.error("Unable to extract string due",e);
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
            case 4:

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
            case 11:

                break;
            default:
                LOG.info("Exceed the Range fix it");

        }
    }

    public static void main(String[]args){
        DataCleaner dataCleaner=new DataCleaner();
        FilterData filterData=new FilterData();
        WebScrapper scrapper=new WebScrapperImpl(dataCleaner,filterData);
        scrapper.tableScrap(WEBSITE_CURRENT_URL);
    }
}
