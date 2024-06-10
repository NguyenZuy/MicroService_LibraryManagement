package com.example.project.ui.bookManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.Supplier;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierFragment extends Fragment {
    private ListView lvSupplier;
    private Button btnAddSupplier;
    private SupplierAdapter adapter;
    private List<Supplier> supplierList = new ArrayList<>();
    private RestfulAPIService restfulAPIService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_supplier, container, false);
        lvSupplier = rootView.findViewById(R.id.listViewSupplier);
        btnAddSupplier = rootView.findViewById(R.id.btnAddSupplier);

        adapter = new SupplierAdapter(requireContext(), supplierList);
        lvSupplier.setAdapter(adapter);

        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
        fetchSuppliers();

        btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddSupplierDialog();
            }
        });

        lvSupplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Supplier selectedSupplier = supplierList.get(position);
                showUpdateSupplierDialog(selectedSupplier);
            }
        });

        return rootView;
    }

    private void fetchSuppliers() {
        restfulAPIService.getAllSuppliers().enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    supplierList.clear();
                    supplierList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "Failed to load suppliers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddSupplierDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_supplier, null);
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etSupplierName);
        final EditText etEmail = dialogView.findViewById(R.id.etSupplierEmail);
        final EditText etPhoneNumber = dialogView.findViewById(R.id.etSupplierPhoneNumber);
        final EditText etAddress = dialogView.findViewById(R.id.etSupplierAddress);
        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerSupplierStatus);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmitSupplier);

        // Thiết lập giá trị cho Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.supplier_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String address = etAddress.getText().toString();
                String status = spinnerStatus.getSelectedItem().toString();

                if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || status.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Supplier newSupplier = new Supplier();
                    newSupplier.setId(0);
                    newSupplier.setName(name);
                    newSupplier.setEmail(email);
                    newSupplier.setPhoneNumber(phoneNumber);
                    newSupplier.setAddress(address);
                    newSupplier.setStatus(status);

                    restfulAPIService.addSupplier(newSupplier).enqueue(new Callback<List<Supplier>>() {
                        @Override
                        public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                            if (response.isSuccessful()) {
                                supplierList.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(requireContext(), "Supplier added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Failed to add supplier", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Supplier>> call, Throwable t) {
                            Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private void showUpdateSupplierDialog(Supplier supplier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_supplier, null);
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etSupplierName);
        final EditText etEmail = dialogView.findViewById(R.id.etSupplierEmail);
        final EditText etPhoneNumber = dialogView.findViewById(R.id.etSupplierPhoneNumber);
        final EditText etAddress = dialogView.findViewById(R.id.etSupplierAddress);
        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerSupplierStatus);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmitSupplier);

        // Thiết lập giá trị cho Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.supplier_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Điền thông tin nhà cung cấp vào các trường
        etName.setText(supplier.getName());
        etEmail.setText(supplier.getEmail());
        etPhoneNumber.setText(supplier.getPhoneNumber());
        etAddress.setText(supplier.getAddress());
        // Thiết lập giá trị cho spinnerStatus
        if (supplier.getStatus() != null) {
            int spinnerPosition = adapter.getPosition(supplier.getStatus());
            spinnerStatus.setSelection(spinnerPosition);
        }

        final AlertDialog dialog = builder.create();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String address = etAddress.getText().toString();
                String status = spinnerStatus.getSelectedItem().toString();

                if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || status.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    supplier.setName(name);
                    supplier.setEmail(email);
                    supplier.setPhoneNumber(phoneNumber);
                    supplier.setAddress(address);
                    supplier.setStatus(status);

                    restfulAPIService.updateSupplier(supplier).enqueue(new Callback<Supplier>() {
                        @Override
                        public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                            if (response.isSuccessful()) {
                                int index = supplierList.indexOf(supplier);
                                supplierList.set(index, response.body());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(requireContext(), "Supplier updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Failed to update supplier", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Supplier> call, Throwable t) {
                            Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        dialog.show();
    }
}