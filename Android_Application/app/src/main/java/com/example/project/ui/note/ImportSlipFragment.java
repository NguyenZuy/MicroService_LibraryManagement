package com.example.project.ui.note;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.DetailImportSlip;
import com.example.project.entities.ImportSlip;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportSlipFragment extends Fragment {
    private ListView lvImportSlip;
    private ImportSlipAdapter adapter;
    private List<ImportSlip> importSlipList = new ArrayList<>();
    private RestfulAPIService restfulAPIService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_import_slip, container, false);
        lvImportSlip = rootView.findViewById(R.id.listViewImportSlip);

        adapter = new ImportSlipAdapter(getContext(), importSlipList);
        lvImportSlip.setAdapter(adapter);

        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
        fetchImportSlips();

        lvImportSlip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImportSlip importSlip = importSlipList.get(position);
                showDetailImportSlipDialog(importSlip.getId());
            }
        });

        return rootView;
    }

    private void fetchImportSlips() {
        restfulAPIService.GetAllImportSlip().enqueue(new Callback<List<ImportSlip>>() {
            @Override
            public void onResponse(Call<List<ImportSlip>> call, Response<List<ImportSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    importSlipList.clear();
                    importSlipList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load import slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ImportSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetailImportSlipDialog(Integer importSlipId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_import_slip, null);
        builder.setView(dialogView);

        ListView lvDetailImportSlip = dialogView.findViewById(R.id.listViewDetailImportSlip);
        List<DetailImportSlip> detailImportSlipList = new ArrayList<>();
        DetailImportSlipAdapter detailAdapter = new DetailImportSlipAdapter(getContext(), detailImportSlipList);
        lvDetailImportSlip.setAdapter(detailAdapter);

        restfulAPIService.GetDetailImportSlip(importSlipId).enqueue(new Callback<List<DetailImportSlip>>() {
            @Override
            public void onResponse(Call<List<DetailImportSlip>> call, Response<List<DetailImportSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailImportSlipList.clear();
                    detailImportSlipList.addAll(response.body());
                    detailAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load detail import slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailImportSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
