package org.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamExample {
    public static void main(String[] args) {
        System.out.println("This is a placeholder for StreamExample.");

        int[] arr = {1, 2, 3, 4, 5};
        double avg = Arrays.stream(arr).average().getAsDouble();
        System.out.println("Average: " + avg);
        List<Integer> intList = Arrays.stream(arr).boxed().collect(Collectors.toList());
        avg = intList.stream().mapToDouble(Integer::doubleValue).average().getAsDouble();
        System.out.println("Average: " + avg);
        System.out.println(Arrays.stream(arr).boxed().reduce((a, b) -> a*b).get());
        System.out.println(Arrays.stream(arr).reduce((a, b) -> a*b).getAsInt());

        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        List<List<Integer>> list1 =
                list.stream()
                .collect(Collectors.groupingBy(i -> i%2 == 0,
                Collectors.toList())).entrySet().stream().map(x -> x.getValue())
                        .collect(Collectors.toList());
        System.out.println(list1);
        System.out.println(list);

        String[] str = {"pat", "tap", "tree", "eat", "tea"};
        Collection<List<String>> values = Arrays.asList(str).stream()
                .collect(
                        Collectors.groupingBy(
                                s -> (
                Arrays.stream(s.toLowerCase().split("")).sorted()).collect(
                                Collectors.toList())))
                .values();
        System.out.println(values);


        String s = "aabbcaacddeeffaaaa";
        char c = s.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(ch -> ch, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println(c);

        String check = "aa";
        Long count = IntStream.range(0, s.length() - check.length() + 1)
                .filter(i -> s.substring(i, i + check.length()).equals(check)).count();
        System.out.println(count);

        int len = s.length();
        String mid = IntStream.range(0, len)
                .filter(i -> (len %2 == 0) ? (i == len/2 -1 || i == len/2) : (i == len/2))
                .mapToObj(s::charAt)
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append).toString();
        System.out.println(mid);

    }
}
