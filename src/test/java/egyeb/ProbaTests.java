package egyeb;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProbaTests {

    @Test
    void java9() {
        String name = null;
        String s = Optional.ofNullable(name)
                .or(() -> Optional.of("anonymous")).get();

        assertTrue(s.startsWith("a"));
    }

    @Test
    void ize() {

    }


    @Test
    @Description("Gas Station")
    void gasStation() {

        List<Integer> A = List.of(1, 2);
        List<Integer> B = List.of(2, 1);
        int actual = canCompleteCircuit(A, B);

        assertEquals(1, actual);
    }

    private int canCompleteCircuit(final List<Integer> A, final List<Integer> B) {
        for (int i = 0; i < A.size(); i++) {
            int count = A.size();
            int tank = 0;
            int j = i;
            while (count != 0) {
                tank += A.get(j % A.size()) - B.get(j % A.size());
                if (tank < 0)
                    break;
                j++;
                count--;
            }
            if (count == 0)
                return i;
        }
        return -1;
    }


}
