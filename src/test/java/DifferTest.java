import hexlet.code.Differ;
import org.junit.jupiter.api.Test;
import static hexlet.code.Differ.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public  void parseTest() throws Exception {
        var parser = parse("filesExample/file1.json");
        System.out.println(ff);
    }

    @Test
    public  void generateTest() throws Exception {
        String valueTest = generate("filesExample/file1.json", "filesExample/file2.json");
        String resultTes = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertEquals(resultTes,valueTest);
    }
}
