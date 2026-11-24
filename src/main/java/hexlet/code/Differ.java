package hexlet.code;

import static hexlet.code.Formatter.*;
import static hexlet.code.Parser.parser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2, String format)
            throws Exception {

        String result;
        Map<String, Object> parseFile1 = getData(filePath1);
        Map<String, Object> parseFile2 = getData(filePath2);

        if (format.equals("plain")) {
            result = plainFormat(parseFile1, parseFile2);
        } else {
            result = stylishFormat(parseFile1, parseFile2);
        }

        return result;
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static Map<String, Object> getData(String filePath) throws Exception {
        String content = Files.readString(Paths.get(filePath).normalize());
        int indFormat = filePath.lastIndexOf(".") + 1;
        String format = filePath.substring(indFormat);

        return parser(content, format);
    }
}
