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
    // Set up PUT endpoint for crawling URLs

    // Start server loop with initServer()
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
   * Driver function to handle a request to crawl a list of URLs
   * Request and response bodies are specified in the Swagger specification
   *
   * @param req the JSON request, containing a list of string URLs in the body
   * @param res the JSON response to be returned
   */
  private static void handleCrawlRequest(Request req, Response res) {
    // Parse/serialize/transform the request body into a readable string

    // Crawl the URL

    // Set the response body
  }
}
