import com.google.gson.Gson;
import spark.Request;
import spark.Spark.*;
import spark.Response;

import java.util.Map;
import java.util.TreeMap;

import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.threadPool;

public class CrawlerController {
    public static String DDSUrl;

    /**
     * Main process, which causes initialization of the Spark server and configures the PUT API
     * endpoint. Will continue handling crawl request until the process is stopped.
     *
     * @param args command-line arguments. First argument should be the URL for DDS
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("First command-line argument should be the URL to DDS");
        }

        DDSUrl = args[0];

        // Set up Spark server configuration
        port(4567); // explicitly set default Spark port
        int maxThreads = 8;
        threadPool(maxThreads); // allow maximum of 8 threads to handle requests

        // Set up PUT endpoint for crawling URLs
        post("/crawl",
                (request, response) -> handleCrawlRequest(request),
                JsonUtil.json());
    }

    /**
     * Driver function to handle a request to crawl a list of URLs Request and response bodies are
     * specified in the Swagger specification
     *
     * @param req the JSON request, containing a list of string URLs in the body
     * @return a string serialization of the JSON response
     */
    private static String handleCrawlRequest(Request req) {

        Crawler c = new Crawler(DDSUrl);
        Gson gson = new Gson();
        String[] urls = gson.fromJson(req.body(),String[].class);

        Map<String, Map<String,Object>> linkMap = new TreeMap<>();
        for (String url : urls) {
            Map<String, Object> singlePage = c.crawlUrl(url);
            linkMap.put(url,singlePage);
        }

        return gson.toJson(linkMap);
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
    private Request pullFromDDS(Request req) {
        // When we wantz to know what URLs need to be recrawled,
        // make a GET request querying by recrawl time to find which
        // documents have recrawl times before the current time.
        return null;
    }
}
