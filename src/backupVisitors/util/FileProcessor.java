package backupVisitors.util;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class FileProcessor{

    private String filePath;
    private File file;
    private BufferedReader br;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    //constructor
    public FileProcessor(String path) {
        this.filePath = path;
        getInitializedFileObject();
        Logger.writeMessage(String.format("FileProcessor Constructor: Object Crested with  file path set to : %s",filePath),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }
    public FileProcessor(){
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("FileProcessor Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    private BufferedReader getInitializedBufferedReaderObject(FileInputStream fStream){
        return new BufferedReader(new InputStreamReader(fStream));
    }

    private void getInitializedFileObject(){
        br = null;
        String path = filePath;
        file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            try {
                br = getInitializedBufferedReaderObject(new FileInputStream(file));
            } catch (IOException ex) {
                if (br != null) try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.writeMessage(e.getMessage(),FILE_PROCESSOR);
                    System.exit(1);
                }
            }
        } else {
            Logger.writeMessage("Error in reading file passed", FILE_PROCESSOR);
        }
    }

    //get the one at a time from the input file
    public String readLine() {
        if (file.exists() && !file.isDirectory()) {
            String strLine;
            try {
                if ((strLine = br.readLine()) != null) {
                        return strLine.trim();
                }
                else {
                    br.close();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in readLine, check log for information");
                System.exit(1);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String className = this.getClass().getName();
        String description = "This class has a method String readLine(...), which returns one line at a time from a file.";
        String str = String.format("Class : %s\nMethod toString()\nDescription : %s\nPrivate variable inputPath value is : %s\n",className,description,filePath);
        System.out.println(str);
        return str;
    }

    public void writeToFile(String content,String outputFilePath) {
        Logger.writeMessage(content,INFO);
        Logger.writeMessage(content,FILE_PROCESSOR);
        Logger.writeMessage(String.format("Write to file %s started",outputFilePath),FILE_PROCESSOR);
        File file;
        try {
            if (null != outputFilePath && outputFilePath.trim().length() > 0) {
                file = new File(outputFilePath);

                if (file.exists() && !file.isDirectory()) file.delete();

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outputFilePath), "utf-8"))) {
                    String str = content;
                    writer.write(str);
                }
                Logger.writeMessage(String.format("Write to file %s done",outputFilePath),FILE_PROCESSOR);
                Logger.addTextSeparator(FILE_PROCESSOR);

            } else {
                String msg = "No output file found, file either is null or a blank string";
                Logger.writeMessage(msg,FILE_PROCESSOR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in printing stored string into the given output file");
            System.exit(1);
        }
    }
}