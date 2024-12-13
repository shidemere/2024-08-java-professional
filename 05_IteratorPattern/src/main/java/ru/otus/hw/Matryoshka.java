package ru.otus.hw;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class Matryoshka implements Iterable<String>{
    // [0] - the smallest / [9] - the biggest
    private List<String> items;

    public Matryoshka(List<String> items) {
        this.items = items.stream()
                .sorted(Comparator.comparingInt(this::extractNumber))
                .toList();
    }

    @Override
    public Iterator<String> iterator() {
        // Сортируем элементы перед созданием итератора


        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < items.size();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                return items.get(currentIndex++);
            }
        };
    }

    private int extractNumber(String item) {
        // Извлекаем числовую часть строки для сортировки
        return Integer.parseInt(item.replaceAll("\\D+", ""));
    }
}
