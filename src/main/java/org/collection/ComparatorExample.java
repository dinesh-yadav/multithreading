package org.collection;

import java.util.*;

public class ComparatorExample {
    public static void main(String[] args) {
        Student st1 = new Student(1, "Dinesh");
        Student st2 = new Student(2, "Ramesh");
        Student st3 = new Student(3, "Suresh");
        Student st4 = new Student(3, "Harish");
        Student st5 = new Student(3, "Harry");
        List<Student> students = new ArrayList<>(Arrays.asList(st1, st2, st3, st4, st5));

        Collections.sort(students, (a, b) -> a.getId() - b.getId());
        System.out.println(students);

        students.sort((a, b) -> b.getId() - a.getId());
        System.out.println(students);

        Comparator<Student> comparator = Comparator.comparing(Student::getId).thenComparing(Student::getName);
        students.sort(comparator);
        System.out.println(students);

        students.sort(comparator.reversed());
        System.out.println(students);

        Comparator<Student> comparator1 = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.getId() - o2.getId() < 0) {
                    return 1;
                } else if (o1.getId() - o2.getId() > 0) {
                    return -1;
                } else {
                    return o1.getName().length() - o2.getName().length();
                }
            }
        };
        students.sort(comparator1);
        System.out.println(students);
    }
}


