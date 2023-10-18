package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;


    public ArrayDeque(){
        items =(T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public void addFirst(T item){
        items[nextFirst] = item;
        size += 1;
        nextFirst = nextFirst == 0 ? items.length -1 : nextFirst - 1;
    }

    public void addLast(T item){
        items[nextLast] = item;
        size += 1;
        nextLast = nextLast == items.length - 1 ? 0 : nextLast + 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int start = nextFirst == items.length-1 ? 0 : nextFirst + 1;
        int end = nextLast == 0 ? items.length - 1 : nextLast - 1;
        for(int i = start; i <= end; i++){
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println(' ');
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextFirst = nextFirst == items.length - 1 ? 0 : nextFirst + 1;
        return items[nextFirst];
    }

    public T removeLast(){
        if(size == 0){
            return null;
        }
        size -= 1;
        nextLast = nextLast == 0 ? items.length - 1 : nextLast - 1;
        return items[nextLast];
    }

    public T get(int index){
        if(index + 1 > size){
            return null;
        }
        int start = nextFirst == items.length-1 ? 0 : nextFirst + 1;
        while(--index > 0){
            start = start == items.length-1 ? 0 : start + 1;
        }
        return items[start];
    }
}
