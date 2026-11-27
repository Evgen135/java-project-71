package hexlet.code;

import static hexlet.code.Differ.generate;
import static hexlet.code.Differ.getData;
import static hexlet.code.Parser.parser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DifferTest {

    private String readFixture(String fileName) throws Exception {
        Path path =
                Paths.get("src/test/resources/filesExample", fileName).toAbsolutePath().normalize();
        return Files.readString(path).replace("\r\n", "\n").trim();
    }

    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml"})
    public void getDataTest(String format) throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("setting1", "Some value");
        expected.put("setting2", 200);
        expected.put("setting3", true);
        expected.put("key1", "value1");
        expected.put("numbers1", List.of(1, 2, 3, 4));
        expected.put("numbers2", List.of(2, 3, 4, 5));
        expected.put("id", 45);
        expected.put("default", null);
        expected.put("checked", false);
        expected.put("numbers3", List.of(3, 4, 5));
        expected.put("chars1", List.of("a", "b", "c"));
        expected.put("chars2", List.of("d", "e", "f"));
        Map<String, Object> valueTest = getData("src/test/resources/filesExample/file1." + format);

        assertEquals(expected, valueTest);
    }

    @Test
    public void generateTest() throws Exception {
        String valueTest =
                generate(
                        "src/test/resources/filesExample/file1.json",
                        "src/test/resources/filesExample/file2.json");
        assertEquals(readFixture("expected_stylish.txt"), valueTest);
    }

    @Test
    public void generateYamlTest() throws Exception {
        String valueTest =
                generate(
                        "src/test/resources/filesExample/file1.yaml",
                        "src/test/resources/filesExample/file2.yaml");
        assertEquals(readFixture("expected_stylish.txt"), valueTest);
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
