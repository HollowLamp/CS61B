package bstmap;

import edu.princeton.cs.algs4.BST;

import java.security.Key;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable, V> implements Map61B <K, V>{
    private class BSTNode {
        K key;
        V val;
        BSTNode Lchild;
        BSTNode Rchild;
        BSTNode(K k, V v) {
            key = k;
            val = v;

        }
    }
    private BSTNode root;
    private int size = 0;
    @Override
    public V get(K key) {
        return get_helper(root, key);
    }
    private V get_helper(BSTNode t, K key) {

        if(t == null) {
            return null;
        }
        if(t.key.equals(key)){
            return t.val;
        } else if (t.key.compareTo(key) > 0) {
            return get_helper(t.Lchild, key);
        } else {
            return get_helper(t.Rchild, key);
        }
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public void clear() {
        size = 0;
        root = null;
    }
    /*
    对于修改树结构的方法，通用的思路的就是定义一个私有帮助函数，根节点就是该函数的返回值
     */
    @Override
    public void put(K key, V value){
        root = put_helper(root , key, value);
        size++;
    }
    private BSTNode put_helper(BSTNode t, K key, V value){
        if(t == null) {
            return new BSTNode(key, value);
        } else if (t.key.equals(key)) {
            t.val = value;
        } else if (t.key.compareTo(key) > 0) {
            t.Lchild = put_helper(t.Lchild, key, value);
        } else if (t.key.compareTo(key) < 0) {
            t.Rchild = put_helper(t.Rchild, key, value);
        }
        return t;
    }
    @Override
    public boolean containsKey(K key) {
        return conHelper(root, key);
    }
    private boolean conHelper(BSTNode t, K key){
        if(t == null){
            return false;
        } else if (t.key.equals(key)) {
            return true;
        } else if (t.key.compareTo(key) > 0) {
            return conHelper(t.Lchild, key);
        } else if (t.key.compareTo(key) < 0) {
            return conHelper(t.Rchild, key);
        }
        return false;
    }

    private K min(){
        return min(root).key;
    }
    private BSTNode min(BSTNode t){
        if (t.Lchild == null) return t;
        return min(t.Lchild);
    }
    private void deleteMin(){
        root = deleteMin(root);
    }
    private BSTNode deleteMin(BSTNode t){
        if(t.Lchild == null){
            return t.Rchild;
        }
        return deleteMin(t.Lchild);
    }
    /*
    递归删除的基本想法，递归函数接受一个根结点，一个key，返回这个key删除之后的树的根结点
    那么，如果key不是这个根结点，就根据大小关系判断，要在左分支删，还是右分支删
    如果要删除的就是这个根结点，分情况
    一个是，若当前结点不是完全二叉的，返回其分支即可
    另外一个，若当前结点完全二叉，就需要更改树的结构，因为直接删除这个结点会有2个连接需要分配
    更改结构需要保持BST的性质，基本的想法就是，让待删除结点的某一个子结点作为新的根结点
    为保持这个性质，很显然，新的结点要么是左分支中的最大值，要么是右分支中的最小值
    以右分支为例，还需要实现min函数，min的实现也很简单，形象来说就是最左下的那个点
    */
    @Override
    public V remove(K key) {
        size --;
        V ans = get(key);
        root = removeHelper(root, key);
        return ans;
    }
    private BSTNode removeHelper(BSTNode t, K key){
        if(t == null){
            return null;
        } else if (t.key.equals(key)) {
            //若不是完全二叉的
            if(t.Rchild == null) {
                return t.Lchild;
            }
            if(t.Lchild == null) {
                return t.Rchild;
            }
            BSTNode x = t;
            t = min(x.Rchild);
            t.Rchild = deleteMin(x.Rchild);
            t.Lchild = x.Lchild;
        } else if (t.key.compareTo(key) > 0) {
            t.Lchild = removeHelper(t.Lchild, key);
        }else {
            t.Rchild = removeHelper(t.Rchild, key);
        }
        return t;
    }
    @Override
    public V remove(K key, V value) {
        if (get(key) == value){
            return remove(key);
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        keySetHelper(returnSet, root);
        return returnSet;
    }
    private void keySetHelper(Set<K> s, BSTNode t) {
        if (t == null) {
            return;
        }
        keySetHelper(s, t.Lchild);
        s.add(t.key);
        keySetHelper(s, t.Rchild);
    }
    @Override
    /*
    本质就是中序先序后序遍历的迭代形式，分解到三个部分去写
     */
    public Iterator<K> iterator() {return new BSTMapIter(); }

    private class BSTMapIter implements Iterator<K>{
        private BSTNode curr;
        private Stack<BSTNode> nodeStack;
        public BSTMapIter(){
            nodeStack = new Stack<>();
            curr = root;
            //压入根值后，压左树
            while(curr != null){
                nodeStack.push(curr);
                curr = curr.Lchild;
            }
        }
        public boolean hasNext(){
            return !nodeStack.empty();
        }
        public K next(){
            //弹出当前值
            BSTNode p = nodeStack.pop();
            K ans = p.key;
            //尝试压入当前值的右树
            p = p.Rchild;
            while(p != null){
                nodeStack.push(p);
                p = p.Rchild;
            }
            return ans;
        }
    }

    public void printInOrder(){
        printHelper(root);
    }
    private void printHelper(BSTNode t){
        if(t != null){
            printHelper(t.Lchild);
            System.out.print(t.key);
            printHelper(t.Rchild);
        }
    }
}
