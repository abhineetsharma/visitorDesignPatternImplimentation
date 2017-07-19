package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.MyArrayList;
import backupVisitors.util.TreeBuilder;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class StatsVisitorsImpl implements TreeVisitorI {
    private MyArrayList courseList;
    private StringBuilder printString;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    public StatsVisitorsImpl() {
        printString = new StringBuilder();
        courseList = new MyArrayList();
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("StatsVisitorsImpl Constructor: Object Crested "), CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    @Override
    public void visit(TreeBuilder tree, String outputFilePath) {
        //System.out.println("StatsVisitors");
        traverse(tree.getRoot(), tree);
        process();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.writeToFile(printString.toString(), outputFilePath);
    }

    private void traverse(Node node, TreeBuilder tree) {
        if (node != null) {
            traverse(node.getRight(), tree);
//            System.out.println("Added : "+node.getCourseList().size());
            courseList.insertSorted(node.getCourseList().size());
//            System.out.println("Size : "+courseList.size());
            courseList.toString();
            traverse(node.getLeft(), tree);
        }
    }

    private void process() {
//        Collections.sort(courseList);
        addToStoredString("Stats");
        float median = getMedian();
        String msg1 = String.format("Median : %.2f", median);
        float mean = getMean();
        String msg2 = String.format("Mean   : %.2f", mean);
        addToStoredString(msg1);
        addToStoredString(msg2);
    }

    private float getMedian() {
        int size = courseList.size();
        int index = size / 2 - 1;
        float c ;
        if (size % 2 == 0) {
            //System.out.println(courseList.get(index) + " " + courseList.get(index + 1));
            float a = (float) courseList.get(index);
            float b = (float) courseList.get(index + 1);
            float f = a + b;
            f /= 2;
            c = f;
        } else {
            c = courseList.get(index);
        }
        return c;

    }

    private float getMean() {
        return (float) courseList.sum() / (float) courseList.size();
    }

    private void addToStoredString(String str) {
        if (null == printString) {
            printString = new StringBuilder();
        }
        str = String.format("%s%s", str, "\n");
        printString.append(str);
    }
}
