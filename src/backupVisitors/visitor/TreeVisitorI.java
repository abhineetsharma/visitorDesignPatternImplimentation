package backupVisitors.visitor;

import backupVisitors.util.TreeBuilder;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public interface TreeVisitorI {
    void visit(TreeBuilder tree,String outputFilePath);
}
