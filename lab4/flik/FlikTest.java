package flik;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void test() {
        int c = 50000;
        while (c-- > 0) {
            assertTrue(Flik.isSameNumber(c, c));
        }
    }
}
