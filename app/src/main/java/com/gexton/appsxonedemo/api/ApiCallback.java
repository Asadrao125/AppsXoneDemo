package com.gexton.appsxonedemo.api;

public interface ApiCallback {

    public void onApiResponce(int httpStatusCode, int successOrFail, String apiName, String apiResponce);

}
