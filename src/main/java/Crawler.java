import spark.Request;
import spark.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

import java.util.List;

public class Crawler {

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
        return "";
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
        return null;
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
    }
}
