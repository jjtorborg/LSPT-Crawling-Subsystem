import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class Crawler {

  private final String putURL;
  /**
   * Constructor for a Crawler that sets up a crawler instance with a specific Document Data Store
   *
   * @param DDSUrl the URL corresponding to an instance of Document Data Store
   */
  public Crawler(String DDSUrl) {
    this.putURL = DDSUrl;
  }

  /**
   * Method receiving the API caller input URL and constructing a Map&lt;String, Object&gt; that contains
   * a list of outgoing links and the HTTP status code for the given URL. The list will not contain
   * any links using the "mailto" URI scheme, i.e. any links of the form
   * "mailto:example@example.com". The list may contain "dead" links.
   *
   * @param url the URL that will be crawled for its HTML
   * @return a map with two string keys: "statusCode" and "links". "statusCode" contains the integer
   *     status code returned by the GET request on that URL, and "links" contains a List of Strings
   *     that are the URLs of the outgoing links from the URL argument. In JSON this would be
   *     similar to: { "statusCode": 200, "links": [ "http://outgoinglink1.com/",
   *     "http://outgoinglink2.com/", "http://possiblydeadlink.com/" ] }
   */
  public Map<String, Object> crawlUrl(String url) {

    // Status is good until error occurs
    int statusCode = 200;

    // Generate jsoup document from input url
    Document doc = new Document(url);

    try {
      doc = Jsoup.connect(url).get();
    } catch (MalformedURLException e) {
      System.out.println("ERROR: MalformedURLException on " + url);
      statusCode = 404;
    } catch (HttpStatusException e) {
      statusCode = e.getStatusCode();
      System.out.println("ERROR: HttpStatusException \"" + statusCode + "\" on " + url);
    } catch (Exception e) {
      System.out.println("ERROR: Connection failed on " + url);
      statusCode = 404;
    }

    // Take a timestamp and make recrawl time 1 week in future
    Date timestamp = new Date();
    Date recrawlTime =
        new Date(
            // 1000 ms * 60 s * 60 min * 24 hr * 7 days = 1 week
            System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);

    // Get contained links
    List<String> links = getLinks(doc);

    // Initialize resulting intermediate map structure
    Map<String, Object> result = new HashMap<String, Object>();

    // Add status code and links to map
    result.put("statusCode", Integer.toString(statusCode));
    result.put("links", links);

    // Get HTML content
    String body = getHTML(doc);

    // Package all DDS required data
    Map<String, Object> outputJsonAsMap = new HashMap<String, Object>();
    List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
    Map<String, Object> documentJson = new HashMap<String, Object>();
    List<Map<String, Object>> anchors = new ArrayList<Map<String, Object>>();

    documentJson.put("url", url);
    documentJson.put("body", body);
    documentJson.put("crawledDateTime", timestamp);
    documentJson.put("recrawlDateTime", recrawlTime);
    documentJson.put("anchors", anchors);

    documents.add(documentJson);
    outputJsonAsMap.put("documents", documents);

    // Push to DDS
    Gson gsonBuilder = new GsonBuilder().create();
    String outputJson = gsonBuilder.toJson(outputJsonAsMap);

    int ddsStatusCode = pushToDDS(outputJson);
    if (ddsStatusCode < 200 || ddsStatusCode >= 300) {
      System.out.println(
          "Could not push to Document Data Store; status code " + Integer.toString(ddsStatusCode));
    }

    return result;
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
    String html = "";
    try {
      html = document.html();
    } catch (Exception e) {
      System.out.println("Unable to fetch HTML from URL");
    }
    return html;
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
    List<String> linksList = new ArrayList<>();
    try {
      Elements links = document.select("a");
      for (Element link : links) {
        linksList.add(link.attr("abs:href"));
      }
    } catch (Exception e) {
      System.out.println("Unable to fetch links from URL");
    }
    return linksList;
  }

  /**
   * Function to facilitate pushing info to Document Data Store
   *
   * @param res the JSON response to be pushed to DDS, as a string
   * @return the HTTP status code returned by DDS, or -1 if an unknown exception occurs
   */
  private int pushToDDS(String res) {
    // If a URL is crawled that contains a doc that should not be a search result,
    // make a DELETE request to DDS which includes the crawled URL

    // If the crawled URL contains a doc that could be a search result,
    // make a PUT request to DDS containing the URL, full html document, out-links (anchors), time
    // crawled, and recrawl time.

    HttpPut request = new HttpPut(this.putURL);
    StringEntity entity = new StringEntity(res, "UTF-8");
    entity.setContentType("application/json");
    request.setEntity(entity);
    try {
      final HttpResponse execute = HttpClientBuilder.create().build().execute(request);
      return execute.getStatusLine().getStatusCode();
    } catch (Exception e) {
      System.out.println("Could not push to Document Data Store at URL " + this.putURL);
      e.printStackTrace();
      return -1;
    }
  }
}
