package ru.ncedu.sa4ek;

/**
 * Created by sa4ek on 16.11.14.
 */
public class Employee {
    private int id;
    private String fio;
    private String position;
    private String office;
    private String tel;
    private double salary;


    @Override
    public String toString() {
        return String.valueOf(id) + '\t' + fio + "\t\t\t" + position + "\t\t\t\t\t\t\t" + office + "\t\t\t" + tel + "\t\t\t" + salary;
    }

    public String getFio(){
        return fio;
    }

    public Employee(Integer id, String fio, String position, String office, String tel, Double salary) {
        this.id = id;
        this.fio = fio;
        this.position = position;
        this.office = office;
        this.tel = tel;
        this.salary = salary;
    }
}
