package backupVisitors.myTree;

import backupVisitors.util.Logger;

import java.util.SortedSet;
import java.util.ArrayList;
import java.util.TreeSet;


/**
 * Created by abhineetsharma on 6/29/17.
 */
public class Node implements ObserverI, SubjectI, Cloneable ,Comparable<Node>{
    private ArrayList <ObserverI> observerList;
    private int id;
    private SortedSet<String> courseList;
    private Node left;
    private Node right;
    public enum Action{INSERT,DELETE};

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    /**
     * Parametrised constructor : accepts id of typr int and courseName of type String
     * Return a Student Info object
     */
    public Node(int idI, String courseName) {

        id = idI;
        left = null;
        right = null;
        addCourse(courseName);
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("Node Constructor: Object Crested with Id : %d and course %s", id, courseName),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    /**
     * Add course to current node
     */
    public  void addCourse(String courseName) {

        int courseASCII = (int)courseName.charAt(0);
        if (courseASCII >= (int)'A' && courseASCII <= (int)'K' || courseASCII == (int)'S');
        else courseName = null;

        if (null == getCourseList()) {
            courseList = new TreeSet <>();
        }
        if (null != courseName)// && !courseList.contains(courseName))
            courseList.add(courseName);
    }

    /**
     * remove course from current object node if the course is present
     */
    public void removeCourse(String courseName) {
        if (null != courseList) //&& courseList.contains(courseName))
            courseList.remove(courseName);
    }

    /**
     * Observer pattern function to register observer
     * Interface implemented SubjectI
     */
    @Override
    public void registerObserver(ObserverI observer) {
        if (null == getObserverList())
            setObserverList(new ArrayList <>());
        observerList.add(observer);
    }

    /**
     * Observer pattern function to remove observer
     * Interface implemented SubjectI
     */
    @Override
    public void removeObserver(ObserverI observer) {
        int index = observerList.indexOf(observer);
        if (index > -1) {
            observerList.remove(observer);
        }
    }

    /**
     * Observer pattern function to notify all observer
     * Interface implemented SubjectI
     */
    @Override
    public void notify(Action operation, String courseName) {
        for (ObserverI node : observerList)
            node.update(operation, courseName);
    }

    /**
     * Override to clone method for getting the clone of node
     */
    @Override
    public Node clone() {
        Node clone = null;
        try {
            clone = (Node) super.clone();
            clone.id = this.getId();
            clone.courseList = new TreeSet <>(this.getCourseList());
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return clone;
    }

    /**
     * Get for the current node's observer List
     */
    public ArrayList <ObserverI> getObserverList() {
        return observerList;
    }

    /**
     * Set for the current node's observer List
     */
    private void setObserverList(ArrayList <ObserverI> observerList) {
        this.observerList = observerList;
    }

    /**
     * Observer pattern function to update the observer course list with insert or delete opration
     * Interface implemented ObserverI
     */
    @Override
    public void update(Action operation, String courseName) {

        switch (operation) {
            case DELETE:
                removeCourse(courseName);
                break;
            case INSERT:
                addCourse(courseName);
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Get current node ID
     */

    public int getId() {
        return id;
    }

    /**
     * Get current node Course List
     */
    public SortedSet <String> getCourseList() {
        return courseList;
    }

    /**
     * Get current node left
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Set current node left
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Get current node right
     */
    public Node getRight() {
        return right;
    }

    /**
     * Set current node right
     */
    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public int compareTo(Node node) {
        if (id == node.getId())
            return 0;
        else if(id < node.getId())
            return -1;
        else
            return 1;
    }
}

