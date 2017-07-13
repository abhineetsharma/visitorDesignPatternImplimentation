package backupVisitors.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;


public class Results implements StdoutDisplayInterface, FileDisplayInterface {

    public Results(String path) {
        outputPath = null;
        if(null !=path && path.trim().length() >0)
            outputPath = path;
    }

    private StringBuilder stringBuilderStorage = new StringBuilder();

    private String outputPath;

    public void storeNewResult(Object obj) {
        String str = obj.toString();
        Logger.log(str);
        str = String.format("%s%s", str, "\n");
        //System.out.println(str);
        stringBuilderStorage.append(str);
    }

    public void addTextSeprator(){
        StringBuilder sbr = new StringBuilder("\n");
        for(int i =0;i<72;i++){
            sbr.append("-");
        }
        sbr.append("\n");
        storeNewResult(sbr);
    }

    private String getStoredString() {
        return stringBuilderStorage.toString();
    }

    @Override
    public void writeToStdout() {
        System.out.println(getStoredString());
    }

    @Override
    public void writeToFile() {
        File file;
        try {
            if (null != outputPath && outputPath.trim().length() > 0) {
                file = new File(outputPath);

                if (file.exists() && !file.isDirectory()) file.delete();

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outputPath), "utf-8"))) {
                    String str = getStoredString();
                    writer.write(str);
                }
            }
            else{
                String msg = "No output file found, file either is null or a blank string";
                //storeNewResult(msg);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.log(ex.toString());
            System.out.println("Error in printing stored string into the given output file");
            System.exit(1);
        }


    }

    @Override
    public String toString() {
        String className = this.getClass().getName();
        String description = "This class has a data structure as private data member that store Strings and it implements FileDisplayInterface and StdoutDisplayInterface";
        String str = String.format("\nClass : %s\nMethod toString()\nDescription : %s\nPrivate variable :\noutputPath value is : %s\nstringBuilderStorage value is: %s\n", className, description, outputPath, getStoredString());
        System.out.println(str);
        return str;
    }
}