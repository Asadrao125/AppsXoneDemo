package com.gexton.appsxonedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gexton.appsxonedemo.adapter.ItemsAdapter;
import com.gexton.appsxonedemo.api.ApiCallback;
import com.gexton.appsxonedemo.api.ApiManager;
import com.gexton.appsxonedemo.models.CategoryModel;
import com.gexton.appsxonedemo.models.ItemsModel;
import com.gexton.appsxonedemo.utils.Const;
import com.gexton.appsxonedemo.utils.SharedPref;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity implements ApiCallback {
    String loginResponse;
    ApiCallback apiCallback;
    RecyclerView rvItems;
    SearchView sv;
    Spinner spinner;
    ItemsAdapter adapter;
    ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<>();
    ArrayList<ItemsModel> itemsModelArrayList = new ArrayList<>();
    CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        setTitle("Items");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        }

        SharedPref.init(this);
        loginResponse = SharedPref.read("loginResponse", "");
        apiCallback = ItemsActivity.this;
        rvItems = findViewById(R.id.rvItems);
        sv = findViewById(R.id.sv);
        spinner = findViewById(R.id.spinner);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loginResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("CategoryList");
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("json_array", "onCreate: " + jsonArray.get(i));
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String catID = jsonObject1.getString("CategoryId");
                String catName = jsonObject1.getString("CategoryName");
                categoryModelArrayList.add(new CategoryModel(catID, catName));
                categoryModel = new CategoryModel(catID, catName);
                list.add(catName);
            }

            ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
            spinner.setAdapter(categoriesAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        itemsList("", "");
    }

    @Override
    public void onApiResponce(int httpStatusCode, int successOrFail, String apiName, String apiResponce) {
        Log.d("items_response", "onApiResponce: " + apiResponce);

        try {
            JSONObject jsonObject = new JSONObject(apiResponce);

            JSONArray jsonArray = jsonObject.getJSONArray("ItemsList");
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("json_array", "onCreate: " + jsonArray.get(i));
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String category = jsonObject1.getString("Category");
                String description = jsonObject1.getString("Description");
                String icp = jsonObject1.getString("ICP");
                String imagePath = jsonObject1.getString("ImagePath");
                String itemId = jsonObject1.getString("ItemID");
                String itemName = jsonObject1.getString("ItemName");
                String mcp = jsonObject1.getString("MCP");
                String qoh = jsonObject1.getString("QOH");
                String qoo = jsonObject1.getString("QOO");
                String sellPrice = jsonObject1.getString("SellPrice");
                String subCategory = jsonObject1.getString("SubCategory");
                String upcode = jsonObject1.getString("UPCCode");
                itemsModelArrayList.add(new ItemsModel(category, description, icp, itemId, imagePath, itemName, mcp, qoh, qoo));
            }
            adapter = new ItemsAdapter(ItemsActivity.this, itemsModelArrayList);
            rvItems.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void itemsList(String SearchValue, String CategoryId) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("SearchValue", SearchValue);
        requestParams.put("CategoryId", CategoryId);
        requestParams.put("HDImage", "false");
        requestParams.put("CustomerId", "0");
        ApiManager apiManager = new ApiManager(ItemsActivity.this, "get", Const.ALL_ITEMS_SERVICE, requestParams, apiCallback);
        apiManager.loadURL();
    }

}