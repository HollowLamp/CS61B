package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int size;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] newTable = (Collection<Node>[]) new Collection[tableSize];
        for(int i = 0; i < tableSize; i++) {
            newTable[i] = createBucket();
        }
        return newTable;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /**
     * 获取key
     */
    private int getInd(K key, int leng){
        int hash = key.hashCode();
        return Math.floorMod(hash, leng);
    }
    /**
     * 检查并修改大小
     */
    private void resize(){
        double curr = (double) size / buckets.length;
        if(curr > loadFactor) {
            int newCap = 2 * buckets.length;
            Collection<Node>[] newBuckets = createTable(newCap);
            //collection的迭代器
            for(Collection<Node> bucket : buckets){
                //bucket的迭代器
                for(Node node : bucket){
                    int newInd = getInd(node.key, newCap);
                    newBuckets[newInd].add(node);
                }
            }
            buckets = newBuckets;
        }
    }
    @Override
    public void clear(){
        for(Collection<Node> bucket : buckets){
            bucket.clear();
        }
        size = 0;
    }
    @Override
    public boolean containsKey(K key){
        int index = getInd(key, buckets.length);
        Collection<Node> bucket = buckets[index];
        //这里调用的迭代器是bucket的数据结构定义的，而不是collection的
        for(Node node : bucket){
            if(node.key.equals(key)){
                return true;
            }
        }
        return false;
    }
    @Override
    public V get(K key){
        int index = getInd(key, buckets.length);
        Collection<Node> bucket = buckets[index];
        for(Node node : bucket){
            if(node.key.equals(key)){
                return node.value;
            }
        }
        return null;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void put(K key, V value){
        int index = getInd(key, buckets.length);
        Collection<Node> bucket = buckets[index];
        for(Node node : bucket){
            if(node.key.equals(key)){
                node.value = value;
                return;
            }
        }
        bucket.add(createNode(key, value));
        size++;
        resize();
    }
    @Override
    public Set<K> keySet(){
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    /**
     * 使用index跟踪桶，使用迭代器迭代每个桶中的所有key
     */
    private class MapIterator implements Iterator<K> {
        private int bucketIndex = 0;
        private Iterator<Node> nodeIterator;

        public MapIterator() {
            advanceToNextBucket();
        }

        private void advanceToNextBucket() {
            while (bucketIndex < buckets.length && (buckets[bucketIndex] == null || !buckets[bucketIndex].iterator().hasNext())) {
                bucketIndex++;
            }
            if (bucketIndex < buckets.length) {
                nodeIterator = buckets[bucketIndex].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (nodeIterator == null) {
                return false;
            }
            if (nodeIterator.hasNext()) {
                return true;
            } else {
                bucketIndex++;
                advanceToNextBucket();
                return nodeIterator != null && nodeIterator.hasNext();
            }
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node currentNode = nodeIterator.next();
            if (!nodeIterator.hasNext()) {
                bucketIndex++;
                advanceToNextBucket();
            }
            return currentNode.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MapIterator();
    }
    /**
     * 参考自bing
     * 在remove方法中显式调用迭代器是因为我们需要在遍历桶中的元素时有能力移除特定的元素。
     * Java的Iterator接口提供了remove()方法，这允许我们在迭代过程中安全地移除元素，而不会引起ConcurrentModificationException。
     * 这是在使用例如for-each循环时可能会遇到的问题。
     *
     * 在之前的方法中，如containsKey或get，我们只是在查找元素，而不需要移除它们。
     * 因此可以直接使用for-each循环来遍历集合。
     * for-each循环内部使用的是迭代器，但它是隐式的，我们不需要直接操作迭代器。
     *
     * 总的来说，当我们需要修改集合时，显式使用迭代器是必要的，因为它提供了更多的控制权和安全性。
     */
    @Override
    public V remove(K key) {
        Iterator<K> keyIterator = this.iterator();
        Node nodeToRemove = null;
        while (keyIterator.hasNext()) {
            K currentKey = keyIterator.next();
            if (currentKey.equals(key)) {
                for (Node node : buckets[getInd(key, buckets.length)]) {
                    if (node.key.equals(key)) {
                        nodeToRemove = node;
                        break;
                    }
                }
                if (nodeToRemove != null) {
                    buckets[getInd(key, buckets.length)].remove(nodeToRemove);
                    size--;
                    return nodeToRemove.value;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        Iterator<K> keyIterator = this.iterator();
        Node nodeToRemove = null;
        while (keyIterator.hasNext()) {
            K currentKey = keyIterator.next();
            if (currentKey.equals(key)) {
                for (Node node : buckets[getInd(key, buckets.length)]) {
                    if (node.key.equals(key) && node.value.equals(value)) {
                        nodeToRemove = node;
                        break;
                    }
                }
                if (nodeToRemove != null) {
                    buckets[getInd(key, buckets.length)].remove(nodeToRemove);
                    size--;
                    return nodeToRemove.value;
                }
            }
        }
        return null;
    }


}
