import java.io.*;

public class CryptoApp {
   public static void main(String[] args) throws IOException{
      try {
         // Ensure at least 3 command-line arguments
         if (args.length < 3 || args.length > 4) {
            throw new CAppException("Usage: crypto mode algorithm key [filename]");
         }

         // Extract the command-line arguments
         String mode = args[0];        // "E" for encode, "D" for decode
         String encoderType = args[1]; // "RotEncoder" or "SbsEncoder"
         String key = args[2];         // Key for the encoder
         String filename = args.length == 4 ? args[3] : null; // Optional filename

         Encoder encoder;

         // Encoder selection based on the type passed as a command-line argument
         if (encoderType.equals("RotEncoder")) {
            int shift;
            try {
               shift = Integer.parseInt(key); // Parse the key as an integer for RotEncoder
            } catch (NumberFormatException e) {
               throw new CAppException("Bad offset " + key);
            }
            encoder = new RotEncoder(shift);
         } else if (encoderType.equals("SbsEncoder")) {
            encoder = new SbsEncoder(key);
         } else if (encoderType.equals("VigenereEncoder")) {
            encoder = new VigenereEncoder(key);
         }else {
            throw new CAppException("Invalid encoder type: " + encoderType);
         }

         // Print out the selected encoder type and mode for confirmation
         System.out.println("Selected encoder: " + encoder);
         System.out.println("Mode: " + (mode.equals("E") ? "Encoding" :
                 "Decoding"));

         // File or standard I/O handling
         if (filename != null) {
            // File-based I/O
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(filename + ".in"));
                 BufferedWriter writer = new BufferedWriter(
                         new FileWriter(filename + ".out"))) {

               String line;
               while ((line = reader.readLine()) != null) {
                  if (mode.equalsIgnoreCase("E")) {
                     writer.write(encoder.encode(line));
                  } else if (mode.equalsIgnoreCase("D")) {
                     writer.write(encoder.decode(line));
                  } else {
                     throw new CAppException("Invalid mode. Use 'E' for Encode or 'D' for Decode.");
                  }
                  writer.newLine(); // Add a newline after each encoded/decoded line
               }
            } catch (IOException e) {
               throw new CAppException("Error handling file: " +
                       filename + ".in");
            }
         } else {
            // Standard input/output
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new
                    OutputStreamWriter(System.out));

            String line;
            while ((line = in.readLine()) != null) {
               if (mode.equalsIgnoreCase("E")) {
                  out.write(encoder.encode(line));
               } else if (mode.equalsIgnoreCase("D")) {
                  out.write(encoder.decode(line));
               } else {
                  throw new CAppException("Invalid mode. Use 'E' for Encode or"
                          + " 'D' for Decode.");
               }
               out.newLine();
               out.flush();
            }
         }

      } catch (CAppException e) {
         System.err.println(e.getMessage());
      }
   }
}
