import org.eclipse.jetty.http.HttpStatus;
import org.jsoup.nodes.Document;
import spark.Response;

import java.util.List;
import java.util.Map;

public class Crawler {

    /**
     * Constructor for a Crawler that sets up a crawler instance with a specific Document Data Store
     * @param DDSUrl the URL corresponding to an instance of Document Data Store
     */
    public Crawler(String DDSUrl) {
    }

    /**
     * Method receiving the API caller input URL and constructing a Map<String, Object> that contains a list of outgoing
     * links and the HTTP status code for the given URL.  The list will not contain any links using the "mailto" URI
     * scheme, i.e. any links of the form "mailto:example@example.com". The list may contain "dead" links.
     * 
     * @param url the URL that will be crawled for its HTML
     * @return a map with two string keys: "statusCode" and "links". "statusCode" contains the integer status code
     * returned by the GET request on that URL, and "links" contains a List of Strings that are the URLs of the
     * outgoing links from the URL argument. In JSON this would be similar to:
     * {
     *     "statusCode": 200,
     *     "links": [
     *          "http://outgoinglink1.com/",
     *          "http://outgoinglink2.com/",
     *          "http://possiblydeadlink.com/"
     *     ]
     * }
     *
     */
    public Map<String,Object> crawlUrl(String url) {
        // Call submethods and obtain required resulting data
        return null;

    }

    /**
     * Function to call jsoup and return the raw HTML of the given url
     *
     * @param document the jsoup Document to get the full HTML text of
     * @return the raw HTML of the document in a string
     */
    private String getHTML(Document document) {
        // Call jsoup to get the HTML
        // Return value
        return "";
    }

    /**
     * Function to call jsoup and return a List of String links from the page
     *
     * @param document the jsoup Document to extract links from
     * @return a List of URLs (as Strings) that are in the HTML content
     */
    private List<String> getLinks(Document document) {
        // Call jsoup to get the links
        // Return value
        return null;
    }

    /**
     * Function to facilitate pushing info to Document Data Store
     *
     *
     * @param res the JSON response to be pushed to DDS
     * @return the HTTP status code returned by DDS
     */
    private int pushToDDS(Response res) {
        // If a URL is crawled that contains a doc that should not be a search result,
        // make a DELETE request to DDS which includes the crawled URL

        // If the crawled URL contains a doc that could be a search result,
        // make a PUT request to DDS containing the URL, full html document, out-links (anchors), time
        // crawled, and recrawl time.

        return 404;
    }
}
