package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class StatsVisitorsImpl implements TreeVisitorI {
    @Override
    public void visit(TreeBuilder tree) {

    }
    public void traverse(Node node, TreeBuilder tree) {
        if (node != null) {
            traverse( node.getRight(),tree);

            traverse( node.getLeft(),tree);


        }
    }
}
