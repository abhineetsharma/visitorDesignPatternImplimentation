package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.TreeBuilder;
import java.util.TreeSet;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class SortedRecordsVisitorImpl implements TreeVisitorI {

    private StringBuilder printString;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    public SortedRecordsVisitorImpl(){
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("SortedRecordsVisitorImpl Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }
    @Override
    public void visit(TreeBuilder tree,String outputFilePath) {
        traverse(tree.getRoot());
        FileProcessor fileProcessor= new FileProcessor();
        fileProcessor.writeToFile(printString.toString(),outputFilePath);
    }

    private void traverse(Node node) {
        if (node != null) {
            traverse(node.getRight());
            String msg = String.format("Student Id : %d Courses Enrolled : %s", node.getId(), beautifyString(node.getCourseList().toString()));
            addToStoredString(msg);
            traverse(node.getLeft());
        }
    }
    private void addToStoredString(String str){
        if(null == printString){
            printString = new StringBuilder();
        }
        str = String.format("%s%s", str, "\n");
        //System.out.println(str);
        printString.append(str);
    }
    private String beautifyString(String str) {
        str = str.substring(1, str.length() - 1);
        return str;
    }

}
