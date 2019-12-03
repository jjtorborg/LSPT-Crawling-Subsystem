import spark.Request;
import spark.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;

import java.io.IOException;
import java.util.*;

import java.util.List;

public class Crawler {

    private String putURL = "lspt-TODO.cs.rpi.edu";

    public Crawler() {
    }

    /**
     * Method receiving the API caller input URL and
     * constructing the properly formatted JSON to be output
     * 
     * @param url the URL that will be crawled for its HTML
     */
    public void crawlUrl(String url) {
        // Call submethods and obtain required resulting data
    }

    /**
     * Function to call jsoup and return the raw HTML of the given url
     *
     * @param url the URL that will be crawled for its HTML
     * @return the raw HTML in a string
     */
    private String getHTML(String url) {
        // Call jsoup to get the HTML
        // Return value
        String html = "";
        try {
            html = Jsoup.connect(url).get().html();
        }
        catch (Exception e){System.out.println("Unable to fetch HTML from URL");
        }
        return html;
    }

    /**
     * Function to call jsoup and return a vector of links from the page
     *
     * @param url the URL that will be crawled for its HTML
     * @return a list of links
     */
    private List<String> getLinks(String url) {
        // Call jsoup to get the links
        // Return value
        List<String> linksList = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a");
            for (Element link : links){
                linksList.add(link.attr("abs:href"));
            }
        }
        catch (Exception e){
            System.out.println("Unable to fetch links from URL");
        }
        return linksList;
    }

    /**
     * Function to facilitate pushing info to Document Data Store
     *
     * @param res the JSON response to be pushed to DDS
     */
    private void pushToDDS(Response res) {
        // If a URL is crawled that contains a doc that should not be a search result,
        // make a DELETE request to DDS which includes the crawled URL

        // If the crawled URL contains a doc that could be a search result,
        // make a PUT request to DDS containing the URL, full html document, out-links (anchors), time
        // crawled, and recrawl time.
        HttpPut request = new HttpPut(this.putURL);

    }
}
