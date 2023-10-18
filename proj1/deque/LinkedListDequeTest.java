package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class LinkedListDequeTest {
    // YOUR TESTS HERE
    @Test
    public void test() {
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        ArrayDeque<Integer> B = new ArrayDeque<>();
        int N = 5000000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
                System.out.println("size1: " + L.size() + "size2: " + B.size());
            } else if (L.size() != 0 && operationNumber == 1) {
                assertEquals(L.removeLast(), B.removeLast());
                System.out.println("removeLast()");
                System.out.println("size1: " + L.size() + "size2: " + B.size());
            } else if (L.size() != 0 && (operationNumber == 2 || operationNumber == 5)) {
                assertEquals(L.get(0), B.get(0));
            } else if (L.size() != 0 && operationNumber == 3) {
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                B.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");
                System.out.println("size1: " + L.size() + "size2: " + B.size());
            } else if (L.size() != 0 && operationNumber == 4) {
                assertEquals(L.removeFirst(), B.removeFirst());
                System.out.println("removeFirst()");
                System.out.println("size1: " + L.size() + "size2: " + B.size());
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
}
