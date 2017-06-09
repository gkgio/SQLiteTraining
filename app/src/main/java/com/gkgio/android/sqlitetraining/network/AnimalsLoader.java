package com.gkgio.android.sqlitetraining.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.gkgio.android.sqlitetraining.AnimalsStorage;
import com.gkgio.android.sqlitetraining.OnContentChangeListener;
import com.gkgio.android.sqlitetraining.model.Animal;

import java.util.List;

public class AnimalsLoader extends AsyncTaskLoader<List<Animal>> implements OnContentChangeListener {

    private final AnimalsStorage animalsStorage;
    private List<Animal> cachedResult;

   public AnimalsLoader(Context context, AnimalsStorage animalsStorage) {
        super(context);
       this.animalsStorage = animalsStorage;
       animalsStorage.addOnContentChangeListener(this);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (cachedResult == null || takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<Animal> data) {
        super.deliverResult(data);
        cachedResult = data;
    }

    @Override
    public List<Animal> loadInBackground() {
        return animalsStorage.getAnimals();
    }

    @Override
    public void onReset() {
        super.onReset();
        animalsStorage.removeOnContentChangeListener();
    }

    @Override
    public void onAnimalAdded(AnimalsStorage sender, Animal animal) {
        onContentChanged();
    }
}
