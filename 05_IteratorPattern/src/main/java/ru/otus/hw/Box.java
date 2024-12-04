package ru.otus.hw;

import lombok.AllArgsConstructor;

import java.util.Iterator;
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
            Iterator<String> redIterator = red.iterator();
            Iterator<String> greenIterator = green.iterator();
            Iterator<String> blueIterator = blue.iterator();
            Iterator<String> magentaIterator = magenta.iterator();

            @Override
            public boolean hasNext() {
                return redIterator.hasNext() || greenIterator.hasNext() || blueIterator.hasNext() || magentaIterator.hasNext();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String result;
                switch (iteratorCounter) {
                    case 0:
                        if (redIterator.hasNext()) {
                            result = redIterator.next();
                            iteratorCounter++;
                            break;
                        }
                    case 1:
                        if (greenIterator.hasNext()) {
                            result = greenIterator.next();
                            iteratorCounter++;
                            break;
                        }
                    case 2:
                        if (blueIterator.hasNext()) {
                           result =  blueIterator.next();
                            iteratorCounter++;
                            break;
                        }
                    case 3:
                        if (magentaIterator.hasNext()) {
                            result = magentaIterator.next();
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
            Iterator<String> redIterator = red.iterator();
            Iterator<String> greenIterator = green.iterator();
            Iterator<String> blueIterator = blue.iterator();
            Iterator<String> magentaIterator = magenta.iterator();

            @Override
            public boolean hasNext() {
                return redIterator.hasNext() || greenIterator.hasNext() || blueIterator.hasNext() || magentaIterator.hasNext();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String result;
                switch (iteratorCounter) {
                    case 0:
                        if (redIterator.hasNext()) {
                            result = redIterator.next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 1:
                        if (greenIterator.hasNext()) {
                            result = greenIterator.next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 2:
                        if (blueIterator.hasNext()) {
                            result =  blueIterator.next();
                            break;
                        } else {
                            iteratorCounter++;
                        }
                    case 3:
                        if (magentaIterator.hasNext()) {
                            result = magentaIterator.next();
                            break;
                        }
                    default: throw new NoSuchElementException();
                }
                return result;
            }
        };
    }
}
