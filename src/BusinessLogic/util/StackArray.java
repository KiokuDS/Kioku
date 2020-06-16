package BusinessLogic.util;

import java.util.Arrays;

public class StackArray<T>  {

    private int top;
    private T[] arrayStack;

    public StackArray(int n){
        this.top = 0;
        arrayStack = (T[]) new Object [n];
    }

    public boolean empty(){
        return top <= 0;
    }
    public boolean full(){
        return top >= arrayStack.length;
    }

    public T pop() {
        if(empty()){
            throw new RuntimeException("Pila vacia");
        } else {
            top --;
        }
        return arrayStack[top];
    }
    public void push(T item){
        if(full()){
            throw new RuntimeException("Pila llena");
        } else {
            arrayStack[top]=item;
            top++;
        }
    }
    public T peek(){
        if(!empty()){
            return arrayStack[top];
        } else {
            throw new RuntimeException("Pila vacia peek");
        }
    }
}
