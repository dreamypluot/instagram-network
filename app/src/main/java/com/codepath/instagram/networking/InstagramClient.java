package com.codepath.instagram.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class InstagramClient {
    private static final String TAG = "InstagramClient";
    private static final String API_BASE_URL = "https://api.instagram.com/v1/media/popular?client_id=";
    private static String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    public static void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = API_BASE_URL + CLIENT_ID;
        new AsyncHttpClient().get(url, responseHandler);
    }
}

