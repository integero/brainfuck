package com.apach.my;

import static org.junit.Assert.assertEquals;
public class BrainfuckTest {
    @org.junit.Test
    public void brainfuck() throws Exception {
        String st = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        Brainfuck brF = new Brainfuck();
        String actual = brF.brainfuck(st);
        String expected = "Hello World!\n";
        assertEquals(expected, actual);
    }
}