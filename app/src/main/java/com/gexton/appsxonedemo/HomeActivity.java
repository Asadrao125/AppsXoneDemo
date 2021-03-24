package com.gexton.appsxonedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gexton.appsxonedemo.utils.SharedPref;

public class HomeActivity extends AppCompatActivity {
    Button btnItems, btnLogout;

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