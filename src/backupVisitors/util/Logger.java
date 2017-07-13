package backupVisitors.util;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.File;
import java.io.BufferedWriter;
import java.time.LocalDateTime;

public class Logger {
    private static File file;
    private static Writer writer;
    private static String logFilePath;

    private Logger() {


    }

    public static void stopLogging() {

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Error in stopping logger string into the output file");
            e.printStackTrace();
            System.exit(1);
        }


    }

    public static void log(Object obj) {
        if (null == writer) {
            try {
                logFilePath = String.format("%s/%s", System.getProperty("user.dir"), "log.txt");
                file = new File(logFilePath);
                if (file.exists() && !file.isDirectory()) file.delete();
                writer = new BufferedWriter(new FileWriter(logFilePath, true));

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        String str =  "\n"+obj.toString();

        str = str.replaceAll("[\\n]", "\n" + "[" + LocalDateTime.now().toString() + "] ");

        writeToFile(str);
    }

    private static void writeToFile(String str) {


        try {

            writer.append(str);

        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }


}