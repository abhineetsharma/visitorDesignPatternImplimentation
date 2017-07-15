package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;
import java.util.TreeSet;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class SortedRecordsVisitorImpl implements TreeVisitorI {
    @Override
    public void visit(TreeBuilder tree) {
        traverse(tree.getRoot());
    }

    private void traverse(Node node) {
        if (node != null) {
            traverse(node.getRight());
            System.out.println(node.getId() + " " + node.getCourseList());
            traverse(node.getLeft());
        }
    }
}
