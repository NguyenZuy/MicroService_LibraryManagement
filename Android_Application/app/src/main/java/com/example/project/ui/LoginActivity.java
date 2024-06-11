package com.example.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project.DataManager;
import com.example.project.R;
import com.example.project.entities.DataResponse;
import com.example.project.entities.Supplier;
import com.example.project.entities.User;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.network.SocketEventListener;
import com.example.project.network.WebSocketClient;
import com.example.project.network.WebSocketResponseListener;
import com.example.project.ui.home.HomeFragment;
import com.example.project.ui.subFragments.SignUpActivity;
import com.example.project.utils.Constants;
import com.example.project.utils.ConvertService;
import com.example.project.utils.LoadingDialog;
import com.example.project.utils.PopupUtils;
import com.example.project.utils.UIService;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements SocketEventListener {
    TextView signUpTxt;
    private Boolean resultCheck = new Boolean(true);
    boolean isPasswordVisible = false;

    RestfulAPIService restfulAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIService.HideStatusBar(this, this);
        setContentView(R.layout.activity_login);
        restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);

        // Now you can find the views and set onClickListener
        findViewById(R.id.btnSubmit).setOnClickListener(view -> {
            LoadingDialog.getInstance(this).show();

            JSONObject loginObject = new JSONObject();
            // Corrected casting to EditText
            EditText editUsername = (EditText)findViewById(R.id.editUsername);
            EditText editPassword = (EditText)findViewById(R.id.editPassword);

            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String email = "1@gmail.com";
            User user = new User(username, password, email);

            restfulAPIService.login(user).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.isSuccessful()) {
                        Intent intent;
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    // Xử lý khi có lỗi xảy ra trong quá trình gọi API
                    Toast.makeText(LoginActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        });
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resultCheck) {
                    Toast.makeText(LoginActivity.this, "Username không tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent;
                intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        EditText editTextUsername = (EditText)findViewById(R.id.editUsername);
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String username = editTextUsername.getText().toString().trim();
                    checkUserName(username);
                }
            }
        });
        ImageView iconEye = findViewById(R.id.icon_eye);
        iconEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái hiện tại của mật khẩu (ẩn thành hiện và ngược lại)
                isPasswordVisible = !isPasswordVisible;
                EditText editPassword = (EditText)findViewById(R.id.editPassword);

                // Thay đổi loại dữ liệu đầu vào của EditText
                if (isPasswordVisible) {
                    // Nếu mật khẩu đang ẩn, hiển thị văn bản
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    iconEye.setImageResource(R.drawable.icon_eye);
                } else {
                    // Nếu mật khẩu đang hiển thị, ẩn văn bản
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconEye.setImageResource(R.drawable.icon_eye1);
                }

                // Di chuyển con trỏ văn bản về cuối chuỗi
                editPassword.setSelection(editPassword.getText().length());
            }
        });
    }

    @Override
    public void onLoginResponse(boolean result) throws JSONException {
        if(result){
            GetAllData();
        }
        else{
            LoadingDialog.getInstance(this).hide();
            PopupUtils.showPopup(this, "Login Result Notification", "Login attempt failed. Please check your credentials and try again.", Constants.TYPE_ALERT.OK, null, null);
        }
    }

    @Override
    public void onGetDataResponse(String data) {
        Gson gson = new Gson();
        DataResponse dataResponse = gson.fromJson(data, DataResponse.class);
        DataManager.getInstance().UpdateData(dataResponse);
        LoadingDialog.getInstance(this).hide();
        Log.d("WebSocket", "Received all data success");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onOrderResponse(boolean result) {

    }
    private void checkUserName(String userName) {
        Gson gson = new Gson();
        String userJson = gson.toJson(userName);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("event", Constants.EVENT_CHECK_USERNAME);
            jsonObject.put("username", userName);
            String message = jsonObject.toString();

            // Gửi tin nhắn và gắn listener
            WebSocketClient.getInstance().send(message, new WebSocketResponseListener() {
                @Override
                public void checkUserNameResponse(String data) {
                    // Xử lý phản hồi từ máy chủ
                    if(data.equals("null")){
                        resultCheck = false;
                    }
                    else {
                        resultCheck = true;
                        DataManager.getInstance().getUser(data);
                        ForgotPasswordActivity.currentUser = DataManager.getInstance().getUser();
                    }
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onAddBookResponse(boolean result) {

    }

    @Override
    public void onAddCategoryResponse(boolean result) throws JSONException {

    }

    @Override
    public void onGetCategoryResponse(String data) throws JSONException {

    }

    @Override
    public void onHandlePhieu(boolean result) throws JSONException {

    }

    @Override
    public void onHandleUpdate(boolean result) throws JSONException {

    }


    void GetAllData() throws JSONException {
        JSONObject loginObject = new JSONObject();
        loginObject.put("event", Constants.EVENT_GET_DATA);
        loginObject.put("username", DataManager.getInstance().username);
        String mess = loginObject.toString();
        WebSocketClient.getInstance().requestToServer(mess, this);
    }
}
