package com.example.project.ui.update;

import static android.app.PendingIntent.getActivity;
import static android.provider.MediaStore.Images.Media.getBitmap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.NewDataManager;
import com.example.project.R;
import com.example.project.entities.Category;
import com.example.project.entities.NewBook;
import com.example.project.entities.Publisher;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.ui.MainActivity;
import com.example.project.utils.Constants;
import com.example.project.utils.LoadingDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBook extends AppCompatActivity {

    private RestfulAPIService restfulAPIService;

    private TextView textAdd;
    private ImageButton ibBookImage, ibPublishDate;
    private ImageView ivBookImage;
    private EditText edtBookId;
    private EditText edtBookName;
    private EditText edtAuthorName;
    private EditText edtSummary;
    private EditText editPublishDate;
    private Spinner spnPublisherName;
    private Spinner spnCategoryName;
    private Spinner spnStatus;
    private EditText edtInventoryQuantity;
    private EditText edtAvailableQuantity;
    private Button btnSubmit, btnCancel;
    ArrayAdapter<String> adapter, publisherAdapter, categoryAdapter;
    ActivityResultLauncher<Intent> resultLauncher;
    String base64Image;
    Bitmap bitmap;
    boolean imgSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        Setup();
        registerResult();
        Event();
    }

    private void getBookById(String bookId) {
        Call<NewBook> call = restfulAPIService.findBookById(bookId);
        call.enqueue(new Callback<NewBook>() {
            @Override
            public void onResponse(Call<NewBook> call, Response<NewBook> response) {
                if (response.isSuccessful()) {
                    NewBook book = response.body();

                    edtBookId.setText(book.getId());
                    edtBookName.setText(book.getTitle());
                    edtAuthorName.setText(book.getAuthorName());
                    edtSummary.setText(book.getSummary());
                    editPublishDate.setText(book.getPublishDate());

//                    Log.d("PublisherName", book.getPublisherName());
                    // Lấy dữ liệu từ API getAllCategories
                    Call<List<Category>> callCategories = restfulAPIService.getAllCategories();
                    callCategories.enqueue(new Callback<List<Category>>() {
                        @Override
                        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                            if (response.isSuccessful()) {
                                List<Category> categories = response.body();
                                List<String> categoryNames = new ArrayList<>();
                                for (Category category : categories) {
                                    categoryNames.add(category.getCategoryName());
                                }

                                // In dữ liệu ra log
                                for (String categoryName : categoryNames) {
                                    Log.d("CategoryAdapter", "Category: " + categoryName);
                                }

                                categoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoryNames);
                                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnCategoryName.setAdapter(categoryAdapter);

                                // Đặt spinner mặc định
                                if (book.getCategoryName() != null) {
//                                    Log.d("CategoryName", book.getCategoryName());
                                    int spinnerPosition = categoryAdapter.getPosition(book.getCategoryName());
                                    spnCategoryName.setSelection(spinnerPosition);
                                }
//                                Log.d("categoryAdapter", categoryAdapter.getItem(0).toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi khi tải danh mục: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Category>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Lấy dữ liệu từ API getAllPublishers
                    Call<List<Publisher>> callPublishers = restfulAPIService.getAllPublishers();
                    callPublishers.enqueue(new Callback<List<Publisher>>() {
                        @Override
                        public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {
                            if (response.isSuccessful()) {
                                List<Publisher> publishers = response.body();
                                List<String> publisherNames = new ArrayList<>();
                                for (Publisher publisher : publishers) {
                                    publisherNames.add(publisher.getPublisherName());
                                }

                                // In dữ liệu ra log
                                for (String publisherName : publisherNames) {
                                    Log.d("CategoryAdapter", "Category: " + publisherName);
                                }

                                publisherAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, publisherNames);
                                publisherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnPublisherName.setAdapter(publisherAdapter);

                                // Đặt spinner mặc định
                                if (book.getPublisherName() != null) {
//                                    Log.d("PublisherName", book.getPublisherName());
                                    int spinnerPosition = publisherAdapter.getPosition(book.getPublisherName());
                                    spnPublisherName.setSelection(spinnerPosition);
                                }
//                                Log.d("publisherAdapter", publisherAdapter.getItem(0).toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "Lỗi khi tải nhà xuất bản: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Publisher>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (book.getStatus() != null) {
                        int spinnerPosition = adapter.getPosition(book.getStatus());
                        spnStatus.setSelection(spinnerPosition);
                    }

                    edtInventoryQuantity.setText(String.valueOf(book.getInventoryQuantity()));
                    edtAvailableQuantity.setText(String.valueOf(book.getAvailableQuantity()));

                    String base64Image = book.getImage();
                    if (base64Image != null && !base64Image.isEmpty()) {
                        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ivBookImage.setImageBitmap(decodedByte);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Lỗi: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<NewBook> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void Setup(){
        textAdd = findViewById(R.id.text_add);
        ibBookImage = findViewById(R.id.ibBookImage);
        ibPublishDate = findViewById(R.id.ibPublishDate);
        ivBookImage = findViewById(R.id.ivBookImage);
        edtBookId = findViewById(R.id.edtBookId);
        edtBookName = findViewById(R.id.edtBookName);
        edtAuthorName = findViewById(R.id.edtAuthorName);
        edtSummary = findViewById(R.id.edtSummary);
        editPublishDate = findViewById(R.id.editPublishDate);
        spnPublisherName = findViewById(R.id.spnPublisherName);
        spnCategoryName = findViewById(R.id.spnCategoryName);
        spnStatus = findViewById(R.id.spnStatus);
        edtInventoryQuantity = findViewById(R.id.edtInventoryQuantity);
        edtAvailableQuantity = findViewById(R.id.edtAvailableQuantity);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        Spinner spnStatus = findViewById(R.id.spnStatus);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Borrowable", "Not Borrowable"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adapter);

        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");

        getBookById(bookId);
    }

    private void Event(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<NewBook>> call1 = restfulAPIService.getAllBooks();

                LoadingDialog.getInstance(getApplicationContext()).show();
                call1.enqueue(new Callback<List<NewBook>>() {
                    @Override
                    public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                        if (response.isSuccessful()) {
                            List<NewBook> books = response.body();
                            if(books != null)
                                NewDataManager.getInstance().books = books;
                            LoadingDialog.getInstance(getApplicationContext()).hide();
                            startActivity(new Intent(UpdateBook.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewBook>> call, Throwable t) {
                        Log.e("MainActivity", "Error: " + t.getMessage());
                        LoadingDialog.getInstance(UpdateBook.this).hide();
                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
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
                    newBook.setCategoryName(spnCategoryName.getSelectedItem().toString());
                    newBook.setPublisherName(spnPublisherName.getSelectedItem().toString());
                    newBook.setStatus(spnStatus.getSelectedItem().toString());
                    newBook.setPublishDate(editPublishDate.getText().toString().trim());

                    BitmapDrawable drawable = (BitmapDrawable) ivBookImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    // Chuyển đổi Bitmap thành chuỗi base64
                    String base64Image = convertBitmapToBase64(bitmap);

                    newBook.setImage(base64Image); // Assuming you have converted the image to base64

                    Call<List<NewBook>> call = restfulAPIService.addBook(newBook);
                    call.enqueue(new Callback<List<NewBook>>() {
                        @Override
                        public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                            if (response.isSuccessful()) {
                                RestfulAPIService restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
                                Call<List<NewBook>> call1 = restfulAPIService.getAllBooks();

                                LoadingDialog.getInstance(getApplicationContext()).show();
                                call1.enqueue(new Callback<List<NewBook>>() {
                                    @Override
                                    public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                                        if (response.isSuccessful()) {
                                            List<NewBook> books = response.body();
                                            if(books != null)
                                                NewDataManager.getInstance().books = books;
                                            LoadingDialog.getInstance(getApplicationContext()).hide();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<NewBook>> call, Throwable t) {
                                        Log.e("MainActivity", "Error: " + t.getMessage());
                                        LoadingDialog.getInstance(getApplicationContext()).hide();
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Book updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to add book.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<NewBook>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin và chọn các mục cần thiết.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibPublishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateBook.this,
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

        ibBookImage.setOnClickListener(view -> pickImage());
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

                            bitmap = getBitmap(getApplicationContext().getContentResolver(), imageUri);
                            base64Image = convertBitmapToBase64(bitmap);
                            ivBookImage.setImageBitmap(bitmap);
                            imgSelected = true;
                            Log.d("Base64Image", base64Image);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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

        if (ivBookImage.getDrawable() == null)
            return false;

        return true;
    }

    // Phương thức chuyển đổi Bitmap thành chuỗi base64
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
