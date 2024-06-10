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
import com.example.project.entities.DetailLoanSlip;
import com.example.project.entities.DetailReturnSlip;
import com.example.project.entities.LoanSlip;
import com.example.project.entities.NewBook;
import com.example.project.entities.ReturnSlip;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanSlipFragment extends Fragment {

    private ListView lvLoanSlip;
    private Button btnCreate;
    private LoanSlipAdapter adapter;
    private List<LoanSlip> loanSlipList = new ArrayList<>();
    private RestfulAPIService restfulAPIService;
    private List<DetailLoanSlip> detailLoanSlipList = new ArrayList<>();

    private LoanSlip loanSlip;

    private String email, phoneNumber, address, firstName, lastName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loan_slip, container, false);
        lvLoanSlip = rootView.findViewById(R.id.listViewLoanSlip);
        btnCreate = rootView.findViewById(R.id.btnAddLoanSlip);

        loanSlipList = new ArrayList<>();
        adapter = new LoanSlipAdapter(getContext(), loanSlipList);
        lvLoanSlip.setAdapter(adapter);
        loanSlip = new LoanSlip();

        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        fetchLoanSlips();

        SetEvent();

        return rootView;
    }

    private void SetEvent(){
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLoanDialog();
            }
        });

        lvLoanSlip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanSlip selectedLoanSlip = loanSlipList.get(position);
                int loanSlipId = selectedLoanSlip.getId();

                // Gọi API để lấy danh sách DetailLoanSlip theo loanSlipId
                restfulAPIService.GetDetailLoanSlipByLoanId(loanSlipId).enqueue(new Callback<List<DetailLoanSlip>>() {
                    @Override
                    public void onResponse(Call<List<DetailLoanSlip>> call, Response<List<DetailLoanSlip>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<DetailLoanSlip> detailLoanSlipList = response.body();
                            showDetailLoanSlipDialog(selectedLoanSlip, detailLoanSlipList);
                        } else {
                            Toast.makeText(getContext(), "Failed to load detail loan slips", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DetailLoanSlip>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void fetchLoanSlips() {
        restfulAPIService.GetAllLoanSlip().enqueue(new Callback<List<LoanSlip>>() {
            @Override
            public void onResponse(Call<List<LoanSlip>> call, Response<List<LoanSlip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loanSlipList.clear();
                    loanSlipList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load loan slips", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<LoanSlip>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddLoanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_loan_slip, null);
        builder.setView(dialogView);

        final EditText etEmail = dialogView.findViewById(R.id.etEmail);
        final EditText etPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);
        final EditText etAddress = dialogView.findViewById(R.id.etAddress);
        final EditText etFirstName = dialogView.findViewById(R.id.etFirstName);
        final EditText etLastName = dialogView.findViewById(R.id.etLastName);
        Button btnNext = dialogView.findViewById(R.id.btnNext);

        final AlertDialog dialog = builder.create();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                address = etAddress.getText().toString();
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();

                if (email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {

                    dialog.dismiss();
                    showAddDetailLoanDialog();
                }
            }
        });

        dialog.show();
    }

    private void showAddDetailLoanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_add_loan_slip, null);
        builder.setView(dialogView);

        final Spinner spinnerBookName = dialogView.findViewById(R.id.spinnerBookName);
        final EditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        final EditText etReturnDate = dialogView.findViewById(R.id.etReturnDate);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        Button btnFinish = dialogView.findViewById(R.id.btnFinish);

        // Thiết lập DatePickerDialog cho etReturnDate
        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Định dạng ngày tháng năm và đặt vào EditText
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
                        String formattedDate = dateFormat.format(selectedDate.getTime());
                        etReturnDate.setText(formattedDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Gọi API để lấy danh sách sách
        Call<List<NewBook>> call = restfulAPIService.GetBooks();
        call.enqueue(new Callback<List<NewBook>>() {
            @Override
            public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewBook> books = response.body();
                    List<String> bookNames = new ArrayList<>();
                    for (NewBook book : books) {
                        bookNames.add(book.getTitle());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bookNames);
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

        final AlertDialog dialog = builder.create();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String bookName = spinnerBookName.getSelectedItem().toString();
                    String quantityStr = etQuantity.getText().toString();
                    String returnDateStr = etReturnDate.getText().toString();

                    // Kiểm tra các giá trị input có null hoặc rỗng không
                    if (quantityStr.isEmpty() || returnDateStr.isEmpty()) {
                        // Hiển thị thông báo lỗi cho người dùng
                        Toast.makeText(v.getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Kiểm tra bookName có bị trùng không
                    for (DetailLoanSlip slip : detailLoanSlipList) {
                        if (slip.getBookName().equals(bookName)) {
                            Toast.makeText(v.getContext(), "Tên sách đã tồn tại trong danh sách", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    int quantity = Integer.parseInt(quantityStr);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Sử dụng định dạng ISO 8601
                    Date borrowDate = new Date();
                    Date returnDate = dateFormat.parse(returnDateStr);
                    String borrowDateStr = dateFormat.format(borrowDate);
                    Log.d("date", returnDate.toString());

                    if (returnDate.after(borrowDate)) {
                        detailLoanSlipList.add(new DetailLoanSlip(0, bookName, quantity, borrowDateStr, returnDateStr, "Not Return"));
                        Toast.makeText(v.getContext(), "Detail loan slip added successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Return date must be greater than today", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle the error, e.g., show a message to the user
                    Toast.makeText(v.getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle the error, e.g., show a message to the user
                    Toast.makeText(v.getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailLoanSlipList.isEmpty()) {
                    detailLoanSlipList.remove(detailLoanSlipList.size() - 1);
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SaveData();
            }
        });

        dialog.show();
    }

    private void SaveData() {
        loanSlip.setId(0);
        loanSlip.setEmail(email);
        loanSlip.setPhoneNumber(phoneNumber);
        loanSlip.setAddress(address);
        loanSlip.setFirstName(firstName);
        loanSlip.setLastName(lastName);

        // Save to db here
        restfulAPIService.AddLoanSlip(loanSlip).enqueue(new Callback<LoanSlip>() {
            @Override
            public void onResponse(Call<LoanSlip> call, Response<LoanSlip> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoanSlip loanSlip = response.body();
                    Log.d("loanSlipId1", loanSlip.getId() + "");
                    for (DetailLoanSlip detailLoanSlip : detailLoanSlipList) {
                        Log.d("loanSlipId2", loanSlip.getId() + "");
                        detailLoanSlip.setLoanSlipId(loanSlip.getId());
                        restfulAPIService.AddDetailLoanSlip(detailLoanSlip).enqueue(new Callback<List<DetailLoanSlip>>() {
                            @Override
                            public void onResponse(Call<List<DetailLoanSlip>> call, Response<List<DetailLoanSlip>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Toast.makeText(getContext(), "Add detail loan successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to add detail loan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<DetailLoanSlip>> call, Throwable t) {
                                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to add loan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoanSlip> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetailLoanSlipDialog(LoanSlip loanSlip, List<DetailLoanSlip> detailLoanSlipList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_loan_slip, null);
        builder.setView(dialogView);

        ListView lvDetailLoanSlip = dialogView.findViewById(R.id.listViewDetailLoanSlip);
        Button btnReturn = dialogView.findViewById(R.id.btnReturn);

        DetailLoanSlipAdapter detailAdapter = new DetailLoanSlipAdapter(getContext(), detailLoanSlipList);
        lvDetailLoanSlip.setAdapter(detailAdapter);

        AlertDialog dialog = builder.create();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)                                         {
                List<DetailLoanSlip> selectedDetails = detailAdapter.getSelectedDetailLoanSlips();
                // Xử lý dữ liệu loanSlip và selectedDetails tại đây
                processReturnData(loanSlip, selectedDetails);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void processReturnData(LoanSlip loanSlip, List<DetailLoanSlip> selectedDetails) {
        // Xử lý dữ liệu loanSlip và selectedDetails tại đây
        // Ví dụ: Hiển thị dữ liệu, lưu vào database, hoặc gọi API khác
        // In ra dữ liệu của LoanSlip
        if (selectedDetails.size() > 0){

//            Log.d("LoanSlip", "ID: " + loanSlip.getId());
//            Log.d("LoanSlip", "Email: " + loanSlip.getEmail());
//            Log.d("LoanSlip", "Phone Number: " + loanSlip.getPhoneNumber());
//            Log.d("LoanSlip", "Address: " + loanSlip.getAddress());
//            Log.d("LoanSlip", "First Name: " + loanSlip.getFirstName());
//            Log.d("LoanSlip", "Last Name: " + loanSlip.getLastName());
//
//            // In ra dữ liệu của selectedDetails
//            for (DetailLoanSlip detail : selectedDetails) {
//                Log.d("DetailLoanSlip", "LoanSlip ID: " + detail.getLoanSlipId());
//                Log.d("DetailLoanSlip", "Book Name: " + detail.getBookName());
//                Log.d("DetailLoanSlip", "Quantity: " + detail.getQuantity());
//                Log.d("DetailLoanSlip", "Borrow Date: " + detail.getBorrowDate());
//                Log.d("DetailLoanSlip", "Return Date: " + detail.getReturnDate());
//                Log.d("DetailLoanSlip", "Status: " + detail.getStatus());
//            }

            // Hiển thị Toast ngắn gọn
            Toast.makeText(getContext(), "LoanSlip and selected details logged", Toast.LENGTH_SHORT).show();

            ReturnSlip returnSlip = new ReturnSlip();
            returnSlip.setId(0);
            returnSlip.setEmail(loanSlip.getEmail());
            returnSlip.setPhoneNumber(loanSlip.getPhoneNumber());
            returnSlip.setAddress(loanSlip.getAddress());
            returnSlip.setFirstName(loanSlip.getFirstName());
            returnSlip.setLastName(loanSlip.getLastName());
            returnSlip.setLoanId(loanSlip.getId());

            restfulAPIService.AddReturnSlip(returnSlip).enqueue(new Callback<ReturnSlip>() {
                @Override
                public void onResponse(Call<ReturnSlip> call, Response<ReturnSlip> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ReturnSlip returnSlip1 = response.body();
                        for (DetailLoanSlip detailLoanSlip : selectedDetails) {
                            DetailReturnSlip detailReturnSlip = new DetailReturnSlip();
                            detailReturnSlip.setBookName(detailLoanSlip.getBookName());
                            detailReturnSlip.setQuantity(detailLoanSlip.getQuantity());
                            detailReturnSlip.setBorrowDate(detailLoanSlip.getBorrowDate());
                            detailReturnSlip.setReturnDate(detailLoanSlip.getReturnDate());
                            detailReturnSlip.setReturnSlipId(returnSlip1.getId());
                            restfulAPIService.AddDetailReturnSlip(detailReturnSlip).enqueue(new Callback<List<DetailReturnSlip>>() {
                                @Override
                                public void onResponse(Call<List<DetailReturnSlip>> call, Response<List<DetailReturnSlip>> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        restfulAPIService.UpdateDetailLoanSlipStatus(loanSlip.getId(), detailLoanSlip.getBookName()).enqueue(new Callback<DetailLoanSlip>() {
                                            @Override
                                            public void onResponse(Call<DetailLoanSlip> call, Response<DetailLoanSlip> response) {
                                                if (response.isSuccessful() && response.body() != null) {
                                                } else {
                                                    Toast.makeText(getContext(), "Failed to update detail loan status", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DetailLoanSlip> call, Throwable t) {
                                                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Toast.makeText(getContext(), "Add detail return successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Failed to add detail return", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<DetailReturnSlip>> call, Throwable t) {
                                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to add loan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReturnSlip> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}