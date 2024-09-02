import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        boolean doneInput = false;
        String id = "";
        String name = "";
        String description = "";
        double cost = 0;

        Scanner in = new Scanner(System.in);
        ArrayList<String> products = new ArrayList<>();
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), "src", "ProductTestData.txt");

        do {
            id = SafeInput.getNonZeroLenString(in, "Enter the product ID");
            name = SafeInput.getNonZeroLenString(in, "Enter the product name");
            description = SafeInput.getNonZeroLenString(in, "Enter a short description of the product");
            cost = SafeInput.getDouble(in, "Enter the price of the product");

            String product = id + ", " + name + ", " + description + ", " + cost;
            products.add(product);
            doneInput = SafeInput.getYNConfirm(in, "Are you done?");
        } while (!doneInput);

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String product : products) {
                writer.write(product, 0, product.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Data file written");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
