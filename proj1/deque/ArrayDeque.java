package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Iterable<T>{
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
    public boolean equals(Object o){
        if (! (o instanceof ArrayDeque<?>)|| ((ArrayDeque<?>) o).size != this.size){
            return false;
        }
        for(int i = 0; i < size(); i++){
            if(((ArrayDeque<?>) o).get(i) != this.get(i)){
                return false;
            }
        }
        return true;
    }
    public Iterator<T> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<T> {
        private  int wizPos;

        public DequeIterator(){
            wizPos = nextFirst == items.length-1 ? 0 : nextFirst + 1;
        }
        public boolean hasNext(){
            return  wizPos != nextLast;
        }
        public T next(){
            T returnItem = items[wizPos];
            wizPos = wizPos == items.length-1 ? 0 : wizPos + 1;
            return returnItem;
        }
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
        for(int i = 0; i < size; i++) {
            System.out.print(items[start]);
            System.out.print(' ');
            start = start == items.length-1 ? 0 : start + 1;
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
        while(--index >= 0){
            start = start == items.length-1 ? 0 : start + 1;
        }
        return items[start];
    }
}
