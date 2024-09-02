import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String record = "";
        ArrayList <String> records = new ArrayList<>();

        final int COLUMNS = 5;
        String id, firstName, lastName, title;
        int yob;

        try {
            File currentFolder = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(currentFolder);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                int numOfLines = 0;
                while(reader.ready()) {
                    record = reader.readLine();
                    records.add(record);
                    numOfLines++;
                    System.out.printf("\nLine %4d %-60s ", numOfLines, record);
                }
                reader.close();
                System.out.println("\n\nData file read!");

                System.out.println("ID#           Firstname     Lastname       Title    YOB");
                System.out.print("========================================================");
                for(String l : records) {
                    String[] fields = l.split(",");

                    if(fields.length == COLUMNS) {
                        id = fields[0].trim();
                        firstName = fields[1].trim();
                        lastName = fields[2].trim();
                        title = fields[3].trim();
                        yob = Integer.parseInt(fields[4].trim());
                        System.out.printf("\n%-14s%-14s%-15s%-9s%-12d", id, firstName, lastName, title, yob);
                    }
                    else {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(l);
                    }
                }
            }
            else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
