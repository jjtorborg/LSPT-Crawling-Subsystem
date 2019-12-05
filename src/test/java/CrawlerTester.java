import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import spark.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public final class CrawlerTester {
  private CrawlerController crawlerController;
  private Crawler crawler;

  /**
   * Test setup
   */
  public void setUp() {
    this.crawlerController = new CrawlerController();
    this.crawler = new Crawler();
  }
  
  /*
   * CRAWL-6 tests
   * handles links with permission restrictons
   *
   */

  /**
   * Helper method to parse file paths and return 
   * their associated file content as string
   * @param outputFilename
   * @return file content as string
   */
  private String readFileFromPathToString(String outputFilename) {
    StringBuilder contentBuilder = new StringBuilder();
    try (Stream<String> stream = Files.lines( Paths.get(outputFilename), StandardCharsets.UTF_8)) 
    {
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
    }
    catch (IOException e) 
    {
        e.printStackTrace();
    }
    return contentBuilder.toString();
  }

  private void assertCrawledUrlResponseEquals(String url, String outputFilename) throws IOException {
    String outputAsString = FileUtils.readFileToString(FileUtils.getFile(outputFilename));

    Map<String,Object> crawlerResponse = this.crawler.crawlUrl(url);
    Gson gson = new Gson();
    gson.toJson(crawlerResponse);

    JSONAssert.assertEquals(outputAsString, actual, JSONCompareMode.LENIENT);
  }

  @Test
  public void testEthics(){
    //links with permission restrictions
    Request req;
    String res;

    res = this.crawlerController.handleCrawlRequest(req);
    String input = readFileFromPathToString("/ethicsTest1_input.json");
    String expected = readFileFromPathToString("/ethicsTest1_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links with no permission restrictions
    res = this.crawlerController.handleCrawlRequest(req);
    input = readFileFromPathToString("/ethicsTest2_input.json");
    expected = readFileFromPathToString("/ethicsTest2_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links with both no restrictions and permission restrictions
    res = this.crawlerController.handleCrawlRequest(req);
    input = readFileFromPathToString("/ethicsTest3_input.json");
    expected = readFileFromPathToString("/ethicsTest3_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }
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
    String res;

    //rpi related domain
    res = this.crawlerController.handleCrawlRequest(req);
    String expected = readFileFromPathToString("/relevanceTest1_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //non rpi related domain
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/relevanceTest2_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //both rpi and non rpi related domains
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/relevanceTest3_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }
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
    String res;

    //links needed to be recrawled
    res = this.crawlerController.handleCrawlRequest(req);
    String expected = readFileFromPathToString("/recrawlTest1_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links that do not need to be recrawled
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/recrawlTest2_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links that do not need and need to be recrawled
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/recrawlTest3_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }
  }

  /*
   * Tests set of links containing invalid URLs
   *
   */

  @Test
  public void testInvalidLinks (){
    Request req;
    String res;

    //links that lead to 404 errors
    res = this.crawlerController.handleCrawlRequest(req);
    String expected = readFileFromPathToString("/invalidLinksTest1_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links that lead to deadends
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/invalidLinksTest2_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links that lead to 404 errors and deadends
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/invalidLinksTest3_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //links that lead to 404 errors, deadends and valid links
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/invalidTest4_outputLinks.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

    //json files with empty strings as links
    res = this.crawlerController.handleCrawlRequest(req);
    expected = readFileFromPathToString("/invalidTestLinks5_output.json");
    
    try {
      JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
    } catch (JSONException e) {
      // println("CRAWLER", "Unexpected JSON exception", e);
    }

  }

}
