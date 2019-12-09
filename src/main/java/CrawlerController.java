import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.TreeMap;

import static spark.Spark.*;

public class CrawlerController {
  public static String DDSPutUrl;
  private static String DDSRecrawlURL;
  private static final int maxThreads = 8;
  private static int port;

  private String recrawlURL = "lspt-TODO.cs.rpi.edu";

  /**
   * Main process, which causes initialization of the Spark server and configures the PUT API
   * endpoint. Will continue handling crawl request until the process is stopped.
   *
   * @param args command-line arguments. First argument should be the URL for DDS
   */
  public static void main(String[] args) {
    if (args.length < 3) {
      throw new IllegalArgumentException("Usage: java -jar [file] port DDSPutUrl DDSRecrawlUrl");
    }

    port = Integer.valueOf(args[0]);
    DDSPutUrl = args[1];
    DDSRecrawlURL = args[2];

    initServer();
  }

  /**
   * Driver function to handle a request to crawl a list of URLs Request and response bodies are
   * specified in the Swagger specification
   *
   * @param req the JSON request, containing a list of string URLs in the body
   * @return a string serialization of the JSON response
   */
  private static Map<String, Map<String, Object>> handleCrawlRequest(Request req) {

    Crawler c = new Crawler(DDSPutUrl);
    Gson gson = new Gson();
    String[] urls = gson.fromJson(req.body(), String[].class);

    Map<String, Map<String, Object>> linkMap = new TreeMap<>();
    for (String url : urls) {
      Map<String, Object> singlePage = c.crawlUrl(url);
      linkMap.put(url, singlePage);
    }

    return linkMap;
  }

  /**
   * Method initializing the Spark server and endpoint listening for calls to the predefined API
   * Sets up logic with handleCrawlRequest and request/response JSON structure
   */
  private static void initServer() {
    // Set up Spark server configuration
    port(port); // explicitly set default Spark port
    threadPool(maxThreads); // allow maximum of 8 threads to handle requests

    // Set up PUT endpoint for crawling URLs
    post("/crawl", (request, response) -> new Gson().toJson(handleCrawlRequest(request)));
  }

  /**
   * Function to facilitate pulling info from Document Data Store
   *
   * @param req Request that makes a GET request to DDS
   * @return a Response that is what we receive from DDS
   */
  private static Response pullFromDDS(Request req) {
    // When we want to know what URLs need to be recrawled,
    // make a GET request querying by recrawl time to find which
    // documents have recrawl times before the current time.
    Response res;
    HttpGet request = new HttpGet(DDSRecrawlURL);

    return null;
  }
}
