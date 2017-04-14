package ru.spbau.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    private static final Predicate<Integer> FIRST = new Predicate<Integer>() {
        @Override
        Boolean apply(Integer s) {
            final int c = 0;
            return s == c;
        }
    };
    private static final Predicate<Integer> SECOND = new Predicate<Integer>() {
        @Override
        Boolean apply(Integer s) {
            final int c = 0;
            return s / c == c;
        }
    };
    @Test
    public void testApply() throws Exception {
        Predicate<String> u = new Predicate<String>() {
            @Override
            Boolean apply(String s) {
                return s.length() > 0;
            }
        };
        assertTrue(u.apply("Test String"));
        assertFalse(u.apply(""));
        assertEquals(u.apply("Test string"), Predicate.ALWAYS_TRUE.apply(null));
        assertEquals(u.apply(""), Predicate.ALWAYS_FALSE.apply(null));
    }

    @Test
    public void testNot() throws Exception {
        Predicate<String> u = new Predicate<String>() {
            @Override
            Boolean apply(String s) {
                return s.length() > 0;
            }
        };
        assertFalse(u.not().apply("Test String"));
        assertTrue(u.not().apply(""));
    }

    @Test
    public void testOr() throws Exception {
        Predicate<Integer> u = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer s) {
                final int c = 10;
                return s == c;
            }
        };
        Predicate<Integer> v = new Predicate<Integer>() {
            @Override
            Boolean apply(Integer s) {
                final int c = 5;
                return s == c;
            }
        };
        Predicate<Integer> or = u.or(v);
        final Integer check5 = 5;
        assertTrue(or.apply(check5));
        final Integer check10 = 10;
        assertTrue(or.apply(check10));
        final Integer check6 = 6;
        assertFalse(or.apply(check6));

    }

    @Test
    public void testOrLazy() throws Exception {
        Predicate<Integer> or = FIRST.or(SECOND);
        final Integer check5 = 0;
        assertTrue(or.apply(check5));
    }
    @Test
    public void testAnd() throws Exception {
        Predicate<String> u = new Predicate<String>() {
            @Override
            Boolean apply(String s) {
                return s.length() > 0;
            }
        };
        Predicate<String> v = new Predicate<String>() {
            @Override
            Boolean apply(String s) {
                final int l = 11;
                return s.length() <= l;
            }
        };
        Predicate<String> and = u.and(v);
        assertTrue(and.apply("Test string"));
        assertFalse(u.and(v).apply("Test   string"));
        and = v.and(u);
        assertTrue(and.apply("Test string"));
        assertFalse(u.and(v).apply(""));
    }
    @Test
    public void testAndLazy() throws Exception {
        Predicate<Integer> and = FIRST.and(SECOND);
        final Integer check5 = 10;
        assertFalse(and.apply(check5));
    }


}
