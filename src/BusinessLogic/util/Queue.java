package BusinessLogic.util;

public class Queue<T> {
    private int n = 10;
    private int front;
    private int rear;
    private int count;
    private T[] queueArray;

    public Queue(){
        front = count = rear = 0;
        queueArray = (T[]) new Object[n];
    }
    public Queue(int size){
        front = count = rear = 0;
        n = size;
        queueArray = (T[]) new Object[size];
    }
    public boolean empty(){
        return count <= 0;
    }
    public boolean full(){
        return count >= n;
    }
    public int getCount(){
        return count;
    }
    public void enqueue(T data) throws Exception {
        if(full()){
            throw new Exception("Cola llena");
        } else {
            queueArray[rear] = data;
            rear = (rear+1) % n;
            count++;
        }
    }
    public T dequeue() throws Exception {
        T data = null;
        if(empty()){
            throw new Exception("Cola vacia");
        } else {
            data = queueArray[front];
            front = (front+1) % n;
            count--;
        }
        return data;
    }
}
