package backupVisitors.visitor;


import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class FullTimeStatusVisitorImpl implements TreeVisitorI{
    @Override
    public void visit(TreeBuilder tree) {
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
