import java.util.HashMap;
import java.util.Map;

public class SbsEncoder implements Encoder {
   private final Map<Character, Character> encodeMap = new HashMap<>();
   private final Map<Character, Character> decodeMap = new HashMap<>();

   public SbsEncoder(String key) {
      String[] pairs = key.split(",");
      for (String pair : pairs) {
         char from = pair.charAt(0);
         char to = pair.charAt(1);
         encodeMap.put(from, to);
         encodeMap.put(Character.toUpperCase(from), Character.toUpperCase(to));
         decodeMap.put(to, from);
         decodeMap.put(Character.toUpperCase(to), Character.toUpperCase(from));
      }
   }

   @Override
   public String encode(String text) {
      return transformText(text, encodeMap);
   }

   @Override
   public String decode(String text) {
      return transformText(text, decodeMap);
   }

   private String transformText(String text, Map<Character, Character> map) {
      StringBuilder result = new StringBuilder();
      for (char c : text.toCharArray()) {
         result.append(map.getOrDefault(c, c));
      }
      return result.toString();
   }
}

