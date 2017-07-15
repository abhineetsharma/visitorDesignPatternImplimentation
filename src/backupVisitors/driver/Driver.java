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
            Logger.log(String.format("error in processing string %s",str));
            Logger.log(ex.getMessage());
        }
        return studentInfo;
    }

    public static void main(String[] args) {

        Results results = new Results("");
        results.storeNewResult("Process started..");
        //results.addTextSeprator();

        try {
            //results.storeNewResult("Insert Operation\n");
            if (null != args && args.length > 0) {
                String inputFile = args[0];
                Logger.log(String.format("Input File: %s", inputFile));
                FileProcessor inputFileProcessor = new FileProcessor(inputFile);

                TreeBuilder tree = new TreeBuilder();

                tree.setBackupTree1(new TreeBuilder());
                tree.setBackupTree2(new TreeBuilder());

                TreeBuilder backupTree1 = tree.getBackupTree1();
                TreeBuilder backupTree2 = tree.getBackupTree2();

                String str;

                Driver driver = new Driver();

                //insert into the tree
                while ((str = inputFileProcessor.readLine()) != null) {
                    StudentInfo st = driver.processString(str);
                    if (null == st)
                        continue;
                    int id = st.id;
                    String courseName = st.courseName;
                    String msg = String.format("For ID: %d Course name added : %s",id,courseName);
                    String rst = tree.insertCourseForId(id,courseName);
                    results.storeNewResult( msg+" "+rst);
                }
                results.addTextSeprator();
                results.storeNewResult("Delete Operation\n");
                if (args.length > 1) {
                    String deleteFile = args[1];
                    Logger.log(String.format("delete File: %s", deleteFile));
                    FileProcessor deleteFileProcessor = new FileProcessor(deleteFile);

                    //delete course from a tree
                    while ((str = deleteFileProcessor.readLine()) != null) {
                        StudentInfo studentInfo = driver.processString(str);
                        if (null == studentInfo) continue;
                        int id = studentInfo.id;
                        String courseName = studentInfo.courseName;
                        String st  =tree.removeCourseFromNodeAndObservers(id,courseName);
                        String msg = String.format("For ID: %d Course name deleted : %s Course Enrolled: %s",id,courseName, st);
                        results.storeNewResult(msg);
                    }
                } else {
                    Logger.log("Delete file needed for execution");
                    results.storeNewResult("Error : Delete file needed for execution");
                    results.writeToStdout();
                    Logger.stopLogging();
                    System.exit(1);
                }

                results.addTextSeprator();

                //Result
                Results results1 = null, results2 = null, results3 = null;
                if (args.length > 2 && args[2].trim().length() > 0) {
                    String outputFile1 = args[2];
                    Logger.log(String.format("output 1 File: %s", outputFile1));
                    results1 = new Results(outputFile1);
                }
                else {
                    Logger.log("Error : No output file 1 present");
                    results.storeNewResult("Output 1 file needed for execution");
                    results.writeToStdout();
                    Logger.stopLogging();
                    System.exit(1);
                }
                if (args.length > 3 && args[3].trim().length() > 0) {
                    String outputFile2 = args[3];
                    Logger.log(String.format("output 2 File: %s", outputFile2));
                    results2 = new Results(outputFile2);
                }
                else {
                    Logger.log("No output file 2 present");
                    results.storeNewResult("Error::Output 2 file needed for execution");
                    results.writeToStdout();
                    Logger.stopLogging();
                    System.exit(1);
                }
                if (args.length > 4 && args[4].trim().length() > 0) {
                    String outputFile3 = args[4];
                    Logger.log(String.format("output 3 File: %s", outputFile3));
                    results3 = new Results(outputFile3);
                }
                else {
                    Logger.log("No output file 3 present");
                    results.storeNewResult("Error:Output 3 file needed for execution");
                    results.writeToStdout();
                    Logger.stopLogging();
                    System.exit(1);
                }
                results.storeNewResult("Done, 3 output file generated");
                results.writeToStdout();

                TreeVisitorI fullTimeStatusVisitor = new FullTimeStatusVisitorImpl();
                TreeVisitorI sortedVisitor = new SortedRecordsVisitorImpl();
                TreeVisitorI identicalRecordsVisitor = new IdenticalRecordsVisitorImpl();
                TreeVisitorI statsVisitors = new StatsVisitorsImpl();

                tree.accept(fullTimeStatusVisitor);
                tree.accept(statsVisitors);
                backupTree1.accept(sortedVisitor);
                backupTree2.accept(identicalRecordsVisitor);

                if (null != results1) {
                    //tree.printNode(results1);
                    //results1.writeToStdout();
                    //results1.writeToFile();
                }
                if (null != results2) {
                    //tree.getBackupTree1().printNode(results2);
                    //results2.writeToStdout();
                    //results2.writeToFile();
                }
                if (null != results3) {
                    //tree.getBackupTree2().printNode(results3);
                    //results3.writeToStdout();
                    //results3.writeToFile();
                }


            } else {
                results.storeNewResult("Error: No arguments pass, Input file needed for execution");
                Logger.stopLogging();
                results.writeToStdout();
                System.exit(1);
            }
        } finally {
            Logger.stopLogging();
        }
    }
}

