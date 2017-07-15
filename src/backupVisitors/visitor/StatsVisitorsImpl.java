package backupVisitors.visitor;

import backupVisitors.myTree.Node;
import backupVisitors.util.TreeBuilder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by abhineetsharma on 7/12/17.
 */
public class StatsVisitorsImpl implements TreeVisitorI {
    private ArrayList <Character> courseList;

    @Override
    public void visit(TreeBuilder tree) {
        System.out.println("StatsVisitors");
        courseList = new ArrayList <>();
        traverse(tree.getRoot(), tree);
        process();
    }

    private void traverse(Node node, TreeBuilder tree) {
        if (node != null) {
            traverse(node.getRight(), tree);
            for (String str : node.getCourseList()) {
                char c = str.charAt(0);
                if (c != 'S')
                    courseList.add(c);
            }
            traverse(node.getLeft(), tree);
        }
    }

    private void process() {
        Collections.sort(courseList);
        System.out.println(courseList.toString());
        char median = getMedian();
        System.out.println("Median : " + median);
        char mean = getMean();
        System.out.println("Mean : " + mean);

    }

    private char getMedian() {
        int size = courseList.size();
        int index = size / 2 - 1;
        char c;
        if (size % 2 == 0) {

            System.out.println(courseList.get(index) + " " + courseList.get(index + 1));
            float a = (float) courseList.get(index);
            float b = (float) courseList.get(index + 1);
            float f = a + b;
            f /= 2;
            c = (char) Math.ceil(f);
        } else {
            c = courseList.get(index);
        }
        return c;

    }

    private char getMean() {
        double num = 0;

        char a;
        for (char c : courseList) {
            num += (int) c;
        }
        Double f = num / courseList.size();
        a = (char) Math.ceil(f);

        return a;
    }
}
