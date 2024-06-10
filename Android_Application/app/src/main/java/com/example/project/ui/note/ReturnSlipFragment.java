package com.example.project.ui.note;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.DetailReturnSlip;
import com.example.project.entities.ReturnSlip;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnSlipFragment extends Fragment {
    private ListView lvReturnSlip;
    private ReturnSlipAdapter adapter;
    private List<ReturnSlip> returnSlipList = new ArrayList<>();
    private RestfulAPIService restfulAPIService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_return_slip, container, false);
        lvReturnSlip = rootView.findViewById(R.id.listViewReturnSlip);
        adapter = new ReturnSlipAdapter(getContext(), returnSlipList);
        lvReturnSlip.setAdapter(adapter);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        lvReturnSlip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReturnSlip selectedreturnSlip = returnSlipList.get(position);
                showDetailReturnSlipDialog(selectedreturnSlip.getId());
            }
        });
        
        fetchReturnSlips();
        return rootView;
    }

    private void fetchReturnSlips() {
        restfulAPIService.GetAllReturnSlip().enqueue(new Callback<List<ReturnSlip>>() {
            @Override
            public void onResponse(Call<List<ReturnSlip>> call, Response<List<ReturnSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    returnSlipList.clear();
                    returnSlipList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load return slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReturnSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetailReturnSlipDialog(int loanSlipId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_return_slip, null);
        builder.setView(dialogView);

        ListView lvDetailReturnSlip = dialogView.findViewById(R.id.listViewDetailReturnSlip);

        List<DetailReturnSlip> detailReturnSlipList = new ArrayList<>();
        DetailReturnSlipAdapter adapter = new DetailReturnSlipAdapter(getContext(), detailReturnSlipList);
        lvDetailReturnSlip.setAdapter(adapter);

        // Gọi API để lấy danh sách DetailReturnSlip
        restfulAPIService.GetDetailReturnSlipByLoanId(loanSlipId).enqueue(new Callback<List<DetailReturnSlip>>() {
            @Override
            public void onResponse(Call<List<DetailReturnSlip>> call, Response<List<DetailReturnSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailReturnSlipList.clear();
                    detailReturnSlipList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load detail return slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailReturnSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final AlertDialog dialog = builder.create();

        dialog.show();
    }

}

