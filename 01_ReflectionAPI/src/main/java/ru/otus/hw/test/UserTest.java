package ru.otus.hw.test;

import ru.otus.hw.annotation.AfterSuite;
import ru.otus.hw.annotation.BeforeSuite;
import ru.otus.hw.annotation.Disabled;
import ru.otus.hw.annotation.Test;


public class UserTest  extends AbstractTest {
    @BeforeSuite
    public static void initMethod() {
        System.out.println("Init method do work");
    }

    @Test(priority = 1)
    public static void methodOne() {
        System.out.println("Method one do work");
    }

    @Test(priority = 2)
    public static void methodTwo() {
        System.out.println("Method two do work");
    }

    @Test
    public static void methodFive() {
        System.out.println("Method five do work");
    }

    @Test
    public static void anotherMethodFive() {
        System.out.println("Method five do work");
    }

    @Test(priority = 8)
    @Disabled(reason = "Method 8 shouldn't work")
    public static void methodEight() {
        System.out.println("Method eight should be disabled");
    }

    @Test(priority = 10)
    public static void methodTen() {
        System.out.println("Method ten do work");
    }

    @AfterSuite
    public static void afterMethod() {
        System.out.println("After method do work");
    }
}
