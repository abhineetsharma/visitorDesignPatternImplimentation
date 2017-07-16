package backupVisitors.visitor;


import backupVisitors.myTree.Node;
import backupVisitors.util.FileProcessor;
import backupVisitors.util.Logger;
import backupVisitors.util.TreeBuilder;



/**
 * Created by abhineetsharma on 7/12/17.
 */
public class FullTimeStatusVisitorImpl implements TreeVisitorI{

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    public FullTimeStatusVisitorImpl(){
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("FullTimeStatusVisitorImpl Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }
    @Override
    public void visit(TreeBuilder tree,String outputFilPath) {
        Node root = tree.getRoot();
        traverse(root,tree);
    }
    public void traverse( Node node,TreeBuilder tree) {
        if (node != null) {
            traverse( node.getLeft(),tree);
            if(null != node.getCourseList() && !node.getCourseList().contains("S") && node.getCourseList().size()<3){
                tree.insertCourseForId(node.getId(),"S");
            }
            traverse( node.getRight(),tree);
        }
    }

}
