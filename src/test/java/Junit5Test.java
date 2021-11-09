import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.condition.JRE.*;
import static org.junit.jupiter.api.condition.OS.*;

@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
class Junit5Test {


    @RepeatedTest(10)
    void repeatedTest() {
        // ...
    }

    @Nested
    class RepeatedTestsDemo {


        @BeforeEach
        void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
            int currentRepetition = repetitionInfo.getCurrentRepetition();
            int totalRepetitions = repetitionInfo.getTotalRepetitions();
            String methodName = testInfo.getTestMethod().get().getName();
//            logger.info(String.format("About to execute repetition %d of %d for %s", //
//                    currentRepetition, totalRepetitions, methodName));
        }

        @RepeatedTest(10)
        void repeatedTest() {
            // ...
        }

        @RepeatedTest(5)
        void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
            assertEquals(5, repetitionInfo.getTotalRepetitions());
        }

        @RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
        @DisplayName("Repeat!")
        void customDisplayName(TestInfo testInfo) {
            assertEquals("Repeat! 1/1", testInfo.getDisplayName());
        }

        @RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
        @DisplayName("Details...")
        void customDisplayNameWithLongPattern(TestInfo testInfo) {
            assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
        }

        @RepeatedTest(value = 5, name = "Wiederholung {currentRepetition} von {totalRepetitions}")
        void repeatedTestInGerman() {
            // ...
        }

    }

    @Test
    @DisplayName("2+2")
    void onCreate() {

        int i = 4;
        assertEquals(4, i, "The optional failure message is now the last parameter");

        assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
                + "to avoid constructing complex messages unnecessarily.");
        assertAll("person",
                () -> assertEquals(true, 1 < 2),
                () -> assertEquals(2, 1 + 1)
        );

    }

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",
                () -> {
                    String firstName = "Jane";
                    assertNotNull(firstName);

                    // Executed only if the previous assertion is valid.
                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("e"))
                    );
                },
                () -> {
                    // Grouped assertion, so processed independently
                    // of results of first name assertions.
                    String lastName = "Doe";
                    assertNotNull(lastName);

                    // Executed only if the previous assertion is valid.
                    assertAll("last name",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e"))
                    );
                }
        );
    }

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(
                ArithmeticException.class,
                () -> Math.floorDiv(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(Duration.ofNanos(1_000_000), () -> {
            long sum = 0;
            for (int i = 0; i < 100_000; i++) {
                sum += i;
            }
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(
                Duration.ofMillis(1),
                () -> {
                    return "a result";
                });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(Duration.ofMillis(1000), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    @Test
    @Disabled("Disabled until bug #42 has been resolved")
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
//        assertTimeoutPreemptively(ofMillis(10), () -> {
//            // Simulate task that takes more than 10 ms.
//            new CountDownLatch(1).await();
//        });
    }

    private static String greeting() {
        return "Hello, World!";
    }

    @Nested
    @DisplayName("Nested test class")
    class valid {

        @Test
        @DisplayName("Nested function")
        void miaTokom() {
            int j = 10;
            assertEquals(10, j);
        }

    }

    @Nested
    @DisplayName("Operating System Conditions")
    static
    class OperatingSystemConditions {

        @Test
        @EnabledOnOs({LINUX, MAC})
        void onLinuxOrMac() {
            // ...
        }

        @Test
        @DisabledOnOs(WINDOWS)
        void notOnWindows() {
            // ...
        }

        @Test
        @EnabledOnOs(MAC)
        void onlyOnMacOs() {
            // ...
        }

        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @Test
        @EnabledOnOs(MAC)
        @interface TestOnMac {
        }


    }


    @Nested
    @DisplayName(" Java Runtime Environment Conditions")
    class JavaRuntimeEnvironmentConditions {
        @Test
        @EnabledOnJre(JAVA_8)
        void onlyOnJava8() {
            // ...
        }

        @Test
        @EnabledOnJre({JAVA_9, JAVA_10})
        void onJava9Or10() {
            // ...
        }

        @Test
        @EnabledForJreRange(min = JAVA_9, max = JAVA_11)
        void fromJava9to11() {
            // ...
        }

        @Test
        @EnabledForJreRange(min = JAVA_9)
        void fromJava9toCurrentJavaFeatureNumber() {
            // ...
        }

        @Test
        @EnabledForJreRange(max = JAVA_11)
        void fromJava8To11() {
            // ...
        }

        @Test
        @DisabledOnJre(JAVA_9)
        void notOnJava9() {
            // ...
        }

        @Test
        @DisabledForJreRange(min = JAVA_9, max = JAVA_11)
        void notFromJava9to11() {
            // ...
        }

        @Test
        @DisabledForJreRange(min = JAVA_9)
        void notFromJava9toCurrentJavaFeatureNumber() {
            // ...
        }

        @Test
        @DisabledForJreRange(max = JAVA_11)
        void notFromJava8to11() {
            // ...
        }
    }

    @Nested
    @DisplayName("Custom Conditions")
    static
    class CustomConditions {
        @Test
        @EnabledIf("customCondition")
        void enabled() {
            // ...
        }

        @Test
        @DisabledIf("customCondition")
        void disabled() {
            // ...
        }

        boolean customCondition() {
            return true;
        }

        @Nested
        class ExternalCustomConditionDemo {

            @Test
            @EnabledIf("bruzsal.betfairapingandriod.ExternalCondition#customCondition")
            void enabled() {
                // ...
            }

        }
    }


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class OrderedTestsDemo {

        @Test
        @Order(1)
        void nullValues() {
            // perform assertions against null values
        }

        @Test
        @Order(2)
        void emptyValues() {
            // perform assertions against empty values
        }

        @Test
        @Order(3)
        void validValues() {
            // perform assertions against valid values
        }

    }


    @Nested
    @TestClassOrder(ClassOrderer.OrderAnnotation.class)
    class OrderedNestedTestClassesDemo {

        @Nested
        @Order(1)
        class PrimaryTests {

            @Test
            void test1() {
            }
        }

        @Nested
        @Order(2)
        class SecondaryTests {

            @Test
            void test2() {
            }
        }
    }


}

class ExternalCondition {

    static boolean customCondition() {
        return true;
    }
}