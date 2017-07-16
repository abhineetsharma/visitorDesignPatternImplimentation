package backupVisitors.util;

import backupVisitors.myTree.Node;
import backupVisitors.myTree.ObserverI;
import backupVisitors.visitor.TreeVisitorI;

/**
 * TreeBuilder class
 */
public class TreeBuilder {
    private Node root;

    private TreeBuilder backupTree1;
    private TreeBuilder backupTree2;

    Logger.DebugLevel INFO = Logger.DebugLevel.INFO;
    Logger.DebugLevel CONSTRUCTOR = Logger.DebugLevel.CONSTRUCTOR;
    Logger.DebugLevel FILE_PROCESSOR = Logger.DebugLevel.FILE_PROCESSOR;

    /**
     * TreeBuilder constructor
     */
    public TreeBuilder() {
        root = null;
        Logger.addTextSeparator(CONSTRUCTOR);
        Logger.writeMessage(String.format("TreeBuilder Constructor: Object Crested "),CONSTRUCTOR);
        Logger.addTextSeparator(CONSTRUCTOR);
    }

    /**
     * Calls the recursive part og insertCourseForId with root node
     */
    private void insertNodeIntoTree(Node node) {
        root = insertRecord(getRoot(), node);
    }

    /**
     * Recursively find the node and set the reference to the new node
     */
    private Node insertRecord(Node currentNode, Node node) {
        if (currentNode == null) {
            currentNode = node;
            return currentNode;
        } else if (node.compareTo(currentNode) == -1)
            currentNode.setLeft(insertRecord(currentNode.getLeft(), node));
        else if (node.compareTo(currentNode) == 1)
            currentNode.setRight(insertRecord(currentNode.getRight(), node));
        return currentNode;
    }

    /**
     * Recursive search for a given a id
     */
    private Node searchRec(Node node, int id) {
        Node result = null;
        int nodeId;
        if (node == null) {
            return null;
        } else if ((nodeId=node.getId()) == id) {
            return node;
        } else if (nodeId > id) {
            result = searchRec(node.getLeft(), id);
        } else if (nodeId < id) {
            result = searchRec(node.getRight(), id);
        }

        return result;
    }

    /**
     * Recursive part of Print BST tree in in-order
     */
    private void printNodeRec(Results results, Node node) {
        if (node != null) {
            printNodeRec(results, node.getLeft());

            StringBuilder sbr = new StringBuilder("Courses : ");

            if (node.getCourseList().size() == 0)
                sbr.append("No course enrolled");
            else sbr.append(node.getCourseList());

            results.storeNewResult(String.format("Student id : %d %s", node.getId(), sbr.toString()));

            printNodeRec(results, node.getRight());
        }
    }

    /**
     * Search the node with a given id
     */
    public Node searchNode(int id) {
        return searchRec(root, id);
    }

    /**
     * Print BST tree in in-order
     */
    public void printNode(Results results) {
        printNodeRec(results, getRoot());
    }

    /**
     * Insert node into the tree
     * After creating a new node if node is not present in the tree and
     * clone it two times as backup nodes and dave it as a observer of the main node
     */
    public String insertCourseForId(int id, String courseName) {
        Node node;

        if (null != courseName && (node = searchNode(id)) != null) {
            node.addCourse(courseName);
            node.notify(Node.Action.INSERT, courseName);
        } else {
            node = new Node(id, courseName);
            insertNodeIntoTree(node);

            ObserverI nodeBackUp1 = node.clone();
            if (null != nodeBackUp1) {
                backupTree1.insertNodeIntoTree((Node) nodeBackUp1);
                node.registerObserver(nodeBackUp1);
            } else {
            }

            ObserverI nodeBackUp2 = node.clone();
            if (null != nodeBackUp2) {
                backupTree2.insertNodeIntoTree((Node) nodeBackUp2);
                node.registerObserver(nodeBackUp2);
            } else {
            }

        }
        return String.format("Courses Enrolled : %s", node.getCourseList());
    }

    /**
     * Remove a course from the node
     */
    public String removeCourseFromNodeAndObservers(int id, String courseName) {
        Node node;

        if ((node = searchNode(id)) != null) {
            node.removeCourse(courseName);
            node.notify(Node.Action.DELETE, courseName);
        }
        return node.getCourseList().size() > 0 ? node.getCourseList().toString() : "No course enrolled";
    }

    /**
     * Visitor pattern accept method implementation
     */
    public void accept(TreeVisitorI visitor,String outputFilePath) {
        visitor.visit(this,outputFilePath);
    }

    public Node getRoot() {
        return root;
    }

    public TreeBuilder getBackupTree1() {
        return backupTree1;
    }

    public void setBackupTree1(TreeBuilder backupTree1) {
        this.backupTree1 = backupTree1;
    }

    public TreeBuilder getBackupTree2() {
        return backupTree2;
    }

    public void setBackupTree2(TreeBuilder backupTree2) {
        this.backupTree2 = backupTree2;
    }
}
