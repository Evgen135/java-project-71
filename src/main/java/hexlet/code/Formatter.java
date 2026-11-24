package hexlet.code;

import java.util.*;

public class Formatter {

    public static Set<String> sortKeys(
            Map<String, Object> parseFile1, Map<String, Object> parseFile2) {
        Set<String> sortKeys = new TreeSet<>();
        sortKeys.addAll(parseFile1.keySet());
        sortKeys.addAll(parseFile2.keySet());
        return sortKeys;
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

        StringBuilder diffstylish = new StringBuilder("\n{\n");
        Set<String> sortKeys = sortKeys(parseFile1, parseFile2);
        for (String key : sortKeys) {
            boolean containkeys1 = parseFile1.containsKey(key);
            boolean containkeys2 = parseFile2.containsKey(key);

            if (containkeys1 && containkeys2) {
                if (Objects.equals(parseFile1.get(key), parseFile2.get(key))) {
                    diffstylish.append("    " + key + ": " + parseFile2.get(key) + "\n");
                } else {
                    diffstylish.append("  - " + key + ": " + parseFile1.get(key) + "\n");
                    diffstylish.append("  + " + key + ": " + parseFile2.get(key) + "\n");
                }

            } else if (containkeys1 && !containkeys2) {
                diffstylish.append("  - " + key + ": " + parseFile1.get(key) + "\n");
            } else if (!containkeys1 && containkeys2) {
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
            boolean containkeys1 = parseFile1.containsKey(key);
            boolean containkeys2 = parseFile2.containsKey(key);

            if (containkeys1 && containkeys2) {
                if (!Objects.equals(parseFile1.get(key), parseFile2.get(key))) {
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
            } else if (!containkeys1 && containkeys2) {
                diffstylish.append(
                        "Property '"
                                + key
                                + "' was added with value: "
                                + valueProcessing(parseFile2.get(key))
                                + "\n");
            } else if (containkeys1 && !containkeys2) {
                diffstylish.append("Property '" + key + "' was removed\n");
            }
        }
        return diffstylish.toString();
    }
}
