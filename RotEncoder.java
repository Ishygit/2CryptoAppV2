public class RotEncoder implements Encoder {
   private final int shift; //constant offset for ROT13

   public RotEncoder(int shift) throws CAppException {
      if (shift <= 0){
         throw new CAppException("Bad Offset "+ shift);
      }
      this.shift = shift;
   }

   @Override
   public String encode(String input) {
      return shiftText(input);
   }

   @Override
   public String decode(String input) {
      return shiftText(input); //Decoding is the same as encoding in ROT13
   }

   private String shiftText(String input) {
      StringBuilder result = new StringBuilder();
      for (char c : input.toCharArray()) {
         if (Character.isLetter(c)) {
            char base = Character.isUpperCase(c) ? 'A' : 'a';
            int shifted = (c - base + shift + 26) % 26 + base;
            result.append((char) shifted);
         } else {
            result.append(c); //non-letters remain unchanged
         }
      }
      return result.toString();
   }

   @Override
   public String toString(){
      return getClass().getSimpleName(); // Return only the class name
   }
}