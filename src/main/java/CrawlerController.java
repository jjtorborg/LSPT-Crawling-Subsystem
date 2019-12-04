import spark.Request;
import spark.Response;
import org.apache.http.client.methods.HttpGet;

import static spark.route.HttpMethod.get;

public class CrawlerController {


    private String recrawlURL = "lspt-TODO.cs.rpi.edu";

    /**
     * Main process, which causes initialization of the Spark server and configures the PUT API
     * endpoint. Will continue handling crawl request until the process is stopped.
     *
     * @param args command-line arguments; should be empty
     */
    public void main(String[] args) {
        // Instantiate a new crawler

        // Set up PUT endpoint for crawling URLs

    }

    /**
     * Driver function to handle a request to crawl a list of URLs Request and response bodies are
     * specified in the Swagger specification
     *
     * @param req the JSON request, containing a list of string URLs in the body
     * @return a string serialization of the JSON response
     */
    public String handleCrawlRequest(Request req) {
        return "";
    }

    /**
     * Method initializing the Spark server and endpoint
     * listening for calls to the predefined API
     * Sets up logic with handleCrawlRequest and request/response JSON structure
     */
    private void initServer() {
        // Init server loop

        // Listen for API calls (handleCrawlRequest if receive call)

        // Instantiate a new crawler if receives API call
    }

    /**
     * Function to facilitate pulling info from Document Data Store
     *
     * @param req Request that makes a GET request to DDS
     * @return a Response that is what we receive from DDS
     */
    private Response pullFromDDS(Request req) {
        // When we want to know what URLs need to be recrawled,
        // make a GET request querying by recrawl time to find which
        // documents have recrawl times before the current time.
        Response res;
        HttpGet request = new HttpGet(this.recrawlURL);

        return null;
    }
}
