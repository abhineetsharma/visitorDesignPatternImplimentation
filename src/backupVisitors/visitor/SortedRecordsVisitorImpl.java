package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class SortedRecordsVisitorImpl implements TreeVisitorI {
    @Override
    public void visit(TreeBuilder tree) {
        traverse(tree.getRoot(),tree);
    }

    public void traverse(Node node, TreeBuilder tree) {
        if (node != null) {
            traverse( node.getRight(),tree);

            System.out.println(node.getId()+ " "+node.getCourseList());
            traverse( node.getLeft(),tree);


        }
    }
}
