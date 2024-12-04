package ru.otus.hw;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class Matryoshka implements Iterable<String>{
    // [0] - the smallest / [9] - the biggest
    private List<String> items;

    @Override
    public Iterator<String> iterator() {
        // Сортируем элементы перед созданием итератора
        List<String> sortedItems = items.stream()
                .sorted(Comparator.comparingInt(this::extractNumber))
                .toList();

        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < sortedItems.size();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                return sortedItems.get(currentIndex++);
            }
        };
    }

    private int extractNumber(String item) {
        // Извлекаем числовую часть строки для сортировки
        return Integer.parseInt(item.replaceAll("\\D+", ""));
    }
}
