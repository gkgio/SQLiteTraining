package com.gkgio.android.sqlitetraining.db;

import com.gkgio.android.sqlitetraining.model.Animal;

import java.util.List;

public interface AnimalsDao {

    long insertAnimal(Animal animal);

    List<Animal> getAnimals();

    Animal getAnimalById(long id);

    int updateAnimal(Animal animal);

    int deleteAnimal(Animal animal);
}