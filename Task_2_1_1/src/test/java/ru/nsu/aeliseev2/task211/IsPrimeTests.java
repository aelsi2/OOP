package ru.nsu.aeliseev2.task211;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IsPrimeTests {
    @Test
    void testNegative() {
        boolean result = PrimeChecker.isPrime(-5);
        Assertions.assertFalse(result);
    }

    @Test
    void test0() {
        boolean result = PrimeChecker.isPrime(0);
        Assertions.assertFalse(result);
    }

    @Test
    void test1() {
        boolean result = PrimeChecker.isPrime(1);
        Assertions.assertFalse(result);
    }

    @Test
    void test2() {
        boolean result = PrimeChecker.isPrime(2);
        Assertions.assertTrue(result);
    }

    @Test
    void test3() {
        boolean result = PrimeChecker.isPrime(3);
        Assertions.assertTrue(result);
    }

    @Test
    void test4() {
        boolean result = PrimeChecker.isPrime(4);
        Assertions.assertFalse(result);
    }

    @Test
    void testPrime() {
        boolean result = PrimeChecker.isPrime(109);
        Assertions.assertTrue(result);
    }

    @Test
    void testNonPrime() {
        boolean result = PrimeChecker.isPrime(240);
        Assertions.assertFalse(result);
    }
}
