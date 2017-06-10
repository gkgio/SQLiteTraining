package com.gkgio.android.sqlitetraining.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gkgio.android.sqlitetraining.AnimalsStorage;
import com.gkgio.android.sqlitetraining.R;
import com.gkgio.android.sqlitetraining.app.SQLiteTrainingApplication;
import com.gkgio.android.sqlitetraining.common.adapters.AnimalsAdapter;
import com.gkgio.android.sqlitetraining.model.Animal;
import com.gkgio.android.sqlitetraining.network.AnimalsLoader;

import java.util.List;

public class AnimalActivity extends AppCompatActivity {
    private static final int ANIMALS_LOADER_ID = 1;
    private static final String INTENT_ANIMAL_PARAM = "Animal";

    private AnimalsStorage animalsStorage;
    private AnimalsAdapter animalsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animalsStorage = ((SQLiteTrainingApplication) getApplication()).getAnimalsStorage();

        setContentView(R.layout.activity_main);

        animalsAdapter = new AnimalsAdapter(this);

        RecyclerView rvAnimals = (RecyclerView) findViewById(R.id.rvAnimals);
        rvAnimals.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvAnimals.setLayoutManager(new LinearLayoutManager(this));
        rvAnimals.setAdapter(animalsAdapter);

        getSupportLoaderManager().initLoader(ANIMALS_LOADER_ID, null, new AnimalsLoaderCallbacks());
    }

    private class AnimalsLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Animal>> {

        @Override
        public Loader<List<Animal>> onCreateLoader(int id, Bundle args) {
            return new AnimalsLoader(AnimalActivity.this, animalsStorage);
        }

        @Override
        public void onLoadFinished(Loader<List<Animal>> loader, List<Animal> data) {
            animalsAdapter.setAnimals(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Animal>> loader) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animals_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_animal_menu_item:
                // добавляем животное
                Intent intent = new Intent(this, AddOrUpdateAnimalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAddORUpdateActivity(final Animal animal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_what_action_animal)
                .setNeutralButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        animalsStorage.deleteAnimal(animal);
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.dialog_update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AnimalActivity.this, AddOrUpdateAnimalActivity.class);
                        intent.putExtra(INTENT_ANIMAL_PARAM, animal);
                        startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.dialog_nothing, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }
}