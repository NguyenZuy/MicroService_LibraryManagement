package com.example.project.ui.bookManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.entities.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, 0, categories);
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        }

        Category category = categories.get(position);

        TextView tvCategoryId = convertView.findViewById(R.id.tvCategoryId);
        TextView tvCategoryName = convertView.findViewById(R.id.tvCategoryName);

        tvCategoryId.setText(String.valueOf(category.getId())); // Assuming Category has a getId() method
        tvCategoryName.setText(category.getCategoryName()); // Assuming Category has a getCategoryName() method

        return convertView;
    }
}

