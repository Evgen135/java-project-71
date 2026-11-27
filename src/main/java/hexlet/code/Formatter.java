package hexlet.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import tools.jackson.databind.*;
import tools.jackson.databind.ObjectMapper;

public class Formatter {

    public static Set<String> sortKeys(
            Map<String, Object> parseFile1, Map<String, Object> parseFile2) {
        Set<String> sortKeys = new TreeSet<>();
        sortKeys.addAll(parseFile1.keySet());
        sortKeys.addAll(parseFile2.keySet());
        return sortKeys;
    }

    public static String comparisonFils(
            String key, Map<String, Object> parseFile1, Map<String, Object> parseFile2) {
        boolean containkeys1 = parseFile1.containsKey(key);
        boolean containkeys2 = parseFile2.containsKey(key);

        if (containkeys1 && containkeys2) {
            return Objects.equals(parseFile1.get(key), parseFile2.get(key))
                    ? "unchanged"
                    : "changed";
        } else if (containkeys1 && !containkeys2) {
            return "removed";
        } else {
            return "added";
        }
    }

    public static String valueProcessing(Object value) {
        if (value instanceof Map<?, ?>
                || value instanceof Collection<?>
                || (value != null && value.getClass().isArray())) {
            return "[complex value]";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }

    public static String stylishFormat(
            Map<String, Object> parseFile1, Map<String, Object> parseFile2) {

        StringBuilder diffstylish = new StringBuilder("{\n");
        Set<String> sortKeys = sortKeys(parseFile1, parseFile2);
        for (String key : sortKeys) {
            switch (comparisonFils(key, parseFile1, parseFile2)) {
                case "unchanged" ->
                        diffstylish.append("    " + key + ": " + parseFile2.get(key) + "\n");
                case "changed" -> {
                    diffstylish.append("  - " + key + ": " + parseFile1.get(key) + "\n");
                    diffstylish.append("  + " + key + ": " + parseFile2.get(key) + "\n");
                }
                case "removed" ->
                        diffstylish.append("  - " + key + ": " + parseFile1.get(key) + "\n");
                case "added" ->
                        diffstylish.append("  + " + key + ": " + parseFile2.get(key) + "\n");
            }
        }

        diffstylish.append("}");
        return diffstylish.toString();
    }

    public static String plainFormat(
            Map<String, Object> parseFile1, Map<String, Object> parseFile2) {
        StringBuilder diffstylish = new StringBuilder("\n");
        Set<String> sortKeys = sortKeys(parseFile1, parseFile2);
        for (String key : sortKeys) {

            switch (comparisonFils(key, parseFile1, parseFile2)) {
                case "changed" -> {
                    diffstylish.append(
                            "Property '"
                                    + key
                                    + "' was updated. "
                                    + "From "
                                    + valueProcessing(parseFile1.get(key))
                                    + " to "
                                    + valueProcessing(parseFile2.get(key))
                                    + "\n");
                }

                case "removed" -> diffstylish.append("Property '" + key + "' was removed\n");
                case "added" ->
                        diffstylish.append(
                                "Property '"
                                        + key
                                        + "' was added with value: "
                                        + valueProcessing(parseFile2.get(key))
                                        + "\n");
            }
        }
        return diffstylish.toString().trim();
    }

    public static String jsonFormat(Map<String, Object> parseFile1, Map<String, Object> parseFile2)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> jsonList = new ArrayList<>();
        Set<String> sortKeys = sortKeys(parseFile1, parseFile2);
        for (String key : sortKeys) {
            String status = comparisonFils(key, parseFile1, parseFile2);
            Object value1 = parseFile1.get(key);
            Object value2 = parseFile2.get(key);
            Map<String, Object> diffEntry = new LinkedHashMap<>();
            diffEntry.put("status", status);
            diffEntry.put("key", key);

            switch (status) {
                case "unchanged" -> diffEntry.put("value", value2);
                case "changed" -> {
                    diffEntry.put("valueOld", value1);
                    diffEntry.put("valueNew", value2);
                }
                case "removed" -> diffEntry.put("value", value1);
                case "added" -> diffEntry.put("value", value2);
            }
            jsonList.add(diffEntry);
        }

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonList).trim();
    }
}
