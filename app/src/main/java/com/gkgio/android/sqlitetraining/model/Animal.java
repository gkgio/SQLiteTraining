package com.gkgio.android.sqlitetraining.model;

import com.google.common.base.Objects;

public class Animal {

    private long id;
    private String species;
    private int age;
    private String name;

    public Animal(String species, int age, String name) {
        this.species = species;
        this.age = age;
        this.name = name;
    }

    public Animal() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Animal)) {
            return false;
        }
        Animal animal = (Animal) o;
        return id == animal.id &&
                age == animal.age &&
                Objects.equal(species, animal.species) &&
                Objects.equal(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, species, age, name);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("species", species)
                .add("age", age)
                .add("name", name)
                .toString();
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
