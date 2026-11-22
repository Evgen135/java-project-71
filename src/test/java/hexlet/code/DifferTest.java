package hexlet.code;

import static hexlet.code.Differ.generate;
import static hexlet.code.Differ.getData;
import static hexlet.code.Parser.parser;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class DifferTest {

  private final String expectedResult =
      """

      {
        - follow: false
          host: hexlet.io
        - proxy: 123.234.53.22
        - timeout: 50
        + timeout: 20
        + verbose: true
      }\
      """;

  private final Map<String, Object> expectedResultPars =
      Map.of("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22", "follow", false);

  //  @ParameterizedTest
  //  @ValueSource(valueTest = {1, 2, 3}))
  @Test
  public void getDataJsonTest() throws Exception {
    Map<String, Object> valueTest = getData("src/test/resources/filesExample/file1.json");

    assertEquals(expectedResultPars, valueTest);
  }

  @Test
  public void getDataYamiTest() throws Exception {
    Map<String, Object> valueTest = getData("src/test/resources/filesExample/file4.yaml");

    assertEquals(expectedResultPars, valueTest);
  }

  @Test
  public void generateJsonTest() throws Exception {
    String valueTest =
        generate(
            "src/test/resources/filesExample/file1.json",
            "src/test/resources/filesExample/file2.json");
    assertEquals(expectedResult, valueTest);
  }

  @Test
  public void generateYamiTest() throws Exception {
    String valueTest =
        generate(
            "src/test/resources/filesExample/file4.yaml",
            "src/test/resources/filesExample/file3.yaml");
    assertEquals(expectedResult, valueTest);
  }

  @Test
  public void UnsupportedTest() throws Exception {

    Exception exception =
        assertThrows(
            Exception.class,
            () -> {
              parser("src/test/resources/filesExample/file1", "txt");
            });
    String expectedMessage = "Unsupported format";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }
}
