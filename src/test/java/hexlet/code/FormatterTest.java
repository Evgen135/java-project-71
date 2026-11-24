package hexlet.code;

import static hexlet.code.Formatter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class FormatterTest {

    private final Map<String, Object> parseFileTest1 =
            Map.of("host", "hexlet.io", "timeout", 50, "proxy", "123.234.53.22", "follow", false);
    private final Map<String, Object> parseFileTest2 =
            Map.of("timeout", 20, "verbose", true, "host", "hexlet.io");

    private final Set<String> sortKeysTest =
            Set.of("follow", "host", "proxy", "timeout", "verbose");

    @Test
    public void stylishTest() {
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

        String valueTest = stylishFormat(parseFileTest1, parseFileTest2);
        assertEquals(expectedResult, valueTest);
    }

    @Test
    public void plainTest() {
        String expectedResult =
                """

                Property 'follow' was removed
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true
                """;

        String valueTest = plainFormat(parseFileTest1, parseFileTest2);
        assertEquals(expectedResult, valueTest);
    }
}
