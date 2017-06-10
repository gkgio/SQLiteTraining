package com.gkgio.android.sqlitetraining.model;

import java.io.Serializable;

public class Animal implements Serializable {

    private long id;
    private String species;
    private int age;
    private String name;

    public Animal(String species, int age, String name) {
        this.species = species;
        this.age = age;
        this.name = name;
    }

    public Animal() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (id != animal.id) return false;
        if (age != animal.age) return false;
        if (species != null ? !species.equals(animal.species) : animal.species != null)
            return false;
        return name != null ? name.equals(animal.name) : animal.name == null;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", species='" + species + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + species.hashCode();
        result = 31 * result + age;
        result = 31 * result + name.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}