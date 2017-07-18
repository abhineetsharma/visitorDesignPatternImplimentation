package backupVisitors.driver;

import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.Results;
import backupVisitors.util.TreeBuilder;
import backupVisitors.visitor.*;


/**
 * Driver
 */
public class Driver {
    class StudentInfo {
        final int id;
        final String courseName;

        StudentInfo(int idI, String courseNameI) {
            id = idI;
            courseName = courseNameI;
        }
    }

    /**
     * Process the input String
     * Return a Student Info object
     */
    private StudentInfo processString(String str) {
        StudentInfo studentInfo = null;
        try {
            String[] strArr = str.split(":");
            if (strArr.length == 2) {
                int id = Integer.parseInt(strArr[0]);
                String courseName = strArr[1];
                studentInfo = new StudentInfo(id, courseName);
            }

        } catch (NumberFormatException ex) {
            Logger.writeMessage(ex.getMessage(), INFO);
        }
        return studentInfo;
    }

    static Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    static Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    static Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    public static void main(String[] args) {
        Results results = null;
        try {
            results = new Results(null);
            results.addTextSeparator();
            results.storeNewResult("Process start...");

            if (null != args && args.length > 0 && !args[0].equalsIgnoreCase("${arg0}")) {
                if (args.length > 5 && !args[5].equalsIgnoreCase("${arg5}")) {
                    try {
                        int num = Integer.parseInt(args[5]);
                        Logger.setDebugValue(num);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
                results.storeNewResult(Logger.writeMessage("Operation Begin", INFO));
                String inputFile = args[0];
                Logger.writeMessage(String.format("Input File : %s", inputFile), INFO);
                Logger.addTextSeparator(INFO);
                FileProcessor inputFileProcessor = new FileProcessor(inputFile);

                TreeBuilder tree = new TreeBuilder();

                tree.setBackupTree1(new TreeBuilder());
                tree.setBackupTree2(new TreeBuilder());

                TreeBuilder backupTree1 = tree.getBackupTree1();
                TreeBuilder backupTree2 = tree.getBackupTree2();

                String str;

                Driver driver = new Driver();

                //insert into the tree

                results.storeNewResult(Logger.writeMessage(String.format("Insert into the tree"), INFO));
                while ((str = inputFileProcessor.readLine()) != null) {
                    StudentInfo st = driver.processString(str);
                    if (null == st)
                        continue;
                    int id = st.id;
                    String courseName = st.courseName;
                    String msg = String.format("For ID: %d Course name added : %s", id, courseName);
                    String rst = tree.insertCourseForId(id, courseName);
                    Logger.writeMessage(String.format("%s %s", msg, rst), Logger.DebugLevel.INFO);
                }
                Logger.addTextSeparator(INFO);
                results.storeNewResult(Logger.writeMessage("Delete Operation", INFO));
                if (args.length > 1 && args[1].trim().length() > 0 && !args[1].equalsIgnoreCase("${arg1}")) {
                    String deleteFile = args[1];


                    FileProcessor deleteFileProcessor = new FileProcessor(deleteFile);

                    //delete course from a tree
                    while ((str = deleteFileProcessor.readLine()) != null) {
                        StudentInfo studentInfo = driver.processString(str);
                        if (null == studentInfo) continue;
                        int id = studentInfo.id;
                        String courseName = studentInfo.courseName;
                        String st = tree.removeCourseFromNodeAndObservers(id, courseName);
                        String msg = String.format("For ID: %d Course name deleted : %s Course Enrolled: %s", id, courseName, st);
                        Logger.writeMessage(msg, INFO);
                    }
                } else {
                    results.addTextSeparator();
                    results.storeNewResult(Logger.writeMessage("Error : Delete file needed for execution", INFO));
                    results.writeToStdout();
                    System.exit(1);
                }

                Logger.addTextSeparator(INFO);

                TreeVisitorI fullTimeStatusVisitor = new FullTimeStatusVisitorImpl();
                TreeVisitorI sortedVisitor = new SortedRecordsVisitorImpl();
                TreeVisitorI identicalRecordsVisitor = new IdenticalRecordsVisitorImpl();
                TreeVisitorI statsVisitors = new StatsVisitorsImpl();

                tree.accept(fullTimeStatusVisitor, null);

                if (args.length > 2 && args[2].trim().length() > 0 && !args[2].equalsIgnoreCase("${arg2}")) {
                    String outputFile1 = args[2];
                    backupTree1.accept(statsVisitors, outputFile1);
                } else {
                    results.addTextSeparator();
                    results.storeNewResult(Logger.writeMessage("Error: Output 1 file needed for execution", INFO));
                    results.writeToStdout();
                    System.exit(1);
                }
                if (args.length > 3 && args[3].trim().length() > 0 && !args[3].equalsIgnoreCase("${arg3}")) {
                    String outputFile2 = args[3];
                    tree.accept(sortedVisitor, outputFile2);
                } else {
                    results.addTextSeparator();
                    results.storeNewResult(Logger.writeMessage("Error: Output 2 file needed for execution", INFO));
                    results.writeToStdout();
                    System.exit(1);
                }
                if (args.length > 4 && args[4].trim().length() > 0 && !args[4].equalsIgnoreCase("${arg4}")) {
                    String outputFile3 = args[4];
                    backupTree2.accept(identicalRecordsVisitor, outputFile3);
                } else {
                    results.addTextSeparator();
                    results.storeNewResult(Logger.writeMessage("Error: Output 3 file needed for execution", INFO));
                    results.writeToStdout();
                    System.exit(1);
                }
                results.storeNewResult(Logger.writeMessage("Process done, 3 output file generated", INFO));
            } else {
                results.addTextSeparator();
                results.storeNewResult(Logger.writeMessage("Error: No arguments pass, Input file needed for execution", INFO));
                results.writeToStdout();
                System.exit(1);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            Logger.writeMessage(ex.getMessage(),INFO);
        }
        finally {
            Logger.writeMessage("Process Finished", INFO);
            if (null != results)
                results.writeToStdout();
        }
    }
}

