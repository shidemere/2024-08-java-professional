package ru.otus.hw.runner;

import ru.otus.hw.annotation.AfterSuite;
import ru.otus.hw.annotation.BeforeSuite;
import ru.otus.hw.annotation.Disabled;
import ru.otus.hw.annotation.Test;
import ru.otus.hw.test.AbstractTest;

import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    private static int beforeSuiteCounter = 0;
    private static int afterSuiteCounter = 0;
    private static final Map<Integer, List<Method>> testPriorityHashMap = new HashMap<>();
    private static final List<Method> activeTest = new ArrayList<>();
    private static final Map<Method, String> disabledTest = new HashMap<>();

    /* Параметризован чтобы убрать CodeSmells */
    public static void prepare(Class<? extends AbstractTest> clazz) throws Exception {
        checkDisabled(clazz);

        Method beforeMethod = null;
        Method afterMethod = null;
        for (Method method : activeTest) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                processBeforeSuite(method, !Objects.isNull(beforeMethod));
                beforeMethod = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                processTest(method);
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                processAfterSuite(method, !Objects.isNull(afterMethod));
                afterMethod = method;
            }
        }
        if (beforeMethod != null) {
            beforeMethod.invoke(null);
        }
        List<Integer> priorityList = testPriorityHashMap.keySet().stream().sorted(Comparator.reverseOrder()).toList();
        int passedTests = 0;
        int failedTests = 0;
        /*
         * Оказывается checked исключения нельзя выбрасывать из стрима без вложенного try/catch. Я об этом не знал.
         * Попытка переписать код ниже в стрим будет распознано компилятором как ошибка и не даст собрать такую программу.
         */
        for (Integer priority : priorityList) {
            List<Method> methodList = testPriorityHashMap.get(priority);
            for (Method method : methodList) {
                try {
                    method.invoke(null);
                    passedTests++;
                } catch (Exception e) {
                    failedTests++;
                    System.err.println(method.getName() + ": " + e.getMessage());
                }
            }
        }
        if (afterMethod != null) {
            afterMethod.invoke(null);
        }
        System.out.println("Passed: " + passedTests + "; Failed: " + failedTests);
        disabledTest.forEach((key, value) -> System.err.println(key.getName() + " was disabled. Reason: " + value));
    }

    private static void checkDisabled(Class<? extends AbstractTest> clazz) {
        Disabled disabled = clazz.getAnnotation(Disabled.class);
        if (disabled != null) {
            System.err.println("Disabled test: " + clazz.getSimpleName() + ". Reason: " + disabled.reason());
        }

        /* Предполагается что тестовые методы будут public */
        Method[] allMethods = clazz.getMethods();

        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Disabled.class)) {
                disabledTest.put(method, method.getAnnotation(Disabled.class).reason());
            } else {
                activeTest.add(method);
            }
        }
    }

    /**
     * Проверка корректности аннотации {@link BeforeSuite}
     */
    private static void processBeforeSuite(Method method, boolean alreadyExist) {
        /* Проверка что аннотация встречается 1 раз */
        if (alreadyExist) {
            throw new IllegalArgumentException("Аннотация @BeforeSuite должна быть использована 1 раз на класс");
        }

        /* Проверка что аннотации не встречаются на одном и том же месте */
        if (method.isAnnotationPresent(AfterSuite.class) || method.isAnnotationPresent(Test.class)) {
            throw new IllegalArgumentException("Аннотация @BeforeSuite не может стоять в паре с @AfterSuite или @Test");
        }
    }

    /**
     * Проверка корректности аннотации {@link Test}
     */
    private static void processTest(Method method) {
        /* Проверка что аннотации не встречаются на одном и том же месте */
        if (method.isAnnotationPresent(BeforeSuite.class) || method.isAnnotationPresent(AfterSuite.class)) {
            throw new IllegalArgumentException("Аннотация @Test  не может стоять в паре с @BeforeSuite или @AfterSuite");
        }

        int priority = method.getAnnotation(Test.class).priority();
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("Приоритет не может быть больше 10 или меньше 1");
        }
        List<Method> methodList = testPriorityHashMap.get(priority);
        if (methodList == null) {
            methodList = new ArrayList<>();
        }
        methodList.add(method);
        testPriorityHashMap.put(priority, methodList);
    }

    /**
     * Проверка корректности аннотации {@link AfterSuite}
     */
    private static void processAfterSuite(Method method, boolean alreadyExist) {
        /* Проверка что аннотация встречается 1 раз */
        if (alreadyExist) {
            throw new IllegalArgumentException("Аннотация @BeforeSuite должна быть использована 1 раз на класс");
        }

        /* Проверка что аннотации не встречаются на одном и том же месте */
        if (method.isAnnotationPresent(BeforeSuite.class) || method.isAnnotationPresent(Test.class)) {
            throw new IllegalArgumentException("Аннотация @AfterSuite не может стоять в паре с @BeforeSuite или @Test");
        }
    }


}
