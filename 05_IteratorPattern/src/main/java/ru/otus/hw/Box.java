package ru.otus.hw;

import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class Box {
    private final Matryoshka red;    // "red0", "red1", ..., "red9"
    private final Matryoshka green;
    private final Matryoshka blue;
    private final Matryoshka magenta;

    // expected: "red0", "green0", "blue0", "magenta0", "red1", "green1", "blue1", "magenta1",...
    public Iterator<String> getSmallFirstIterator() {
        return new Iterator<>() {

            private int iteratorCounter = 0;
            List<Iterator<String>> iterators = List.of(red.iterator(), green.iterator(), blue.iterator(), magenta.iterator());
            @Override
            public boolean hasNext() {
                return iterators.get(0).hasNext() || iterators.get(1).hasNext() || iterators.get(2).hasNext() || iterators.get(3).hasNext();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String result;
                switch (iteratorCounter) {
                    case 0:
                        if (iterators.get(0).hasNext()) {
                            result = iterators.get(0).next();
                            iteratorCounter++;
                            break;
                        }
                    case 1:
                        if (iterators.get(1).hasNext()) {
                            result = iterators.get(1).next();
                            iteratorCounter++;
                            break;
                        }
                    case 2:
                        if (iterators.get(2).hasNext()) {
                           result =  iterators.get(2).next();
                            iteratorCounter++;
                            break;
                        }
                    case 3:
                        if (iterators.get(3).hasNext()) {
                            result = iterators.get(3).next();
                            iteratorCounter = 0;
                            break;
                        }
                    default: throw new NoSuchElementException();
                }
                return result;
            }
        };
    }

    // expected: "red0", "red1", ..., "red9", "green0", "green1", ..., "green9", ...
    public Iterator<String> getColorFirstIterator() {
        return new Iterator<>() {
            private int iteratorCounter = 0;
            List<Iterator<String>> iterators = List.of(red.iterator(), green.iterator(), blue.iterator(), magenta.iterator());
            @Override
            public boolean hasNext() {
                return iterators.get(0).hasNext() || iterators.get(1).hasNext() || iterators.get(2).hasNext() || iterators.get(3).hasNext();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String result;
                switch (iteratorCounter) {
                    case 0:
                        if (iterators.get(0).hasNext()) {
                            result = iterators.get(0).next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 1:
                        if (iterators.get(1).hasNext()) {
                            result = iterators.get(1).next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 2:
                        if (iterators.get(2).hasNext()) {
                            result =  iterators.get(2).next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 3:
                        if (iterators.get(3).hasNext()) {
                            result = iterators.get(3).next();
                            break;
                        }
                    default: throw new NoSuchElementException();
                }
                return result;
            }
        };
    }
}
