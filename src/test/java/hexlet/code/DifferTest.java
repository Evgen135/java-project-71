package hexlet.code;

import static hexlet.code.Differ.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class DifferTest {

  @Test
  public void getDataTest() throws Exception {
    Map<String, Object> valueTest = getData("src/test/resources/filesExample/file1.json");
    Map<String, Object> expectedResult =
        Map.of("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22", "follow", false);
    assertEquals(expectedResult, valueTest);
  }

  @Test
  public void generateTest() throws Exception {
    String valueTest =
        generate(
            "src/test/resources/filesExample/file1.json",
            "src/test/resources/filesExample/file2.json");
    String expectedResult =
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
    //    System.out.println(valueTest);
    assertEquals(expectedResult, valueTest);
  }
}
