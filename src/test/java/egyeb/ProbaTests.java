package egyeb;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProbaTests {

    CountDownLatch latch = new CountDownLatch(1);
    ProcessHandle processHandle;
    CompletableFuture<Integer> future = new CompletableFuture<>();

    @Test
    void java9() {
        String name = null;
        String s = Optional.ofNullable(name)
                .or(() -> Optional.of("anonymous")).get();

        assertTrue(s.startsWith("a"));
    }

    @Test
    void ize() {

        interface f {
            static void mm() {
                return;
            }

            default void mc() {

            }
        }

        class A {
            A(int i) {
            }

            public static int main() {
                return 'B';
            }
        }

        class B extends A implements f {

            B(int i) {
                super(i);
            }

            public static int main() {
                return 'B';
            }
        }

        A a = new A(1);
        A b = new B(2);

        assertEquals(A.main(), A.main());

    }

    @Test
    void vizsgaTesztek() {

        class Animal {
        }
        class Goat extends Animal {
        }
        Animal animal = (Goat) new Animal();
        Goat goat = (Goat) new Animal();
    }

    @Test
    void teszt() throws IOException {
        CountDownLatch cdl = new CountDownLatch(2);
        ProcessHandle.allProcesses()
                .map(ProcessHandle::info)
                .filter(info -> info.command().isPresent() && info.command().get().contains("Vivaldi"))
                .peek(info -> System.out.println(LocalTime.ofInstant(info.startInstant().get(), ZoneId.of("GMT+1"))))
                .forEach(System.out::println);


        double d = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .map(Double::valueOf)
                .peek(System.out::println)
                .collect(Collectors.averagingDouble(Double::doubleValue));
        System.out.println(d);

        class Covid {
            String country;
            Integer cases;
            Integer deaths;
            Integer pupulation;

            public Covid(String country, Integer cases, Integer deaths, Integer pupulation) {
                this.country = country;
                this.cases = cases;
                this.deaths = deaths;
                this.pupulation = pupulation;
            }

            public Integer getDeaths() {
                return deaths;
            }

            public Integer getPupulation() {
                return pupulation;
            }

            public int getCases() {
                return cases;
            }

            public String getCountry() {
                return country;
            }
        }


    }


    @Test
    void stream() {

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
