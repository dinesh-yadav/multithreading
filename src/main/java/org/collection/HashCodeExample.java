package org.collection;

import java.util.Objects;

public class HashCodeExample {
    public static void main(String[] args) {
        Student st1 = new Student(1, "Dinesh");
        Student st2 = new Student(2, "Ramesh");
        Student st3 = new Student(1, "Dinesh");

        System.out.println(st1);
        System.out.println(st2);
        System.out.println(st3);
        System.out.println(st1.equals(st3));
        System.out.println(st1.equals(st2));
    }
}

class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Student student = (Student)obj;
        return id == student.getId() && Objects.equals(name, student.getName());

    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name;
    }
}
