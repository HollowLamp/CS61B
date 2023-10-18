package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>,Deque<T> {
    public class ItemNode<T>{
        public T item;
        public ItemNode next;

        public ItemNode prev;
        public ItemNode(T t, ItemNode p, ItemNode n) {
            item = t;
            prev = p;
            next = n;
        }
    }

    private ItemNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new ItemNode(null, null, null);
        size = 0;
    }

    public Iterator<T> iterator(){
        return new LinkedListDeque.DequeIterator();
    }
    private class DequeIterator implements Iterator<T> {
        private  ItemNode p;

        public DequeIterator(){
            p = sentinel.next;
        }
        public boolean hasNext(){
            return  p.next != null;
        }
        public T next(){
            T returnItem = (T) p.item;
            p = p.next;
            return returnItem;
        }
    }

    public boolean equals(Object o){
        if (! (o instanceof LinkedListDeque<?>)|| ((LinkedListDeque<?>) o).size != this.size){
            return false;
        }
        for(int i = 0; i < size(); i++){
            if(((LinkedListDeque<?>) o).get(i) != this.get(i)){
                return false;
            }
        }
        return true;
    }
    @Override
    public void addFirst(T item){
        if(sentinel.next == null){
            sentinel.next = new ItemNode(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
            size += 1;
            return;
        }
        sentinel.next = new ItemNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    @Override
    public void addLast(T item){
        if(sentinel.prev == null){
            sentinel.prev = new ItemNode<>(item, sentinel, sentinel);
            sentinel.next = sentinel.prev;
            size += 1;
            return;
        }
        sentinel.prev = new ItemNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        ItemNode p = sentinel.next;
        while(p != sentinel){
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst(){
        if(sentinel.next == null){
            return null;
        }
        T tmp = (T) sentinel.next.item;
        if(size == 1){
            sentinel.next = null;
            sentinel.prev = null;
            size -= 1;
            return tmp;
        }
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return tmp;
    }
    @Override
    public T removeLast(){
        if(sentinel.prev == null){
            return null;
        }
        T tmp = (T) sentinel.prev.item;
        if(size == 1){
            sentinel.next = null;
            sentinel.prev = null;
            size -= 1;
            return tmp;
        }
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return tmp;
    }
    @Override
    public T get(int index){
        if(index+1 > size){
            return null;
        }
        ItemNode p = sentinel.next;
        while(index-- > 0){
            p = p.next;
        }
        return (T) p.item;
    }

    /**
     * 返回从指定节点起后第index个节点的值
     * @param index 序号
     * @param node 起始节点
     * @return 返回对应节点的值
     */
    private T helper(int index, ItemNode node){
        if(index == 0){
            return (T) node.item;
        }
        return (T) helper(index-1, node.next);
    }
    public T getRecursive(int index){
        return (T) helper(index, sentinel.next);
    }
}
