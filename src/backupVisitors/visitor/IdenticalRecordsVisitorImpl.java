package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class IdenticalRecordsVisitorImpl implements TreeVisitorI {
    HashMap <String, Set<Integer>> courseRecords;

    @Override
    public void visit(TreeBuilder tree) {
        courseRecords = new HashMap <>();
        traverse(tree.getRoot());
        iterateRecords();
    }

    public void traverse(Node node) {
        if (node != null) {
            traverse(node.getRight());
            process(node);
            traverse(node.getLeft());
        }
    }

    public void process(Node node) {
        if (null != node) {
            int id = node.getId();
            for (String courseName : node.getCourseList()) {
                Object obj = courseRecords.get(courseName);
                if (null == obj) {
                    Set<Integer> studentsId = new HashSet<>();
                    studentsId.add(id);
                    courseRecords.put(courseName, studentsId);
                } else {
                    Set<Integer> studentsId = (Set<Integer>)obj;
                    studentsId.add(id);
                    //courseRecords.put(courseName, studentsId);
                }
            }
        }
    }

    public void iterateRecords() {
        for (Map.Entry <String, Set <Integer>> entry : courseRecords.entrySet()) {
            String key = entry.getKey();
            Set<Integer> value = entry.getValue();
            StringBuilder sbr = new StringBuilder();
            for(Integer i: value){
                sbr.append(i+" ");
            }
            System.out.println(key+" "+sbr.toString());
        }
    }
}
