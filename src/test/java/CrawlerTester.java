import org.junit.Test;
import org.apache.commons.io.FileUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import spark.Request;
import spark.Response;

public final class CrawlerTester{

  /*
   * CRAWL-6 tests
   * handles links with permission restrictons
   *
   */

  @Test
  public void testEthics(){
    //links with permission restrictions
    handleCrawlRequest(Response req, Response res);
    String expected = FileUtils.readFileToString("/ethicsTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links with no permission restrictions
    handleCrawlRequest(Response req, Response res);
    String expected = FileUtils.readFileToString("/ethicsTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links with both no restrictions and permission restrictions
    handleCrawlRequest(Response req, Response res);
    String expected = FileUtils.readFileToString("/ethicsTest3_output.json");
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
    //rpi related domain
    handleCrawlRequest(Response req, Response res);
    String expected1 = FileUtils.readFileToString("/relevanceTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //non rpi related domain
    handleCrawlRequest(Response req, Response res);
    String expected2 = FileUtils.readFileToString("/relevanceTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //both rpi and non rpi related domains
    handleCrawlRequest(Response req, Response res);
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

    //links needed to be recrawled
    handleCrawlRequest(Response req, Response res);
    String expected1 = FileUtils.readFileToString("/recrawlTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that do not need to be recrawled
    handleCrawlRequest(Response req, Response res);
    String expected2 = FileUtils.readFileToString("/recrawlTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that do not need and need to be recrawled
    handleCrawlRequest(Response req, Response res);
    String expected3 = FileUtils.readFileToString("/recrawlTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);
  }

  /*
   * Tests set of links containing invalid URLs
   *
   */

  @Test
  public void testInvalidLinks (){
    //links that lead to 404 errors
    handleCrawlRequest(Response req, Response res);
    String expected1 = FileUtils.readFileToString("/invalidLinksTest1_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to deadends
    handleCrawlRequest(Response req, Response res);
    String expected2 = FileUtils.readFileToString("/invalidLinksTest2_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to 404 errors and deadends
    handleCrawlRequest(Response req, Response res);
    String expected3 = FileUtils.readFileToString("/invalidLinksTest3_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //links that lead to 404 errors, deadends and valid links
    handleCrawlRequest(Response req, Response res);
    String expected4 = FileUtils.readFileToString("/invalidTest4_outputLinks.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

    //json files with empty strings as links
    handleCrawlRequest(Response req, Response res);
    String expected5 = FileUtils.readFileToString("/invalidTestLinks5_output.json");
    JSONAssert.assertEquals(expected, res, JSONCompareMode.STRICT);

  }

}
