package com.xzit;

public class Department {
    private int id;
    private String name;

    public Department() {
        System.out.println("instance me without agrs.....");
    }

    public Department(int id, String name) {
        System.out.println("instance me with two agrs.....");
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
