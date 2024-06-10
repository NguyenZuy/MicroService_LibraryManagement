package com.example.project.ui.bookManager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.Category;
import com.example.project.entities.Publisher;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherFragment extends Fragment {

    private ListView lvPublisher;
    private Button addButton;
    private PublisherAdapter adapter;
    private RestfulAPIService restfulAPIService;
    private List<Publisher> publishersList = new ArrayList<>(); // Danh sách publishers

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publisher, container, false);
        lvPublisher = view.findViewById(R.id.lvPublisher);
        addButton = view.findViewById(R.id.btnAddPublisher);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        // Khởi tạo adapter với danh sách publishers trống ban đầu
        adapter = new PublisherAdapter(requireContext(), publishersList);
        lvPublisher.setAdapter(adapter);

        fetchPublishers();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPublisherDialog();
            }
        });

        // Thêm sự kiện nhấn chọn vào ListView
        lvPublisher.setOnItemClickListener((parent, view1, position, id) -> {
            Publisher selectedPublisher = publishersList.get(position);
            showUpdateDialog(selectedPublisher, position);
        });

        return view;
    }

    private void fetchPublishers() {
        restfulAPIService.getAllPublishers().enqueue(new Callback<List<Publisher>>() {
            @Override
            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    publishersList.clear();
                    publishersList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publisher>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddPublisherDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_publisher, null);
        builder.setView(dialogView);

        final EditText etPublisherName = dialogView.findViewById(R.id.etPublisherName);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        final AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String publisherName = etPublisherName.getText().toString();
                if (!publisherName.isEmpty()) {
                    boolean isDuplicate = false;
                    for (Publisher publisher : publishersList) {
                        if (publisher.getPublisherName().equalsIgnoreCase(publisherName)) {
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (isDuplicate) {
                        Toast.makeText(requireContext(), "Publisher name already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Publisher newPublisher = new Publisher();
                        newPublisher.setPublisherName(publisherName);

                        restfulAPIService.addPublisher(newPublisher).enqueue(new Callback<List<Publisher>>() {
                            @Override
                            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    publishersList.clear();
                                    publishersList.addAll(response.body());
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(requireContext(), "Publisher added successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(), "Failed to add publisher", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Publisher>> call, Throwable t) {
                                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }
                } else {
                    etPublisherName.setError("Publisher name cannot be empty");
                }
            }
        });

        dialog.show();
    }

    private void showUpdateDialog(Publisher publisher, int position) {
        showPublisherUpdateDialog(getContext(), "Update Publisher", publisher.getPublisherName(), updatedPublisherName -> {
            // Kiểm tra xem tên publisher có trùng không
            boolean isDuplicate = false;
            for (Publisher pub : publishersList) {
                if (pub.getPublisherName().equals(updatedPublisherName)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                Toast.makeText(getContext(), "Publisher name already exists", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật tên publisher
                publisher.setPublisherName(updatedPublisherName);
                adapter.notifyDataSetChanged();

                // Gửi dữ liệu cập nhật qua service
                restfulAPIService.updatePublisher(publisher).enqueue(new Callback<List<Publisher>>() {
                    @Override
                    public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Cập nhật danh sách publisher từ server nếu cần
                            List<Publisher> updatedPublishers = response.body();
                            publishersList.clear();
                            publishersList.addAll(updatedPublishers);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Failed to update publisher: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Publisher>> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showPublisherUpdateDialog(Context context, String title, String initialName, OnPublisherUpdateAddedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if (initialName != null) {
            input.setText(initialName);
        }
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String updatedPublisherName = input.getText().toString();
            if (listener != null) {
                listener.onPublisherUpdated(updatedPublisherName);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    interface OnPublisherUpdateAddedListener {
        void onPublisherUpdated(String updatedPublisherName);
    }
}

