package backupVisitors.myTree;


/**
 * Subject Interface
 */
interface SubjectI {

    void registerObserver(ObserverI observer);
    void removeObserver(ObserverI observer);
    void notify(Node.Action operation, String courseName);
}
