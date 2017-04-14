package ru.spbau.mit;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Functional2Test {
    @Test
    public void testApply() throws Exception {
        Functional2<String, String, Integer> v = new Functional2<String, String, Integer>() {
            @Override
            Integer apply(String s, String s2) {
                return s.length() + s2.length();
            }
        };
        final Integer check = 10;
        assertEquals(check, v.apply("Test", "String"));
    }

    @Test
    public void testCompose() throws Exception {
        Functional2<Integer, Integer, Integer> u = new Functional2<Integer, Integer, Integer>() {
            @Override
            Integer apply(Integer integer, Integer integer2) {
                return integer * integer2;
            }
        };
        Functional1<Integer, Integer> v = new Functional1<Integer, Integer>() {
          @Override
          Integer apply(Integer integer) {
              final int c = 2;
              return integer * c;
          }
        };
        final Integer check = 100;
        final Integer param1 = 25;
        final Integer param2 = 2;
        assertEquals(u.compose(v).apply(param1, param2), check);
    }

    @Test
    public void testBind1() throws Exception {
        Functional2<Integer, Integer, Integer> u = new Functional2<Integer, Integer, Integer>() {
            @Override
            Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        };
        final int param1 = 20;
        Functional1<Integer, Integer> v = u.bind1(param1);
        final Integer check = 50;
        final int param2 = 30;
        assertEquals(v.apply(param2), check);
    }

    @Test
    public void testBind2() throws Exception {
        Functional2<Integer, Integer, Integer> u = new Functional2<Integer, Integer, Integer>() {
            @Override
            Integer apply(Integer integer, Integer integer2) {
                return integer - integer2;
            }
        };
        final int param2 = 20;
        Functional1<Integer, Integer> v = u.bind2(param2);
        final Integer check = 10;
        final int param1 = 30;
        assertEquals(v.apply(param1), check);
    }

    @Test
    public void testCurry() throws Exception {
        Functional2<Integer, List<Integer>, Integer> u =
                new Functional2<Integer, List<Integer>, Integer>() {
            @Override
            Integer apply(Integer integer, List<Integer> integers) {
                Integer sum = 0;
                for (Integer i : integers) {
                    sum += i * integer;
                }
                return sum;
            }
        };
        Functional1<Integer, Functional1<List<Integer>, Integer>> curry = u.curry();
        final int param1 = 10;
        final Functional1<List<Integer>, Integer> func = curry.apply(param1);
        final Integer[] in = new Integer[] {1, 2, 3, 4};
        final Integer check = 100;
        assertEquals(func.apply(Arrays.asList(in)), check);
    }
}
