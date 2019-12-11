import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public final class CrawlerTester {
  private static Crawler crawler = null;

  /**
   * Test setup
   */
  
  @BeforeClass
  public static void setUp() {
    crawler = new Crawler("mock_dds");
  }


  /**
   * Helper method to parse file paths and return
   * their associated file content as string
   * @param outputFilename
   * @return file content as string
   */
  private String readFileFromPathToString(String outputFilename) {
    StringBuilder contentBuilder = new StringBuilder();
    Path p = Paths.get(outputFilename);
    try (Stream<String> stream = Files.lines( p, StandardCharsets.UTF_8))
    {
      stream.forEach(s -> contentBuilder.append(s).append("\n"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return contentBuilder.toString();
    
  }

  private void assertCrawledUrlResponseEquals(String url, String expected) throws IOException {
	
    Map<String,Object> crawlerResponse = crawler.crawlUrl(url);
    Gson gson = new Gson();
    String actual = gson.toJson(crawlerResponse);
    
    try {
      JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  
  /*
   * CRAWL-6 tests
   * handles links with permission restrictions
   *
   */
  
  @Test
  public void testEthics() throws IOException {
    //links with permission restrictions
	  
    String input = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest1-1_input.json");
    String expected = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest1-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest1-2_input.json");
    expected = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest1-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //links with no permission restrictions
    input = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest2-1_input.json");
    expected = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest2-2_input.json");
    expected = readFileFromPathToString("src/test/tests/ethics_tests/ethicsTest2-2_output.json");
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
    String input = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest1-1_input.json");
    String expected = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest1-1_output.json");
    //assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest1-2_input.json");
    expected = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest1-2_output.json");
    //assertCrawledUrlResponseEquals(input, expected);

    //non rpi related domain
    input = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest2-1_input.json");
    expected = readFileFromPathToString("src/test/tests/relevance_tests/relevanceTest2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

  }

  /*
   * Tests set of links containing invalid URLs
   *
   */

 @Test
  public void testInvalidLinks () throws IOException {

    //links that lead to 404 errors
    String input = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest1-1_input.json");
    String expected = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest1-1_output.json");
    //assertCrawledUrlResponseEquals(input, expected);

    input = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest1-2_input.json");
    expected = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest1-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //json files with empty string as url
    input = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest2-1_input.json");
    expected = readFileFromPathToString("src/test/tests/validity_tests/invalidTestLinks2-1_output.json");
    assertCrawledUrlResponseEquals(input, expected);

    //garbage entered instead of links
    input = readFileFromPathToString("src/test/tests/validity_tests/invalidLinksTest2-2_input.json");
    expected = readFileFromPathToString("src/test/tests/validity_tests/invalidTestLinks2-2_output.json");
    assertCrawledUrlResponseEquals(input, expected);
  }

}
