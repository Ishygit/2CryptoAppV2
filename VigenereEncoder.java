import java.util.ArrayList;
import java.util.List;

public class VigenereEncoder implements Encoder {
   private final List<Integer> offsets; // Offsets for each keyword letter
   private int lastOffset; // Tracks the current position in the offsets

   public VigenereEncoder(String keyword) throws CAppException {
      if (keyword == null || keyword.isEmpty() || !keyword.chars().allMatch(Character::isLetter)) {
         throw new CAppException("Invalid keyword: " + keyword);
      }

      offsets = new ArrayList<>();
      for (char c : keyword.toLowerCase().toCharArray()) {
         offsets.add(c - 'a' + 1); // Convert 'a' -> 1, 'z' -> 26
      }
      lastOffset = 0; // Start from the first offset
   }

   @Override
   public String encode(String input) {
      return shiftText(input, true);
   }

   @Override
   public String decode(String input) {
      return shiftText(input, false);
   }

   private String shiftText(String input, boolean isEncoding) {
      StringBuilder result = new StringBuilder();

      for (char c : input.toCharArray()) {
         if (Character.isLetter(c)) {
            int shift = getNextOffset();
            if (!isEncoding) {
               shift = -shift; // Reverse the shift for decoding
            }

            char base = Character.isUpperCase(c) ? 'A' : 'a';
            char shifted = (char) ((c - base + shift + 26) % 26 + base); // Handle circular shift
            result.append(shifted);
         } else {
            result.append(c); // Non-letters remain unchanged
         }
      }
      return result.toString();
   }

   private int getNextOffset() {
      int offset = offsets.get(lastOffset);
      lastOffset = (lastOffset + 1) % offsets.size(); // Move to the next offset, wrap around if necessary
      return offset;
   }

   @Override
   public String toString() {
      return getClass().getSimpleName(); // Return the class name for display
   }
}
