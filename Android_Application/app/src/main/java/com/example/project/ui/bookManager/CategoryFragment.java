package com.example.project.ui.bookManager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.Category;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private ListView lvCategory;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryList;
    private RestfulAPIService restfulAPIService;
    private Button addBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        lvCategory = rootView.findViewById(R.id.lvCategory);
        addBtn = rootView.findViewById(R.id.btnAddCategory);

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        lvCategory.setAdapter(categoryAdapter);

        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        fetchCategories();

        addBtn.setOnClickListener(v -> showAddDialog());
        // Thêm sự kiện nhấn chọn vào ListView
        lvCategory.setOnItemClickListener((parent, view, position, id) -> {
            Category selectedCategory = categoryList.get(position);
            showUpdateDialog(selectedCategory, position);
        });

        return rootView;
    }

    private void fetchCategories() {
        restfulAPIService.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList.clear();
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddDialog() {
        showCategoryDialog(getContext(), "Add Category", null, categoryName -> {
            Category newCategory = new Category();
            newCategory.setCategoryName(categoryName);
            // Gửi dữ liệu qua service
            Call<List<Category>> call = restfulAPIService.addCategory(newCategory);
            call.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful()) {
                        categoryList.add(newCategory);
                        categoryAdapter.notifyDataSetChanged();
                        // Cập nhật danh sách category từ server nếu cần
                        List<Category> updatedCategories = response.body();
                        categoryList.clear();
                        categoryList.addAll(updatedCategories);
                        categoryAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Add successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to add category: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Toast.makeText(getContext(), "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void showCategoryDialog(Context context, String title, String initialName, OnCategoryAddedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if (initialName != null) {
            input.setText(initialName);
        }
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String categoryName = input.getText().toString();
            if (listener != null) {
                listener.onCategoryAdded(categoryName);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    interface OnCategoryAddedListener {
        void onCategoryAdded(String categoryName);
    }

    private void showUpdateDialog(Category category, int position) {
        showCategoryUpdateDialog(getContext(), "Update Category", category.getCategoryName(), updatedCategoryName -> {
            // Kiểm tra xem tên category có trùng không
            boolean isDuplicate = false;
            for (Category cat : categoryList) {
                if (cat.getCategoryName().equals(updatedCategoryName)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                Toast.makeText(getContext(), "Category name already exists", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật tên category
                category.setCategoryName(updatedCategoryName);
                categoryAdapter.notifyDataSetChanged();

                // Gửi dữ liệu cập nhật qua service
                Call<List<Category>> call = restfulAPIService.updateCategory(category);
                call.enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful()) {
                            // Cập nhật danh sách category từ server nếu cần
                            List<Category> updatedCategories = response.body();
                            categoryList.clear();
                            categoryList.addAll(updatedCategories);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Failed to update category: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showCategoryUpdateDialog(Context context, String title, String initialName, OnCategoryUpdateAddedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if (initialName != null) {
            input.setText(initialName);
        }
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String categoryName = input.getText().toString();
            if (listener != null) {
                listener.onCategoryAdded(categoryName);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    interface OnCategoryUpdateAddedListener {
        void onCategoryAdded(String categoryName);
    }

}

