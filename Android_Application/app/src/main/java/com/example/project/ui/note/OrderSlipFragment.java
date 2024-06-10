package com.example.project.ui.note;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.entities.DetailImportSlip;
import com.example.project.entities.DetailLoanSlip;
import com.example.project.entities.DetailOrderSlip;
import com.example.project.entities.ImportSlip;
import com.example.project.entities.NewBook;
import com.example.project.entities.OrderSlip;
import com.example.project.entities.Supplier;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSlipFragment extends Fragment {
    private ListView lvOrderSlip;
    private Button btnCreate;
    private OrderSlipAdapter adapter;
    private List<OrderSlip> orderSlipList = new ArrayList<>();
    private RestfulAPIService restfulAPIService;
    private OrderSlip orderSlipTemp = new OrderSlip();
    private List<DetailOrderSlip> detailOrderSlipList = new ArrayList<>();

    private List<DetailOrderSlip> selectedDetailOrderSlips = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_slip, container, false);
        lvOrderSlip = rootView.findViewById(R.id.listViewOrderSlip);
        btnCreate = rootView.findViewById(R.id.btnCreateOrderSlip);
        adapter = new OrderSlipAdapter(getContext(), orderSlipList);
        lvOrderSlip.setAdapter(adapter);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
        fetchOrderSlips();
        setEvent();
        return rootView;
    }

    private void setEvent() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOrderDialog();
            }
        });

        lvOrderSlip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderSlip selectedLoanSlip = orderSlipList.get(position);
                showDetailOrderDialog(selectedLoanSlip);
            }
        });
    }

    private void fetchOrderSlips() {
        restfulAPIService.GetAllOrderSlip().enqueue(new Callback<List<OrderSlip>>() {
            @Override
            public void onResponse(Call<List<OrderSlip>> call, Response<List<OrderSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderSlipList.clear();
                    orderSlipList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load order slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_order_slip, null);
        builder.setView(dialogView);

        final EditText etOrderDate = dialogView.findViewById(R.id.etOrderDate);
        final Spinner spinnerSupplier = dialogView.findViewById(R.id.spinnerSupplier);
        Button btnAdd = dialogView.findViewById(R.id.btnAddOrderSlip);
        final AlertDialog dialog = builder.create();

        // Thiết lập ngày tháng hiện tại cho etOrderDate
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        etOrderDate.setText(currentDate);

        // Đặt etOrderDate thành không thể chỉnh sửa
        etOrderDate.setEnabled(false);

        // Gọi API để lấy danh sách nhà cung cấp
        restfulAPIService.getSuppliersByActiveStatus().enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Supplier> suppliers = response.body();
                    List<String> supplierNames = new ArrayList<>();
                    for (Supplier supplier : suppliers) {
                        supplierNames.add(supplier.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, supplierNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSupplier.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load suppliers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderDate = etOrderDate.getText().toString();
                Log.d("orderDate", orderDate);
                String supplierName = spinnerSupplier.getSelectedItem().toString();
                if (orderDate.isEmpty() || supplierName.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                orderSlipTemp.setId(0);
                orderSlipTemp.setOrderDate(orderDate);
                orderSlipTemp.setSupplierName(supplierName);
                // Save to db here
                showAddOrderDetailDialog();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showAddOrderDetailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_order_detail, null);
        builder.setView(dialogView);

        final Spinner spinnerBookName = dialogView.findViewById(R.id.spinnerBookName);
        final EditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);
        Button btnAddOrderDetail = dialogView.findViewById(R.id.btnAddOrderDetail);

        Button btnAddListDetail = dialogView.findViewById(R.id.btnAddListDetail);
        Button btnDeleteListDetail = dialogView.findViewById(R.id.btnDeleteListDetail);
        final AlertDialog dialog = builder.create();

        // Gọi API để lấy danh sách sách
        restfulAPIService.GetBooks().enqueue(new Callback<List<NewBook>>() {
            @Override
            public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewBook> books = response.body();
                    List<String> bookTitles = new ArrayList<>();
                    for (NewBook book : books) {
                        bookTitles.add(book.getTitle());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bookTitles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBookName.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load books", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NewBook>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập giá trị cho spinnerStatus
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"Not Imported", "Imported"});
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        btnAddListDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = spinnerBookName.getSelectedItem().toString();
                String quantityStr = etQuantity.getText().toString();
                String status = spinnerStatus.getSelectedItem().toString();

                if (bookName.isEmpty() || quantityStr.isEmpty() || status.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);

                // Kiểm tra bookName có bị trùng không
                for (DetailOrderSlip slip : detailOrderSlipList) {
                    if (slip.getBookName().equals(bookName)) {
                        Toast.makeText(getContext(), "Book name already exists in temp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                DetailOrderSlip detailOrderSlip = new DetailOrderSlip();
                detailOrderSlip.setBookName(bookName);
                detailOrderSlip.setQuantity(quantity);
                detailOrderSlip.setStatus(status);

                detailOrderSlipList.add(detailOrderSlip);
                Toast.makeText(v.getContext(), "Added to temp", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteListDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailOrderSlipList.isEmpty()) {
                    detailOrderSlipList.remove(detailOrderSlipList.size() - 1);
                    Toast.makeText(v.getContext(), "Removed from temp", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "There are nothing in temp", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detailOrderSlipList.size() <= 0){
                    Toast.makeText(getContext(), "Don't have detail data", Toast.LENGTH_SHORT).show();
                    return;
                }

                restfulAPIService.AddOrderSlip(orderSlipTemp).enqueue(new Callback<OrderSlip>() {
                    @Override
                    public void onResponse(Call<OrderSlip> call, Response<OrderSlip> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            OrderSlip orderSlip =response.body();
                            for (DetailOrderSlip detailOrderSlip : detailOrderSlipList) {
                                Log.d("orderSlipId", orderSlip.getId() + "");
                                detailOrderSlip.setOrderSlipId(orderSlip.getId());
                                restfulAPIService.AddDetailOrderSlip(detailOrderSlip).enqueue(new Callback<DetailOrderSlip>() {
                                    @Override
                                    public void onResponse(Call<DetailOrderSlip> call, Response<DetailOrderSlip> response) {
                                        if (response.isSuccessful() && response.body() != null) {

                                            Toast.makeText(getContext(), "Add detail order successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to add detail order", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DetailOrderSlip> call, Throwable t) {
                                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(getContext(), "Failed to add order", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderSlip> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDetailOrderDialog(OrderSlip orderSlip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_order, null);
        builder.setView(dialogView);

        ListView lvDetailOrder = dialogView.findViewById(R.id.lvDetailOrder);
        Button btnImport = dialogView.findViewById(R.id.btnImport);

        DetailOrderSlipAdapter detailAdapter = new DetailOrderSlipAdapter(getContext(), detailOrderSlipList);
        lvDetailOrder.setAdapter(detailAdapter);

        restfulAPIService.GetByOrderId(orderSlip.getId()).enqueue(new Callback<List<DetailOrderSlip>>() {
            @Override
            public void onResponse(Call<List<DetailOrderSlip>> call, Response<List<DetailOrderSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detailOrderSlipList.clear();
                    detailOrderSlipList.addAll(response.body());
                    detailAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load detail order slips", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailOrderSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final AlertDialog dialog = builder.create();
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DetailOrderSlip> selectedItems = detailAdapter.getSelectedItems();
                // Hiển thị dữ liệu đã chọn
                for (DetailOrderSlip item : selectedItems) {
                    Log.d("SelectedItem", "Book Name: " + item.getBookName() + ", Quantity: " + item.getQuantity() + ", Status: " + item.getStatus());
                }
                Log.d("SelectedOrderSlip", "Order Date: " + orderSlip.getOrderDate() + ", Supplier Name: " + orderSlip.getSupplierName());

                if (selectedItems.size() == 0){
                    Toast.makeText(getContext(), "Please, choose detail order", Toast.LENGTH_SHORT).show();
                }
                ImportSlip importSlip = new ImportSlip();
                importSlip.setIdOrderSlip(orderSlip.getId());
                importSlip.setSupplierName(orderSlip.getSupplierName());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date currentDate = new Date();
                String formattedDate = dateFormat.format(currentDate);
                importSlip.setImportDate(formattedDate);
                importSlip.setId(0);

                restfulAPIService.AddImportSlip(importSlip).enqueue(new Callback<ImportSlip>() {
                    @Override
                    public void onResponse(Call<ImportSlip> call, Response<ImportSlip> response) {
                        if (response.isSuccessful() && response.body() != null){
                            ImportSlip importSlipTemp = response.body();
                            for (DetailOrderSlip item : selectedItems) {
                                DetailImportSlip detailImportSlip = new DetailImportSlip();
                                detailImportSlip.setImportSlipId(importSlipTemp.getId());
                                detailImportSlip.setBookName(item.getBookName());
                                detailImportSlip.setQuantity(item.getQuantity());

                                restfulAPIService.AddDetailImportSlip(detailImportSlip).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                            restfulAPIService.UpdateStatus(orderSlip.getId(), item.getBookName()).enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        Toast.makeText(getContext(), "Update detail order status successful", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getContext(), "Failed to update detail order status", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Toast.makeText(getContext(), "Add detail import successfully", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Toast.makeText(getContext(), "Add import successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImportSlip> call, Throwable t) {
//                        System.out.println(t.getMessage());
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
