package egyeb;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

}
