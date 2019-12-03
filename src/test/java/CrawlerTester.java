import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import spark.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CrawlerTester{
  private Crawler crawler;
  
  public void setUp() {
    this.crawler = new Crawler();
  }
  
  /*
   * CRAWL-6 tests
   * handles links with permission restrictons
   *
   */

  private List<String> readInputFileToListOfStrings(String inputFilename) {
    return new ArrayList<>();
  }

  private void readOutputFileToObject(String outputFilename) {

  }

  private void assertCrawledUrlResponseEquals(String url, String outputFilename) throws IOException {
    String outputAsString = FileUtils.readFileToString(FileUtils.getFile(outputFilename));

    Map<String,Object> crawlerResponse = this.crawler.crawlUrl(url1);
    Gson gson = new Gson();
    gson.toJson(crawlerResponse);

    JSONAssert.assertEquals(outputAsString, actual, JSONCompareMode.LENIENT);
  }

  @Test
  public void testEthics(){
    //links with permission restrictions
    Request req;

    this.crawlerController.handleCrawlRequest(req);
    String input = FileUtils.readFileToString("/ethicsTest1_input.json");
    String expected = FileUtils.readFileToString("/ethicsTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links with no permission restrictions
    this.crawlerController.handleCrawlRequest(req);
    input = FileUtils.readFileToString("/ethicsTest2_input.json");
    expected = FileUtils.readFileToString("/ethicsTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links with both no restrictions and permission restrictions
    this.crawlerController.handleCrawlRequest(req);
    input = FileUtils.readFileToString("/ethicsTest3_input.json");
    expected = FileUtils.readFileToString("/ethicsTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
  }

  /*
   * CRAWL-8 test
   * handles links with RPI related domains
   * handles links with non RPI related domains
   *
   */

  @Test
  public void testRelevance(){
    Request req;

    //rpi related domain
    this.crawlerController.handleCrawlRequest(req);
    String expected1 = FileUtils.readFileToString("/relevanceTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //non rpi related domain
    this.crawlerController.handleCrawlRequest(req);
    String expected2 = FileUtils.readFileToString("/relevanceTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //both rpi and non rpi related domains
    this.crawlerController.handleCrawlRequest(req);
    String expected3 = FileUtils.readFileToString("/relevanceTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
  }

  /*
   * CRAWL-23, 33
   * Tests when to recrawl links
   * Handles links with both passed call frequencies
   * and unpassed call frequencies
   *
   */

  @Test
  public void testRecrawl() {
    Request req;

    //links needed to be recrawled
    this.crawlerController.handleCrawlRequest(req);
    String expected1 = FileUtils.readFileToString("/recrawlTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that do not need to be recrawled
    this.crawlerController.handleCrawlRequest(req);
    String expected2 = FileUtils.readFileToString("/recrawlTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that do not need and need to be recrawled
    this.crawlerController.handleCrawlRequest(req);
    String expected3 = FileUtils.readFileToString("/recrawlTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
  }

  /*
   * Tests set of links containing invalid URLs
   *
   */

  @Test
  public void testInvalidLinks (){
    Request req;

    //links that lead to 404 errors
    this.crawlerController.handleCrawlRequest(req);
    String expected1 = FileUtils.readFileToString("/invalidLinksTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to deadends
    this.crawlerController.handleCrawlRequest(req);
    String expected2 = FileUtils.readFileToString("/invalidLinksTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to 404 errors and deadends
    this.crawlerController.handleCrawlRequest(req);
    String expected3 = FileUtils.readFileToString("/invalidLinksTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to 404 errors, deadends and valid links
    this.crawlerController.handleCrawlRequest(req);
    String expected4 = FileUtils.readFileToString("/invalidTest4_outputLinks.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //json files with empty strings as links
    this.crawlerController.handleCrawlRequest(req);
    String expected5 = FileUtils.readFileToString("/invalidTestLinks5_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

  }

}
