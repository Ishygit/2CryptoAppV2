import java.util.Scanner;

public class CryptoApp {

   public static void main(String[] args) {
      if (args.length != 3) {
         System.err.println("Usage: java CryptoApp <E|D> <Encoder> <Key>");
         return;
      }

      String mode = args[0].toUpperCase(); // Normalize mode to upper case
      String encoderType = args[1];
      String key = args[2];

      Encoder encoder = getEncoder(encoderType, key);
      if (encoder == null) {
         System.err.println("Invalid encoder type: " + encoderType);
         return;
      }

      if (!mode.equals("E") && !mode.equals("D")) {
         System.err.println("Invalid mode. Use 'E' for Encode or 'D' for Decode.");
         return;
      }

      System.out.println("Mode: " + (mode.equals("E") ? "Encoding" : "Decoding"));

      processInput(encoder, mode);
   }

   private static Encoder getEncoder(String encoderType, String key) {
      try {
         if (encoderType.equalsIgnoreCase("RotEncoder")) {
            return new RotEncoder(Integer.parseInt(key));
         } else if (encoderType.equalsIgnoreCase("SbsEncoder")) {
            return new SbsEncoder(key);
         }
      } catch (NumberFormatException e) {
         System.err.println("Invalid key for RotEncoder. Please provide a valid integer.");
      }
      return null;
   }

   private static void processInput(Encoder encoder, String mode) {
      Scanner in = new Scanner(System.in);
      while (in.hasNextLine()) {
         String line = in.nextLine();
         System.out.println(mode.equals("E") ? encoder.encode(line) : encoder.decode(line));
      }
      in.close();
   }
}
