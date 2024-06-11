package com.example.project.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.NewDataManager;
import com.example.project.R;
import com.example.project.entities.Book;
import com.example.project.entities.NewBook;
import com.example.project.network.RestfulAPIService;
import com.example.project.network.RetrofitClient;
import com.example.project.network.WebSocketClient;
import com.example.project.utils.Constants;
import com.example.project.utils.LoadingDialog;
import com.example.project.utils.UIService;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIService.HideStatusBar(this, this);
        setContentView(R.layout.activity_intro);

        Button startedButton = findViewById(R.id.btnStarted);
        // Set click listener for the "Started" button
        startedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.activity_login);
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });

        RestfulAPIService restfulAPIService = RetrofitClient.getClient(Constants.SERVER_URL).create(RestfulAPIService.class);
        Call<List<NewBook>> call = restfulAPIService.getAllBooks();

        LoadingDialog.getInstance(this).show();
        call.enqueue(new Callback<List<NewBook>>() {
            @Override
            public void onResponse(Call<List<NewBook>> call, Response<List<NewBook>> response) {
                if (response.isSuccessful()) {
                    List<NewBook> books = response.body();
                    if(books != null)
                        NewDataManager.getInstance().books = books;
                    LoadingDialog.getInstance(getApplicationContext()).hide();
                    //startActivity(new Intent(IntroActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<List<NewBook>> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
                LoadingDialog.getInstance(getApplicationContext()).hide();
            }
        });
    }

}
