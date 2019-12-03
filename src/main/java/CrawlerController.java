import spark.Request;
import spark.Response;

public class CrawlerController {

    /**
     * Main process, which causes initialization of the Spark server and configures the PUT API
     * endpoint. Will continue handling crawl request until the process is stopped.
     *
     * @param args command-line arguments; should be empty
     */
    public static void main(String[] args) {
        // Instantiate a new crawler

        // Set up PUT endpoint for crawling URLs

    }

    /**
     * Driver function to handle a request to crawl a list of URLs Request and response bodies are
     * specified in the Swagger specification
     *
     * @param req the JSON request, containing a list of string URLs in the body
     * @param res the JSON response to be returned
     */
    private static void handleCrawlRequest(Request req, Response res) {
        // Parse/serialize/transform the request body into a
        /**
         * Main process, which causes initialization of the Spark server and configures the PUT API
         * endpoint. Will continue handling crawl request until the process is stopped.
         *
         * @param args command-line arguments; should be empty
         */
    }

    /**
     * Method initializing the infinite server loop
     * listening for calls to the predefined API
     */
    private static void initServer() {
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
        // When we wantz to know what URLs need to be recrawled,
        // make a GET request querying by recrawl time to find which
        // documents have recrawl times before the current time.
        return null;
    }
}
