package com.gexton.appsxonedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gexton.appsxonedemo.api.ApiCallback;
import com.gexton.appsxonedemo.api.ApiManager;
import com.gexton.appsxonedemo.utils.Const;
import com.gexton.appsxonedemo.utils.SharedPref;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetailActivity extends AppCompatActivity implements ApiCallback {
    ApiCallback apiCallback;
    String ItemId;
    TextView tvCategory, tvDescription, tvSubCategory, tvItemName, tvMcp, tvQoh, tvQoo, tvPlus, tvMinus, tvQuantity, tvSellPrice;
    ImageView img;
    Button btnAddToCart;
    int qty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        /* Setting Toobar Text */
        setTitle("Item Detail");

        /* Change Color Of Status Bar */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        }

        /* Adding Back Button On Toolbar */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        clickListener();
    }

    private void clickListener() {
        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty > 1) {
                    qty--;
                    tvQuantity.setText("" + qty);
                }
            }
        });

        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                tvQuantity.setText("" + qty);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = 1;
                tvQuantity.setText("" + qty);
            }
        });
    }

    private void init() {
        apiCallback = ItemDetailActivity.this;
        ItemId = getIntent().getStringExtra("ItemId");
        tvCategory = findViewById(R.id.tvCategory);
        tvDescription = findViewById(R.id.tvDescription);
        tvSubCategory = findViewById(R.id.tvSubCategory);
        tvItemName = findViewById(R.id.tvItemName);
        tvMcp = findViewById(R.id.tvMcp);
        tvQoh = findViewById(R.id.tvQoh);
        tvQoo = findViewById(R.id.tvQoo);
        img = findViewById(R.id.img);
        tvPlus = findViewById(R.id.tvPlus);
        tvMinus = findViewById(R.id.tvMinus);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvSellPrice = findViewById(R.id.tvSellPrice);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSingleCategory(ItemId);
    }

    private void loadSingleCategory(String ItemId) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("ItemId", ItemId);
        ApiManager apiManager = new ApiManager(ItemDetailActivity.this, "get", Const.SINGLE_ITEM_DETAIL, requestParams, apiCallback);
        apiManager.loadURL();
    }

    @Override
    public void onApiResponce(int httpStatusCode, int successOrFail, String apiName, String apiResponce) {
        try {
            JSONObject jsonObject = new JSONObject(apiResponce);
            String cat = jsonObject.getString("Category");
            String description = jsonObject.getString("Description");
            String eta = jsonObject.getString("ETA");
            String icp = jsonObject.getString("ICP");
            String imagePath = jsonObject.getString("ImagePath");
            String itemName = jsonObject.getString("ItemName");
            String mcp = jsonObject.getString("MCP");
            String message = jsonObject.getString("Message");
            String price = jsonObject.getString("Price");
            String qoh = jsonObject.getString("QOH");
            String qoo = jsonObject.getString("QOO");
            String status = jsonObject.getString("Status");
            String subCategory = jsonObject.getString("SubCategory");
            String upc = jsonObject.getString("UPC");

            tvItemName.setText(itemName);
            tvCategory.setText(cat);
            tvDescription.setText(description);
            tvMcp.setText(mcp + "\t" + icp);
            tvSellPrice.setText(price);
            tvQoh.setText(qoh);
            tvQoo.setText(qoo);
            tvSubCategory.setText(subCategory);
            Picasso.get().load(imagePath).placeholder(R.mipmap.ic_launcher).into(img);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}