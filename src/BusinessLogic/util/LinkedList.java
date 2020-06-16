package BusinessLogic.util;

public class LinkedList <T>  {

    class Node<T>{
        T data;
        Node<T> next;

        public Node(){
        }
        public Node(T data){
            this.data = data;
            next = null;
        }
        public Node(T data, Node<T> next){
            this.data = data;
            this.next = next;
        }
        public T getItem(){
            return data;
        }
        public void setData(T data){
            this.data = data;
        }
        public Node geteNext(){
            return next;
        }
        public void setData(Node<T> next){
            this.next = next;
        }
    }

    private Node head;
    private int size = 0;

    public LinkedList(){
        size = 0;
        head = null;
    }
    public LinkedList(int size){
        this.size = size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }
    void checkIndex(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException(
                    "index = " + index + " size "+size
            );
        }
    }
    public T get(int index){
        checkIndex(index);
        Node<T> current = head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.data;
    }
    public int indexOf(T element){
        Node<T> current = head;
        int index = 0;
        while(current != null && !current.data.equals(element)){
            current = current.next;
            index++;
        }
        if(current == null){
            return -1;
        } else{
            return index;
        }
    }
    public T remove (int index) {
        checkIndex(index);
        T removedElement = null;
        if (index == 0) {
            removedElement = (T) head.data;
            head = head.next;
        } else {
            Node<T> p = head;
            for (int i = 0; i < index; i++) {
                p = p.next;
                removedElement = p.next.data;
                p.next = p.next.next;
            }
            size--;
        }
        return removedElement;
    }

    public void insert(int index, T element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index )"+index+" size ");
        }
        if(index == 0){
            head = new Node<T>(element, head);
        } else {
            Node<T> p = head;
            for(int i = 0; i < index-1; i++){
                p = p.next;
            }
            p.next = new Node<T>(element, p.next);
        }
        size++;
    }

}

