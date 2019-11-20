import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
   * Driver function to handle a request to crawl a list of URLs
   * Request and response bodies are specified in the Swagger specification
   *
   * @param req the JSON request, containing a list of string URLs in the body
   * @param res the JSON response to be returned
   */
  private static void handleCrawlRequest(Request req, Response res) {
    // Parse/serialize/transform the request body into a

    // Crawl the URL

    // Set the response body
  }

  /**
   * Function to call jsoup and return the raw HTML of the given url
   *
   * @param url the URL that will be crawled for its HTML
   * @return the raw HTML in a string
   */
  private String getHTML(String url){
    // Call jsoup to get the HTML
    // Return value
  }
  /**
   * Function to call jsoup and return a vector of links from the page
   *
   * @param url the URL that will be crawled for its HTML
   * @return a list of links
   */
  private List<String> getLinks(String url){
    // Call jsoup to get the links
    // Return value
  }

  /**
   * Function to facilitate pushing info to Document Data Store
   *
   * @param res the JSON response to be pushed to DDS
   */
  private void pushToDDS(Response res){

  }

  /**
   * Function to facilitate pulling info from Document Data Store
   *
   *
   */
  private void pullFromDDS(){

  }
}
