package BusinessLogic.util;

import BusinessLogic.Flashcard;

public class Tree {

    public Flashcard root;

    private Flashcard addRecursive(Flashcard current, Flashcard nodeToBeInserted){
        if(current==null) {
            return nodeToBeInserted;
        }
        if(current.front.compareTo(nodeToBeInserted.front) > 0){
            current.left = addRecursive(current.left, nodeToBeInserted);
        }
        else if(current.front.compareTo(nodeToBeInserted.front) < 0){
            current.right = addRecursive(current.right, nodeToBeInserted);
        }
        else return current;
        return current;
    }
    public void add(Flashcard fc) {
        root = addRecursive(root, fc);
    }
    public void traverseInOrder(Flashcard node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node);
            traverseInOrder(node.right);
        }
    }
    public void print() {
        traverseInOrder(this.root);
    }

    private Flashcard containsNodeRecursive(Flashcard current, String value) {
        if (current == null) {
            return null;
        }
        if (value.contentEquals(current.front)) {
            return current;
        }

        return (value.compareTo(current.front) < 0)
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public Flashcard search(String value){
        return(this.containsNodeRecursive(this.root, value));
    }
}
