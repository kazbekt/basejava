package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    private static int[] createArray() {
        int size = (int) (Math.random() * 10);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 10);
        }
        return array;
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, digit) -> a * 10 + digit);
    }

    private static ArrayList<Integer> createList() {
        return Arrays.stream(createArray()).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.summingInt(Integer::intValue),
                        sum -> integers.stream()
                                .filter(n -> (sum % 2 == 0) == (n % 2 != 0))
                                .collect(Collectors.toList())
                ));
    }

    public static void main(String[] args) {
        int[] array = createArray();
        System.out.println(Arrays.toString(array));
        System.out.println(minValue(array));
        List<Integer> list = createList();
        System.out.println(list);
        System.out.println(oddOrEven(list));

    }
}
