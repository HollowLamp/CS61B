package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> l1 = new AListNoResizing<>();
        BuggyAList<Integer> l2 = new BuggyAList<>();
        l1.addLast(4);l2.addLast(4);
        l1.addLast(5);l2.addLast(5);
        l1.addLast(6);l2.addLast(6);
        assertEquals(l1.removeLast(),l2.removeLast());
        assertEquals(l1.removeLast(),l2.removeLast());
        assertEquals(l1.removeLast(),l2.removeLast());
    }
    @Test
    public void test(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = L.size();
                int size2 = B.size();
                System.out.println("size1: " + size1 + "size2: " + size2);
                assertEquals(size1, size2);
            } else if (L.size() != 0 && operationNumber == 2){
                assertEquals(L.removeLast(),B.removeLast());
            } else if (L.size() != 0 && operationNumber == 3){
                assertEquals(L.getLast(), B.getLast());
            }
        }
    }
}
