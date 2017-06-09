package com.gkgio.android.sqlitetraining.common.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkgio.android.sqlitetraining.AnimalsStorage;
import com.gkgio.android.sqlitetraining.R;
import com.gkgio.android.sqlitetraining.model.Animal;
import com.gkgio.android.sqlitetraining.ui.AddOrUpdateAnimalActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gio
 */
public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.CategoryItemViewHolder> {

    private final List<Animal> animalList;
    private final Context context;
    private AnimalsStorage animalsStorage;

    public AnimalsAdapter(Context context, AnimalsStorage animalsStorage) {
        this.context = context;
        animalList = new ArrayList<>();
        this.animalsStorage = animalsStorage;
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_list_item, parent, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, int position) {

        final Animal animal = animalList.get(position);

        holder.speciesTextView.setText(composeString(context, R.string.species, animal.getSpecies()));
        holder.ageTextView.setText(composeString(context, R.string.age, String.valueOf(animal.getAge())));
        holder.nameTextView.setText(composeString(context, R.string.name, animal.getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_what_action_animal)
                        .setNeutralButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                animalsStorage.deleteAnimal(animal);
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.dialog_update, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Gson gson = new Gson();
                                Intent intent = new Intent(context, AddOrUpdateAnimalActivity.class);
                                final String jsonAnimal = gson.toJson(animal, Animal.class);
                                intent.putExtra("Animal", jsonAnimal);
                                context.startActivity(intent);
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
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void setAnimals(List<Animal> animals) {
        animalList.clear();
        animalList.addAll(animals);
        notifyDataSetChanged();
    }

    private static String composeString(Context context, int propertyResId, String propertyValue) {
        return context.getString(propertyResId) + ": " + propertyValue;
    }

    class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        final TextView speciesTextView;
        final TextView ageTextView;
        final TextView nameTextView;

        public CategoryItemViewHolder(View itemView) {
            super(itemView);
            speciesTextView = (TextView) itemView.findViewById(R.id.species_text_view);
            ageTextView = (TextView) itemView.findViewById(R.id.age_text_view);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
        }
    }
}

