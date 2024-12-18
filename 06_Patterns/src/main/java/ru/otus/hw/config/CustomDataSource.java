package ru.otus.hw.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomDataSource {

    private static final CustomDataSource INSTANCE = new CustomDataSource();

    public static CustomDataSource getInstance() {
        return INSTANCE;
    }

    // Реализация Singleton через DoubleChecking. Проверяющий счёл что это излишне, поэтому сделал способ выше
//    private static volatile CustomDataSource instance;
//
//    public static CustomDataSource getInstance() {
//        CustomDataSource localInstance = instance;
//        if (localInstance == null) {
//            synchronized (CustomDataSource.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    instance = localInstance = new CustomDataSource();
//                }
//            }
//        }
//        return localInstance;
//    }

    public void beginTransaction() {
        System.out.println("Начало транзакции");
    }

    public void closeTransaction() {
        System.out.println("Конец транзакции");
    }
}
