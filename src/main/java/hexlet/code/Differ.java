package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import static hexlet.code.Parser.parser;

public class Differ {

  public static String generate(String filePath1, String filePath2) throws Exception {

    Map<String, Object> parseFile1 = getData(filePath1);
    Map<String, Object> parseFile2 = getData(filePath2);

    Set<String> sortKeys = new TreeSet<>();
    sortKeys.addAll(parseFile1.keySet());
    sortKeys.addAll(parseFile2.keySet());
    StringBuilder diff = new StringBuilder("\n{\n");
    for (String key : sortKeys) {
      boolean containkeys1 = parseFile1.containsKey(key);
      boolean containkeys2 = parseFile2.containsKey(key);

      if (containkeys1 && containkeys2) {
        if (Objects.equals(parseFile1.get(key), parseFile2.get(key))) {
          diff.append("    " + key + ": " + parseFile2.get(key) + "\n");
        } else {
          diff.append("  - " + key + ": " + parseFile1.get(key) + "\n");
          diff.append("  + " + key + ": " + parseFile2.get(key) + "\n");
        }

      } else if (containkeys1 && !containkeys2) {
        diff.append("  - " + key + ": " + parseFile1.get(key) + "\n");
      } else if (!containkeys1 && containkeys2) {
        diff.append("  + " + key + ": " + parseFile2.get(key) + "\n");
      }
    }
    diff.append("}");

    return diff.toString();
  }

  public static Map<String, Object> getData(String filePath) throws Exception {
    String content = Files.readString(Paths.get(filePath).normalize());
    int indFormat = filePath.lastIndexOf(".")+1;
    String format = filePath.substring(indFormat);

    return parser(content,format);
  }
}
