package com.gexton.appsxonedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gexton.appsxonedemo.api.ApiCallback;
import com.gexton.appsxonedemo.api.ApiManager;
import com.gexton.appsxonedemo.utils.Const;
import com.gexton.appsxonedemo.utils.SharedPref;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends AppCompatActivity implements ApiCallback {
    ApiCallback apiCallback;
    EditText edtEmail, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        apiCallback = LoginActivity.this;
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        SharedPref.init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Empty");
                    edtEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Empty");
                    edtPassword.requestFocus();
                } else {
                    loginUser(email, password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", email);
        requestParams.put("password", password);
        ApiManager apiManager = new ApiManager(LoginActivity.this, "get", Const.LOGIN_SERVICE, requestParams, apiCallback);
        apiManager.loadURL();
        SharedPref.write("login", "true");
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void onApiResponce(int httpStatusCode, int successOrFail, String apiName, String apiResponce) {
        SharedPref.write("loginResponse", apiResponce);
    }

    private void checkUserExistance() {
        SharedPref.init(this);
        if (SharedPref.read("login", "").equals("true")) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserExistance();
    }
}