## Паттерны. Часть 1 \\ ДЗ 

Нужно реализовать два итератора. 
Вот заготовка для ДЗ. 

```java
public final class Matryoshka {
  
  // [0] - the smallest / [9] - the biggest
  private final List<String> items; 

}

public final class Box {

  private final Matryoshka red;    // "red0", "red1", ..., "red9"
  private final Matryoshka green;
  private final Matryoshka blue;
  private final Matryoshka magenta;
  
  // expected: "red0", "green0", "blue0", "magenta0", "red1", "green1", "blue1", "magenta1",... 
  public Iterator<String> getSmallFirstIterator() {
    // TODO
  }
  
  // expected: "red0", "red1", ..., "red9", "green0", "green1", ..., "green9", ... 
  public Iterator<String> getColorFirstIterator() {
    // TODO
  }
  
}
```