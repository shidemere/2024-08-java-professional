package ru.otus.hw;

import ru.otus.hw.runner.TestRunner;
import ru.otus.hw.test.UserTest;


public class Homework01 {
    public static void main(String[] args) throws Exception{
        System.out.println("Hello world!");
        TestRunner.prepare(UserTest.class);
    }
}