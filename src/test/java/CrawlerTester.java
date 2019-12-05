import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public final class CrawlerTester {
  private Crawler crawler;

  /**
   * Test setup
   */
  public void setUp() {
    this.crawler = new Crawler("mock_dds");
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
    String actual = gson.toJson(crawlerResponse);

    try {
      JSONAssert.assertEquals(outputAsString, actual, JSONCompareMode.LENIENT);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEthics() throws IOException {
    //links with permission restrictions

    String input = readFileFromPathToString("/ethics_tests/ethicsTest1-1_input.json");
    String expected = readFileFromPathToString("/ethics_tests/ethicsTest1-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("/ethics_tests/ethicsTest1-2_input.json");
    expected = readFileFromPathToString("/ethics_tests/ethicsTest1-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //links with no permission restrictions
    input = readFileFromPathToString("/ethics_tests/ethicsTest2-1_input.json");
    expected = readFileFromPathToString("/ethics_tests/ethicsTest2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("/ethics_tests/ethicsTest2-2_input.json");
    expected = readFileFromPathToString("/ethics`_tests/ethicsTest2-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

  }

  /*
   * CRAWL-8 test
   * handles links with RPI related domains
   * handles links with non RPI related domains
   *
   */

  @Test
  public void testRelevance() throws IOException {

    //rpi related domain
    String input = readFileFromPathToString("relevance_tests/relevanceTest1-1_input.json");
    String expected = readFileFromPathToString("relevance_tests/relevanceTest1-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("relevance_tests/relevanceTest1-2_input.json");
    expected = readFileFromPathToString("relevance_tests/relevanceTest1-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //non rpi related domain
    input = readFileFromPathToString("relevance_tests/relevanceTest2-1_input.json");
    expected = readFileFromPathToString("relevance_tests/relevanceTest2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

  }

  /*
   * Tests set of links containing invalid URLs
   *
   */

  @Test
  public void testInvalidLinks () throws IOException {

    //links that lead to 404 errors
    String input = readFileFromPathToString("validity_tests/invalidLinksTest1-1_input.json");
    String expected = readFileFromPathToString("validity_tests/invalidLinksTest1-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("validity_tests/invalidLinksTest1-2_input.json");
    expected = readFileFromPathToString("validity_tests/invalidLinksTest1-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //json files with empty string as url
    input = readFileFromPathToString("validity_tests/invalidLinksTest2-1_input.json");
    expected = readFileFromPathToString("validity_tests/invalidTestLinks2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

  }

}
