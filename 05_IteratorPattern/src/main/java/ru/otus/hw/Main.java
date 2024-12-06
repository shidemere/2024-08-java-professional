package ru.otus.hw;

import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> redColors = List.of("red1","red6", "red2", "red0",  "red3", "red4", "red5",  "red7", "red8", "red9");
        List<String> blueColors = List.of("blue9" ,"blue0",  "blue2", "blue3", "blue4", "blue5", "blue6", "blue1", "blue7", "blue8");
        List<String> greenColors = List.of("green0", "green1", "green9", "green2", "green3", "green4", "green5", "green6",  "green8", "green7" );
        List<String> magentaColors = List.of("magenta0", "magenta1", "magenta2", "magenta3", "magenta4", "magenta5", "magenta6", "magenta7", "magenta8", "magenta9");

        Matryoshka red = new Matryoshka(redColors);
        Matryoshka blue = new Matryoshka(blueColors);
        Matryoshka green = new Matryoshka(greenColors);
        Matryoshka magenta = new Matryoshka(magentaColors);

        Box box = new Box(red,  green, blue, magenta);
        Iterator<String> smallFirstIterator = box.getSmallFirstIterator();
        while (smallFirstIterator.hasNext()) {
            System.out.println(smallFirstIterator.next());
        }
        System.out.println("---------------------------------------------------");
        Iterator<String> colorFirstIterator = box.getColorFirstIterator();
        while (colorFirstIterator.hasNext()) {
            System.out.println(colorFirstIterator.next());
        }
    }
}