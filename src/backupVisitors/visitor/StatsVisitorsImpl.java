package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.TreeBuilder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class StatsVisitorsImpl implements TreeVisitorI {
    private ArrayList <Character> courseList;
    private StringBuilder printString;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    public StatsVisitorsImpl(){
        printString = new StringBuilder();
        courseList = new ArrayList <>();
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("StatsVisitorsImpl Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    @Override
    public void visit(TreeBuilder tree,String outputFilePath) {
        //System.out.println("StatsVisitors");
        traverse(tree.getRoot(), tree);
        process();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.writeToFile(printString.toString(),outputFilePath);
    }

    private void traverse(Node node, TreeBuilder tree) {
        if (node != null) {
            traverse(node.getRight(), tree);
            for (String str : node.getCourseList()) {
                char c = str.charAt(0);
                if (c != 'S')
                    courseList.add(c);
            }
            traverse(node.getLeft(), tree);
        }
    }

    private void process() {
        Collections.sort(courseList);
//        System.out.println(courseList);
//        for(Character c : courseList){
//            System.out.print((int)c+", ");
//        }
        char median = getMedian();
        String msg1 = String.format("Median : %s", median);
        char mean = getMean();
        String msg2 = String.format("Mean : %s", mean);
        addToStoredString(msg1);
        addToStoredString(msg2);
    }

    private char getMedian() {
        int size = courseList.size();
        int index = size / 2 - 1;
        char c;
        if (size % 2 == 0) {
            //System.out.println(courseList.get(index) + " " + courseList.get(index + 1));
            float a = (float) courseList.get(index);
            float b = (float) courseList.get(index + 1);
            float f = a + b;
            f /= 2;
            c = (char) Math.ceil(f);
        } else {
            c = courseList.get(index);
        }
        return c;

    }

    private char getMean() {
        double num = 0;

        char a;
        for (char c : courseList) {
            num += (int) c;
        }
        Double f = num / courseList.size();
        a = (char) Math.ceil(f);

        return a;
    }

    private void addToStoredString(String str){
        if (null == printString) {
            printString = new StringBuilder();
        }
        str = String.format("%s%s", str, "\n");
        printString.append(str);
    }
}
