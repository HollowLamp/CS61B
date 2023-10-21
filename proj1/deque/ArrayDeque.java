package deque;
import java.io.ObjectStreamException;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    @Override
    public Iterator<T> iterator(){
        return new ArrayDeque.DequeIterator();
    }
    private class DequeIterator implements Iterator<T> {
        private int wizPos;
        public DequeIterator(){
            wizPos = 0;
        }
        public boolean hasNext(){
            return wizPos < size;
        }
        public T next(){
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(! (o instanceof Deque)){
            return false;
        }

        if(this.size != ((Deque<T>) o).size()){
            return false;
        }
        Deque<T> tmp = (Deque<T>) o;
        for(int i = 0; i < size; i++){
            if(!(this.get(i).equals(tmp.get(i)))){
                return false;
            }
        }
        return true;
    }
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        nextFirst = nextFirst == items.length - 1 ? 0 : nextFirst + 1;
        nextLast = nextLast == 0 ? items.length - 1 : nextLast - 1;
        int min = nextFirst < nextLast ? nextFirst : nextLast;
        int max = nextFirst > nextLast ? nextFirst : nextLast;
        if(capacity == size * 2) {
            if (nextFirst - nextLast == size - 1 || nextLast - nextFirst == size - 1) {
                for (int i = 0; i < size / 2; i++) {
                    temp[i] = null;
                }
                for (int i = size / 2 + size; i < capacity; i++) {
                    temp[i] = null;
                }
                System.arraycopy(items, 0, temp, size / 2, size);
                if (nextFirst > nextLast) {
                    nextLast += size / 2 - 1;
                    nextFirst += size / 2 + 1;
                } else {
                    nextFirst += size / 2 - 1;
                    nextLast += size / 2 + 1;
                }
            } else {
                for (int i = 0; i <= min; i++) {
                    temp[i] = items[i];
                }
                for (int i = min + 1; i < capacity / 2 + min + 1; i++) {
                    temp[i] = null;
                }
                System.arraycopy(items, max, temp, capacity / 2 + min + 1, size - max);
                if (nextFirst > nextLast) {
                    nextLast += 1;
                    nextFirst += capacity / 2 - 1;
                } else {
                    nextFirst += 1;
                    nextFirst += capacity / 2 - 1;
                }
            }
            items =temp;
        } else {
            int start = nextFirst;
            for(int i = 0; i < size; i++){
                temp[i] = items[start];
                start = start == items.length-1 ? 0 : start + 1;
            }
            for(int i = size; i < capacity; i++){
                temp[i] = null;
            }
            nextLast = size;
            items = temp;
            nextFirst = items.length - 1;
        }
    }
    @Override
    public void addFirst(T item){
        items[nextFirst] = item;
        nextFirst = nextFirst == 0 ? items.length -1 : nextFirst - 1;
        size += 1;
        if(size == items.length){
            resize(size*2);
        }
    }
    @Override
    public void addLast(T item){
        items[nextLast] = item;
        nextLast = nextLast == items.length - 1 ? 0 : nextLast + 1;
        size += 1;
        if(size == items.length){
            resize(size*2);
        }
    }

    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        int start = nextFirst == items.length-1 ? 0 : nextFirst + 1;
        for(int i = 0; i < size; i++) {
            System.out.print(items[start]);
            System.out.print(' ');
            start = start == items.length-1 ? 0 : start + 1;
        }
        System.out.println(' ');
    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if(items.length >= 16 && (double) size / (double) items.length < 0.25){
            resize(items.length/4);
        }
        nextFirst = nextFirst == items.length - 1 ? 0 : nextFirst + 1;
        size -= 1;
        return items[nextFirst];
    }
    @Override
    public T removeLast(){
        if(size == 0){
            return null;
        }
        if(items.length >= 16 && (double) size / (double) items.length < 0.25){
            resize(items.length/4);
        }
        nextLast = nextLast == 0 ? items.length - 1 : nextLast - 1;
        size -= 1;
        return items[nextLast];
    }
    @Override
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
