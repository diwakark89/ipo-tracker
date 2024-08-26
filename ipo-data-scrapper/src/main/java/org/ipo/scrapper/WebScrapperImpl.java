package org.ipo.scrapper;

import org.ipo.scrapper.model.IPOTableData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScrapperImpl implements WebScrapper{

    private static final String WEBSITE_CURRENT_URL="https://www.investorgain.com/report/live-ipo-gmp/331/current/";
    private static final String WEBSITE_CLOSE_URL="https://www.investorgain.com/report/live-ipo-gmp/331/close/";

    @Override
    public IPOTableData tableScrap(String url) {
        try {
            // Connect to the website and get the HTML document
            // Replace with the URL you want to scrape
            Document doc = Jsoup.connect(url).get();

            // Select the table by its id, class, or any other identifier
            Element table = doc.select("table#mainTable").first(); // Adjust selector if needed (e.g., "table.myTableClass")

            // Get all rows in the table
            Elements rows = table.select("tr");

            // Iterate through each row
            for (Element row : rows) {
                // Get all cells (td elements) in the row
                Elements cells = row.select("td");

                // Iterate through each cell and print the text
                for (Element cell : cells) {
                    System.out.print(cell.text() + " \t "); // Print the cell content with a tab separation
                }
                System.out.println(); // Newline after each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private IPOTableData setDataBean(Elements elements){
        return null;
    }
}
