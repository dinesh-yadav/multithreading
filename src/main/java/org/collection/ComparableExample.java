package org.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparableExample {
    public static void main(String[] args) {
        Employee emp1 = new Employee(1, "Ram", 1000);
        Employee emp2 = new Employee(0, "Ramesh", 2000);
        Employee emp3 = new Employee(3, "Ramu", 1500);

        List<Employee> employees = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));
        System.out.println(employees);
        employees.sort(null);
        System.out.println(employees);
    }
}

class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private int salary;

    public Employee(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public int compareTo(Employee o) {
        return Integer.compare(this.id, o.getId());
        // both are same.
       // return this.id - o.getId();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
