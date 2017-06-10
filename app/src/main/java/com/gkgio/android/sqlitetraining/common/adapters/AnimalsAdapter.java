package com.gkgio.android.sqlitetraining.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkgio.android.sqlitetraining.R;
import com.gkgio.android.sqlitetraining.model.Animal;
import com.gkgio.android.sqlitetraining.ui.AnimalActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gio
 */
public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.CategoryItemViewHolder> {

    private final List<Animal> animalList;
    private final WeakReference<Context> refContext;

    public AnimalsAdapter(Context context) {
        refContext = new WeakReference<>(context);
        animalList = new ArrayList<>();
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_list_item, parent, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, int position) {

        final Animal animal = animalList.get(position);
        final Context context = refContext.get();

        if (context != null) {
            holder.speciesTextView.setText(composeString(context, R.string.species, animal.getSpecies()));
            holder.ageTextView.setText(composeString(context, R.string.age, String.valueOf(animal.getAge())));
            holder.nameTextView.setText(composeString(context, R.string.name, animal.getName()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AnimalActivity) context).openAddORUpdateActivity(animal);
                }
            });
        }
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