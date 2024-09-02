import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String product = "";
        ArrayList<String> products = new ArrayList<>();

        final int COLUMNS = 4;
        String id, name, description;
        double cost;

        try {
            File currentFolder = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(currentFolder);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int numOfLines = 0;
                while (reader.ready()) {
                    product = reader.readLine();
                    products.add(product);
                    numOfLines++;
                    System.out.printf("\nLine %4d %-60s ", numOfLines, product);
                }
                reader.close();
                System.out.println("\n\nData file read");
                System.out.println("ID#           Name          Description                 Cost");
                System.out.println("==============================================================");
                for (String l : products) {
                    String[] fields = l.split(",");

                    if (fields.length == COLUMNS) {
                        id = fields[0].trim();
                        name = fields[1].trim();
                        description = fields[2].trim();
                        cost = Double.parseDouble(fields[3].trim());
                        System.out.printf("\n%-14s%-14s%-28s%-12.2f", id, name, description, cost);
                    } else {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(l);
                    }
                }
            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
