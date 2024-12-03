import java.util.HashMap;
import java.util.Map;

public class SbsEncoder implements Encoder {
   private final Map<Character, Character> encodeMap; // Map for encoding
   private final Map<Character, Character> decodeMap; // Map for decoding

   public SbsEncoder(String key) throws CAppException {
      encodeMap = new HashMap<>();
      decodeMap = new HashMap<>();
      initializeMaps(key); // Set up the encoding and decoding maps
   }

   private void initializeMaps(String key) throws CAppException {
      String[] pairs = key.split(","); // Split key into pairs
      for (String pair : pairs) {
         if (pair.length() != 2 || !Character.isLetter(pair.charAt(0)) || !Character.isLetter(pair.charAt(1))) {
            throw new CAppException("Bad code pair: " + pair); // Validate each pair
         }
         encodeMap.put(pair.charAt(0), pair.charAt(1)); // Add encoding pair
         decodeMap.put(pair.charAt(1), pair.charAt(0)); // Add decoding pair
      }
   }

   @Override
   public String encode(String input) {
      return input.chars() // Stream over input characters
              .mapToObj(c -> (char) c) // Convert int to char
              .map(c -> encodeMap.getOrDefault(c, c)) // Substitute using encodeMap
              .collect(StringBuilder::new, StringBuilder::append,
                      StringBuilder::append) // Collect to StringBuilder
              .toString(); // Convert to String
   }

   @Override
   public String decode(String input) {
      return input.chars() // Stream over input characters
              .mapToObj(c -> (char) c) // Convert int to char
              .map(c -> decodeMap.getOrDefault(c, c)) // Substitute using decodeMap
              .collect(StringBuilder::new, StringBuilder::append,
                      StringBuilder::append) // Collect to StringBuilder
              .toString(); // Convert to String
   }

   @Override
   public String toString() {
      return getClass().getSimpleName(); // Return class name for display
   }
}
