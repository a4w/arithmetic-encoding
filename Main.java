import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        ArithmeticEncoding compressor = new ArithmeticEncoding("prob.dat");
        Double compressed = compressor.encode("ABCCCAABA");
        System.out.println(compressed);
        String decompressed = compressor.decode(compressed);
        System.out.println(decompressed);
        try {
            //encodeFile("prob.dat", "input.txt", "output.dat");
            decodeFile("prob.dat", "output.dat", "decoded.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encodeFile(String probFile, String inputFile, String outputFile) throws IOException {
        ArithmeticEncoding compressor = new ArithmeticEncoding(probFile);
        String input = new String(Files.readAllBytes(Paths.get(inputFile)));
        Double compressed = compressor.encode(input);
        DataOutputStream writer = new DataOutputStream(new FileOutputStream(outputFile));
        writer.writeDouble(compressed);
        writer.close();
    }

    public static void decodeFile(String probFile, String inputFile, String outputFile) throws IOException {
        ArithmeticEncoding compressor = new ArithmeticEncoding(probFile);
        DataInputStream reader = new DataInputStream(new FileInputStream(inputFile));
        Double input = reader.readDouble();
        reader.close();
        String output = compressor.decode(input);
        FileWriter writer = new FileWriter(outputFile);
        writer.write(output);
        writer.close();
    }
}