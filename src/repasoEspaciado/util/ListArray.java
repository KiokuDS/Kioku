package repasoEspaciado.util;

public class ListArray <T>  {

    private static final int DEFAULT_SIZE = 10;
    private int size = 0;
    private T[] listArr;
    private int position;

    public ListArray(){
        listArr = (T[]) new Object[DEFAULT_SIZE];
    }
    public ListArray(int size){
        listArr = (T[]) new Object[size];
    }
    private boolean full(){
        return size == listArr.length;
    }

    private boolean empty(){
        return size == 0;
    }

    public T getItem(int position){
        return listArr[position];
    }
    public void insert(T item){
        if(full()){
            reallocate();
        }
        listArr[size++] = item;
    }
    public void insert(T item, int position){
        if(position < 0 && position > listArr.length+1){
            System.out.println("Posicion incorrecta");
        }
        if(full()){
            reallocate();
        }
        for(int j = size; j > position; j--) {
            listArr[j] = listArr[j-1];
        }
        listArr[position] = item;
        size++;
    }
    public void reallocate(){
        T[] oldArray = listArr;
        listArr = (T[]) new Object[2*size];
        System.arraycopy(oldArray,0,listArr,0,size);
    }
    public void delete(T item){
        if(!empty()){
            if(search(item)){
                for(int j = position; j < size-1;j++) {
                    listArr[j]=listArr[j+1];
                }
                size--;
            }
        } else {
            System.out.println("Empty list.");
        }
    }
    public void delete(int position){
        for(int j = position; j < size-1;j++) {
            listArr[j]=listArr[j+1];
        }
        size--;
    }
    public boolean search(T item){
        for(int i=0; i<size; i++){
            if (listArr[i].equals(item)) {
                position = i;
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }


    public void print() {
        int j = 0;
        while(j != size){
            System.out.print(listArr[j]+" \n");
            j++;
        }
    }

}
