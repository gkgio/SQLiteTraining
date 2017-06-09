package com.gkgio.android.sqlitetraining.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gkgio.android.sqlitetraining.AnimalsStorage;
import com.gkgio.android.sqlitetraining.R;
import com.gkgio.android.sqlitetraining.app.SQLiteTrainingApplication;
import com.gkgio.android.sqlitetraining.model.Animal;
import com.google.gson.Gson;

public class AddOrUpdateAnimalActivity extends AppCompatActivity {

    private AnimalsStorage animalsStorage;

    private EditText speciesEditText;
    private EditText ageEditText;
    private EditText nameEditText;
    private Button addButton;
    private EditText[] editTexts;
    private Animal animal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animalsStorage = ((SQLiteTrainingApplication) getApplication()).getAnimalsStorage();

        setContentView(R.layout.add_or_update_animal_activity);
        final Gson gson = new Gson();

        animal = gson.fromJson(getIntent().getStringExtra("Animal"), Animal.class);

        speciesEditText = (EditText) findViewById(R.id.species_edit_text);
        ageEditText = (EditText) findViewById(R.id.age_edit_text);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        addButton = (Button) findViewById(R.id.add_animal_button);
        editTexts = new EditText[]{speciesEditText, ageEditText, nameEditText};

        //если открыт для изменения
        if (animal != null) {
            speciesEditText.setText(animal.getSpecies());
            ageEditText.setText(String.valueOf(animal.getAge()));
            nameEditText.setText(animal.getName());
            addButton.setText(R.string.btn_update_animal);
            addButton.setEnabled(true);
        } else addButton.setText(R.string.add_animal);

        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcherImpl());
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animal == null) {
                    createAnimal();
                } else updateAnimal();
            }
        });
    }

    private void createAnimal() {
        String species = speciesEditText.getText().toString();
        int age = Integer.valueOf(ageEditText.getText().toString());
        String name = nameEditText.getText().toString();
        Animal localAnimal = new Animal(species, age, name);
        animalsStorage.addAnimal(localAnimal);
        finish();
    }

    private void updateAnimal() {
        String species = speciesEditText.getText().toString();
        int age = Integer.valueOf(ageEditText.getText().toString());
        String name = nameEditText.getText().toString();
        Animal localAnimal = new Animal(species, age, name);
        localAnimal.setId(animal.getId());
        animalsStorage.updateAnimal(localAnimal);
        finish();
    }

    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean buttonEnabled = true;
            for (EditText editText : editTexts) {
                if (TextUtils.isEmpty(editText.getText())) {
                    buttonEnabled = false;
                    break;
                }
            }
            addButton.setEnabled(buttonEnabled);
        }
    }
}