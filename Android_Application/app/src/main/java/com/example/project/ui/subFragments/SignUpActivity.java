package com.example.project.ui.subFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Printer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.DataManager;
import com.example.project.R;
import com.example.project.entities.User;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.network.SocketEventListener;
import com.example.project.network.WebSocketClient;
import com.example.project.network.WebSocketResponseListener;
import com.example.project.ui.LoginActivity;
import com.example.project.ui.MainActivity;
import com.example.project.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMBERS = "0123456789";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static final SecureRandom random = new SecureRandom();

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextOtp;

    private EditText editTextConfirmPassword;

    boolean isPasswordVisible = false;
    boolean isPasswordVisible1 = false;

    RestfulAPIService restfulAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        editTextUsername = findViewById(R.id.editUsername);
        editTextEmail = findViewById(R.id.editMail);
        editTextPassword = findViewById(R.id.editPassword);
        editTextConfirmPassword = findViewById((R.id.editConfirmPassword));


        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();


                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!confirmPassword.equals((password))){
                    Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không trùng khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Create a new User object with the collected user data
                User user = new User(username, password, email);

                restfulAPIService.signUp(user).enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent;
                            intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Đăng kí không thành công!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        // Xử lý khi có lỗi xảy ra trong quá trình gọi API
                        Toast.makeText(SignUpActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView iconEye = findViewById(R.id.icon_eye);
        ImageView iconEye1 = findViewById(R.id.icon_eye1);
        iconEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái hiện tại của mật khẩu (ẩn thành hiện và ngược lại)
                isPasswordVisible = !isPasswordVisible;
                EditText editPassword = (EditText)findViewById(R.id.editPassword);

                // Thay đổi loại dữ liệu đầu vào của EditText
                if (isPasswordVisible) {
                    // Nếu mật khẩu đang ẩn, hiển thị văn bản
                    iconEye.setImageResource(R.drawable.icon_eye);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Nếu mật khẩu đang hiển thị, ẩn văn bản
                    iconEye.setImageResource(R.drawable.icon_eye1);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Di chuyển con trỏ văn bản về cuối chuỗi
                editPassword.setSelection(editPassword.getText().length());
            }
        });

        iconEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái hiện tại của mật khẩu (ẩn thành hiện và ngược lại)
                isPasswordVisible1 = !isPasswordVisible1;
                EditText editPassword = (EditText)findViewById(R.id.editConfirmPassword);

                // Thay đổi loại dữ liệu đầu vào của EditText
                if (isPasswordVisible1) {
                    // Nếu mật khẩu đang ẩn, hiển thị văn bản
                    iconEye1.setImageResource(R.drawable.icon_eye);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Nếu mật khẩu đang hiển thị, ẩn văn bản
                    iconEye1.setImageResource(R.drawable.icon_eye1);
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Di chuyển con trỏ văn bản về cuối chuỗi
                editPassword.setSelection(editPassword.getText().length());
            }
        });
    }


}