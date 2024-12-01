public class RotEncoder implements Encoder {
   private final int shift;

   public RotEncoder(int shift) {
      this.shift = shift;
   }

   @Override
   public String encode(String text) {
      return shiftText(text, shift);
   }

   @Override
   public String decode(String text) {
      return shiftText(text, -shift);
   }

   private String shiftText(String text, int shift) {
      StringBuilder result = new StringBuilder();
      for (char c : text.toCharArray()) {
         if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            result.append((char) ((c - base + shift + 26) % 26 + base));
         } else {
            result.append(c);
         }
      }
      return result.toString();
   }
}
