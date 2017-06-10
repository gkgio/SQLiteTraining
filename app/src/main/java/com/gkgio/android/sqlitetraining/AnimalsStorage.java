package com.gkgio.android.sqlitetraining;

import com.gkgio.android.sqlitetraining.db.AnimalsDao;
import com.gkgio.android.sqlitetraining.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalsStorage {

    private OnContentChangeListener onContentChangeListeners;
    private AnimalsDao animalsDao;

    public AnimalsStorage(AnimalsDao animalsDao) {
        this.animalsDao = animalsDao;
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animalsDao.getAnimals());
    }

    public void addAnimal(Animal animal) {
        animalsDao.insertAnimal(animal);
        onContentChangeListeners.onAnimalAdded(this, animal);
    }

    public void updateAnimal(Animal animal){
        animalsDao.updateAnimal(animal);
        onContentChangeListeners.onAnimalAdded(this, animal);
    }

    public void deleteAnimal(Animal animal){
        animalsDao.deleteAnimal(animal);
        onContentChangeListeners.onAnimalAdded(this, animal);
    }

    public void addOnContentChangeListener(OnContentChangeListener listener) {
        onContentChangeListeners = listener;
    }

    public void removeOnContentChangeListener() {
        onContentChangeListeners = null;
    }
}