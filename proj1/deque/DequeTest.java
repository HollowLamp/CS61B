package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class DequeTest {
    // YOUR TESTS HERE
    @Test
    public void test() {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> A = new ArrayDeque<>();
        int N = 500000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                A.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
                System.out.println("size1: " + L.size() + "size2: " + A.size());
            } else if (L.size() != 0 && operationNumber == 1) {
                assertEquals(L.removeLast(), A.removeLast());
                System.out.println("removeLast()");
                System.out.println("size1: " + L.size() + "size2: " + A.size());
            } else if (L.size() != 0 && (operationNumber == 2 || operationNumber == 5)) {
                int randVal = StdRandom.uniform(0, L.size());
                assertEquals(L.get(randVal), A.get(randVal));
                System.out.println("L.get(): " + L.get(randVal) + "A.get(): " + A.get(randVal));
            } else if (L.size() != 0 && operationNumber == 3) {
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                A.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");
                System.out.println("size1: " + L.size() + "size2: " + A.size());
            } else if (L.size() != 0 && operationNumber == 4) {
                assertEquals(L.removeFirst(), A.removeFirst());
                System.out.println("removeFirst()");
                System.out.println("size1: " + L.size() + "size2: " + A.size());
            }
        }
    }
    @Test
    public void testResize(){
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for(int i = 0; i < 500; i++){
            L.addFirst(1);
            A.addFirst(1);
        }
        for(int i = 0; i < 500; i++){
            L.removeFirst();
            A.removeFirst();
        }
    }
    @Test
    public void testIterator(){
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> A = new ArrayDeque<>();
        L.addFirst(1);
        A.addFirst(1);
        L.addFirst(2);
        A.addFirst(2);
        L.addFirst(3);
        A.addFirst(3);
        for (int i : L){
            System.out.println(i);
        }
        for (int i : A){
            System.out.println(i);
        }
        System.out.println(L.equals(A));
        L.removeLast();
        System.out.println(L.equals(A));
    }
}
