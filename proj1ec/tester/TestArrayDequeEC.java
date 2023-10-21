package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void ArrayDequeTest(){
        StudentArrayDeque<Integer> st = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sl = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> history = new ArrayDequeSolution<>();
        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                st.addLast(randVal);
                sl.addLast(randVal);
                history.addLast("addLast("+randVal+")");
            } else if (st.size() != 0 && operationNumber == 1) {
                history.addLast("removeLast()");
                assertEquals(String.join("\n",history),st.removeLast(), sl.removeLast());
            } else if (st.size() != 0 && operationNumber == 3) {
                int randVal = StdRandom.uniform(0, 100);
                st.addFirst(randVal);
                sl.addFirst(randVal);
                history.addLast("addFirst("+randVal+")");
            } else if (st.size() != 0 && operationNumber == 4) {
                history.addLast("removeFirst()");
                assertEquals(String.join("\n",history), sl.removeFirst());
            }
        }
    }
}
