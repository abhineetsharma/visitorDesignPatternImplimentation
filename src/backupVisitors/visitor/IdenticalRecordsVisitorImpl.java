package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.TreeBuilder;

import java.util.*;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class IdenticalRecordsVisitorImpl implements TreeVisitorI {
    private Map <String, Set <Integer>> courseRecords;
    private StringBuilder printString;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;
    public IdenticalRecordsVisitorImpl(){
        courseRecords = new HashMap <>();
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("IdenticalRecordsVisitorImpl Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    @Override
    public void visit(TreeBuilder tree,String outputFilePath) {
        traverse(tree.getRoot());
        iterateRecords();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.writeToFile(printString.toString(),outputFilePath);
    }

    private void traverse(Node node) {
        if (node != null) {
            traverse(node.getRight());
            process(node);
            traverse(node.getLeft());
        }
    }

    private void process(Node node) {
        if (null != node) {
            int id = node.getId();

            String courseEnrolled = node.getCourseList().toString();
            Object obj = courseRecords.get(courseEnrolled);
            if (null == obj) {
                SortedSet <Integer> studentsId = new TreeSet <>();
                studentsId.add(id);
                courseRecords.put(courseEnrolled, studentsId);
            } else {
                SortedSet <Integer> studentsId = (SortedSet <Integer>) obj;
                studentsId.add(id);
            }

        }
    }

    private void iterateRecords() {
        Map<String, SortedSet <Integer>> orderedMap = new TreeMap(courseRecords);
        int i=1;
        for (Map.Entry <String, SortedSet <Integer>> entry : orderedMap.entrySet()) {
            String courseEnrolled = entry.getKey();

            SortedSet <Integer> studentsEnrolled = entry.getValue();

            String str = String.format("Group : %d Course Names: %s Students Enrolled: %s%n", i++,beautifyString(courseEnrolled), beautifyString(studentsEnrolled.toString()));
            addToStoredString(str);
        }
    }

    private String beautifyString(String str) {
        str = str.substring(1, str.length() - 1);
        return str;
    }
    private void addToStoredString(String str){
        if(null == printString){
            printString = new StringBuilder();
        }
        str = String.format("%s%s", str, "\n");
        //System.out.println(str);
        printString.append(str);
    }
}
