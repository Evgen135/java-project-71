package hexlet.code;

import tools.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.util.Locale;
import java.util.Map;

public class Parser {

    // читает путь к файлу и передает строку с содержимым файла
    public static Map<String, Object> parser(String content, String format) throws Exception {
        if (format.toLowerCase().equals("json")){
            ObjectMapper pareFile = new ObjectMapper();
            return pareFile.readValue(content, Map.class);
        }
        else if (format.toLowerCase().equals("yaml") || format.toLowerCase().equals("yml")){
            ObjectMapper pareFile = new YAMLMapper();
            return pareFile.readValue(content, Map.class);
        }
        else { throw new Exception("Unsupported format");}
    }
}
