package hexlet.code;

import static hexlet.code.Differ.getData;
import static hexlet.code.Formatter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FormatterTest {

    private String readFixture(String fileName) throws Exception {
        Path path =
                Paths.get("src/test/resources/filesExample", fileName).toAbsolutePath().normalize();
        return Files.readString(path).replace("\r\n", "\n").trim();
    }

    @Test
    public void stylishTest() throws Exception {
        Map<String, Object> parseFile1 = getData("src/test/resources/filesExample/file1.json");
        Map<String, Object> parseFile2 = getData("src/test/resources/filesExample/file2.json");
        String valueTest = stylishFormat(parseFile1, parseFile2);
        assertEquals(readFixture("expected_stylish.txt"), valueTest);
    }

    @Test
    public void plainTest() throws Exception {
        Map<String, Object> parseFile1 = getData("src/test/resources/filesExample/file1.json");
        Map<String, Object> parseFile2 = getData("src/test/resources/filesExample/file2.json");
        String valueTest = plainFormat(parseFile1, parseFile2);
        assertEquals(readFixture("expected_plain.txt"), valueTest);
    }

    @Test
    public void jsonTest() throws Exception {
        Map<String, Object> parseFile1 = getData("src/test/resources/filesExample/file1.json");
        Map<String, Object> parseFile2 = getData("src/test/resources/filesExample/file2.json");
        String valueTest = jsonFormat(parseFile1, parseFile2);
        assertEquals(readFixture("expected_json.txt"), valueTest);
    }
}
