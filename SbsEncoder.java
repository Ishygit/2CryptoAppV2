import java.util.HashMap;
import java.util.Map;

public class SbsEncoder implements Encoder {
   private final Map<Character, Character> encodeMap;
   private final Map<Character, Character> decodeMap;

   public SbsEncoder(String key) throws CAppException {
      encodeMap = new HashMap<>();
      decodeMap = new HashMap<>();
      initializeMaps(key);
   }

   // Private inner class to represent character pairs
   private static class CharPair {
      final char from;
      final char to;

      CharPair(char from, char to) {
         this.from = from;
         this.to = to;
      }
   }

   private void initializeMaps(String key) throws CAppException{
      String[] pairs = key.split(",");
      for (String pair : pairs) {
         if (pair.length() != 2 || !Character.isLetter(pair.charAt(0))
                 || !Character.isLetter(pair.charAt(1)))
            throw new CAppException("Bad code pair:" + pair);

         CharPair charPair = new CharPair(pair.charAt(0), pair.charAt(1));

         //map both lowercase and uppercase characters
         encodeMap.put(charPair.from, charPair.to);
         encodeMap.put(Character.toUpperCase(charPair.from),
                 Character.toUpperCase(charPair.to));

         decodeMap.put(charPair.to, charPair.from);
         decodeMap.put(Character.toUpperCase(charPair.to),
                 Character.toUpperCase(charPair.from));
      }
   }

   @Override
   public String encode(String input) {
      return substituteText(input, encodeMap);
   }

   @Override
   public String decode(String input) {
      return substituteText(input, decodeMap);
   }

   private String substituteText(String input, Map<Character, Character> map) {
      StringBuilder result = new StringBuilder();
      for (char c : input.toCharArray()) { //for-each loop for characters
         result.append(map.getOrDefault(c, c)); //default to original char if no substitute
      }
      return result.toString();
   }

   @Override
   public String toString() {
      return getClass().getSimpleName(); //return class name only
   }
}