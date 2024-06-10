package com.example.project.ui.add;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.DataManager;
import com.example.project.NewDataManager;
import com.example.project.R;
import com.example.project.databinding.FragmentAddBinding;
import com.example.project.entities.Book;
import com.example.project.entities.Category;
import com.example.project.entities.CategoryResponse;
import com.example.project.entities.DataResponse;
import com.example.project.entities.NewBook;
import com.example.project.entities.Publisher;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.network.SocketEventListener;
import com.example.project.network.WebSocketClient;
import com.example.project.ui.IntroActivity;
import com.example.project.ui.MainActivity;
import com.example.project.utils.Constants;
import com.example.project.utils.LoadingDialog;
import com.example.project.utils.PopupUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
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

public class AddFragment extends Fragment{

    private FragmentAddBinding binding;
    TextView textView;
    List<String> data_lsp = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String selectedCategory;
    byte[] byteArray;
    String base64Image;
    Bitmap bitmap;
    Button btnAdd;
    ImageButton ibSelectImage, ibPublishDate;
    ImageView imageView;
    EditText edtBookName, edtAuthorName, edtInventoryQuantity, edtAvailableQuantity, edtSummary, editPublishDate, edtBookId;
    Spinner spnBookCategory, spnPublisher, spnStatus;
    boolean imgSelected = false;

    private RestfulAPIService restfulAPIService;

    ActivityResultLauncher<Intent> resultLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        SetControl();
        registerResult();
        fetchAndSetData();
        SetEvent();

        addViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void SetEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    // Xử lý sự kiện khi tất cả các trường đều hợp lệ
                    NewBook newBook = new NewBook();
                    newBook.setId(edtBookId.getText().toString().trim());
                    newBook.setTitle(edtBookName.getText().toString().trim());
                    newBook.setAuthorName(edtAuthorName.getText().toString().trim());
                    newBook.setSummary(edtSummary.getText().toString().trim());
                    newBook.setInventoryQuantity(Integer.parseInt(edtInventoryQuantity.getText().toString().trim()));
                    newBook.setAvailableQuantity(Integer.parseInt(edtAvailableQuantity.getText().toString().trim()));
                    newBook.setCategoryName(spnBookCategory.getSelectedItem().toString());
                    newBook.setPublisherName(spnPublisher.getSelectedItem().toString());
                    newBook.setStatus(spnStatus.getSelectedItem().toString());
                    newBook.setPublishDate(editPublishDate.getText().toString().trim());
                    newBook.setImage(base64Image); // Assuming you have converted the image to base64

                    Call<List<NewBook>> call = restfulAPIService.addBook(newBook);
                    call.enqueue(new Callback<List<NewBook>>() {
                        @Override
                        public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                            if (response.isSuccessful()) {
                                RestfulAPIService restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
                                Call<List<NewBook>> call1 = restfulAPIService.getAllBooks();

                                LoadingDialog.getInstance(getContext()).show();
                                call1.enqueue(new Callback<List<NewBook>>() {
                                    @Override
                                    public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                                        if (response.isSuccessful()) {
                                            List<NewBook> books = response.body();
                                            if(books != null)
                                                NewDataManager.getInstance().books = books;
                                            LoadingDialog.getInstance(getContext()).hide();
                                            startActivity(new Intent(getContext(), MainActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<NewBook>> call, Throwable t) {
                                        Log.e("MainActivity", "Error: " + t.getMessage());
                                        LoadingDialog.getInstance(getContext()).hide();
                                    }
                                });
                                Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to add book.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<NewBook>> call, Throwable t) {
                            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin và chọn các mục cần thiết.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ibSelectImage.setOnClickListener(view -> pickImage());

        ibPublishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                editPublishDate.setText(selectedDate);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();

                            bitmap = getBitmap(getActivity().getContentResolver(), imageUri);
                            base64Image = convertBitmapToBase64(bitmap);
                            imageView.setImageBitmap(bitmap);
                            imgSelected = true;
                            Log.d("Base64Image", base64Image);
                        } catch (Exception e){
                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private  void  SetControl() {
        textView = binding.textAdd;

        ibSelectImage = binding.ibBookImage;
        imageView = binding.ivBookImage;

        edtBookId = binding.edtBookId;
        edtBookName = binding.edtBookName;
        edtAuthorName = binding.edtAuthorName;
        edtSummary = binding.edtSummary;
        edtInventoryQuantity = binding.edtInventoryQuantity;
        edtAvailableQuantity = binding.edtAvailableQuantity;

        spnBookCategory = binding.spnCategoryName;
        spnPublisher = binding.spnPublisherName;
        spnStatus = binding.spnStatus;

        btnAdd = binding.btnSubmit;

        ibPublishDate = binding.ibPublishDate;
        editPublishDate = binding.editPublishDate;

        // Đặt các lựa chọn cho Spinner spnStatus
        String[] statusOptions = {"Borrowable", "Not Borrowable"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(statusAdapter);
    }

    private boolean isInputValid() {
        // Kiểm tra các trường EditText
        if (edtBookId.getText().toString().trim().isEmpty() ||
                edtBookName.getText().toString().trim().isEmpty() ||
                edtAuthorName.getText().toString().trim().isEmpty() ||
                edtSummary.getText().toString().trim().isEmpty() ||
                edtInventoryQuantity.getText().toString().trim().isEmpty() ||
                edtAvailableQuantity.getText().toString().trim().isEmpty() ||
                editPublishDate.getText().toString().trim().isEmpty()) {
            return false;
        }

        if (!imgSelected)
            return false;

        return true;
    }

    private void fetchAndSetData() {
        // Gọi API để lấy danh sách Category
        restfulAPIService.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body();
                    List<String> categoryNames = new ArrayList<>();
                    for (Category category : categories) {
                        categoryNames.add(category.getCategoryName());
                    }
                    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryNames);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnBookCategory.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });

        // Gọi API để lấy danh sách Publisher
        restfulAPIService.getAllPublishers().enqueue(new Callback<List<Publisher>>() {
            @Override
            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Publisher> publishers = response.body();
                    List<String> publisherNames = new ArrayList<>();
                    for (Publisher publisher : publishers) {
                        publisherNames.add(publisher.getPublisherName());
                    }
                    ArrayAdapter<String> publisherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, publisherNames);
                    publisherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnPublisher.setAdapter(publisherAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Publisher>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load publishers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức chuyển đổi Bitmap thành chuỗi base64
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}