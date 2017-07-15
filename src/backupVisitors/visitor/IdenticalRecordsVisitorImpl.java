package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;

import java.util.*;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class IdenticalRecordsVisitorImpl implements TreeVisitorI {
    Map <String, Set <Integer>> courseRecords;

    @Override
    public void visit(TreeBuilder tree) {
        System.out.println("Identical");
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

    private void process(Node node) {
        if (null != node) {
            int id = node.getId();

            String courseEnrolled = node.getCourseList().toString();
            Object obj = courseRecords.get(courseEnrolled);
            if (null == obj) {
                SortedSet <Integer> studentsId = new TreeSet <>();
                studentsId.add(id);
                courseRecords.put(courseEnrolled, studentsId);
            } else {
                SortedSet <Integer> studentsId = (SortedSet <Integer>) obj;
                studentsId.add(id);
            }

        }
    }

    private void iterateRecords() {
        Map<String, SortedSet <Integer>> orderedMap = new TreeMap(courseRecords);
        for (Map.Entry <String, SortedSet <Integer>> entry : orderedMap.entrySet()) {
            String courseEnrolled = entry.getKey();

            SortedSet <Integer> studentsEnrolled = entry.getValue();
            System.out.println("Course Names: " + beautifyString(courseEnrolled) + " Students Enrolled: " + beautifyString(studentsEnrolled.toString()));
        }
    }

    private String beautifyString(String str) {
        str = str.substring(1, str.length() - 1);
        return str;
    }
}
