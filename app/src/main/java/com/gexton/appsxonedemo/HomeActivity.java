package com.gexton.appsxonedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gexton.appsxonedemo.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    Button btnItems, btnLogout;
    TextView tvCallUsNow;
    String loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Dashbord");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        }

        btnItems = findViewById(R.id.btnItems);
        btnLogout = findViewById(R.id.btnLogout);
        tvCallUsNow = findViewById(R.id.tvCallUsNow); /* CompanyContactNo */

        SharedPref.init(this);
        loginResponse = SharedPref.read("loginResponse", "");
        try {
            JSONObject jsonObject = new JSONObject(loginResponse);
            tvCallUsNow.setText("Call Us Now " + jsonObject.getString("CompanyContactNo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.remove("login");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}