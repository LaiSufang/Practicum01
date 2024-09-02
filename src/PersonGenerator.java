import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {
    boolean doneInput = false;
    String ID = "";
    String fName = "";
    String lName = "";
    String title = "";
    String rec = "";
    int yob = 0;
    Scanner in = new Scanner(System.in);
    ArrayList<String> recs = new ArrayList<>();

    File workingDirectory = new File(System.getProperty("user.dir"));
    Path file = Paths.get(workingDirectory.getPath(),"src","PersonData.txt");

    do {
        ID = SafeInput.getNonZeroLenString(in,"Enter your ID");
        fName = SafeInput.getNonZeroLenString(in, "Enter your First Name");
        lName = SafeInput.getNonZeroLenString(in,"Enter your Last Name");
        title = SafeInput.getNonZeroLenString(in,"Enter your Title");
        yob = SafeInput.getRangedInt(in, "Enter the year of your birth", 1000, 9999);

        rec = ID + ", " + fName + ", " + lName + ", " + title + ", " + yob;

        System.out.println(rec);

        recs.add(rec);

        doneInput = SafeInput.getYNConfirm(in,"Are you finished?");
    } while (!doneInput);

    try {
        OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        for (String record : recs) {
            writer.write(record, 0, record.length());
            writer.newLine();
        }
        writer.close();
        System.out.println("Data file written!");
    }
    catch (IOException e) {
        e.printStackTrace();
    }

    }
}