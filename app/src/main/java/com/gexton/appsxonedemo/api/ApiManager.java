package com.gexton.appsxonedemo.api;

import android.app.Activity;
import android.util.Log;

import com.gexton.appsxonedemo.utils.Const;
import com.gexton.appsxonedemo.utils.Dialog_CustomProgress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiManager {
    final int DEFAULT_TIMEOUT = 1000000000;
    Activity activity;
    String getOrPost;
    String apiName;
    RequestParams params;
    ApiCallback apiCallback;
    Dialog_CustomProgress customProgressDialog;
    public static boolean shouldShowPD = true;

    public ApiManager(Activity activity, String getOrPost, String apiName, RequestParams params, ApiCallback apiCallback) {
        this.activity = activity;
        this.getOrPost = getOrPost;
        this.apiName = apiName;
        this.params = params;
        this.apiCallback = apiCallback;

        customProgressDialog = new Dialog_CustomProgress(activity);

        System.out.println("-- Req URL : " + Const.BASE_URL + apiName);
        System.out.println("-- Params : " + params.toString());
    }

    public void loadURL() {

        customProgressDialog.showProgressDialog();

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);

        client.get(Const.BASE_URL + apiName, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {

                            customProgressDialog.dismissProgressDialog();

                            String content = new String(responseBody);
                            apiCallback.onApiResponce(statusCode, 1, apiName, content);
                            Log.d("onSuccess", "Success: " + content);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        try {
                            customProgressDialog.dismissProgressDialog();
                            String content = new String(responseBody);
                            Log.d("onFailure", "Failure: " + content);
                            apiCallback.onApiResponce(statusCode, 0, apiName, content);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

}
