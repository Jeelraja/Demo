package com.builderfly.parsing_app.network;

import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HTTPUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType urlencoded = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");

    public static OkHttpClient client = new OkHttpClient();

    private static String headerKey = "";
    private static String headerValue = "";

    public static String getRun(String url, Map<String, String> headers) throws IOException {
        Log.i(HTTPUtils.class.getSimpleName(), " URL:: " + url);
        Response response = null;
        if ((headers != null)) {
            Iterator myVeryOwnIterator = headers.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                headerKey = (String) myVeryOwnIterator.next();
                headerValue = (String) headers.get(headerKey);

                //Log.e("TAG  are ", "getRun: "+headerKey+ " "+headerValue );
            }
            Request request = new Request.Builder().addHeader("Content-Type", "application/json").addHeader(headerKey, headerValue)
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
        } else {
            Request request = new Request.Builder().addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
        }

        return response.body().string();
    }

    public static String postRunWithHeader(String url, String json, Map<String, String> headers) throws IOException {
        Log.e(HTTPUtils.class.getSimpleName(), "URL:: " + url);
        Log.e(HTTPUtils.class.getSimpleName(), "Request Parameter:: " + json);

        //json = json.replace("\\", "");

        Log.e(HTTPUtils.class.getSimpleName(), "Request Parameter:: AFTER " + json);
        Request request = createRequestBuilder(url, json, headers, JSON).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postRunWithImage(String url, RequestBody formBody, Map<String, String> headers) throws IOException {
        //Log.e(HTTPUtils.class.getSimpleName(), " URL URL IS:: " + url);
        Response response = null;
        Log.e(HTTPUtils.class.getSimpleName(), "Request Parameter:: " + formBody);

        if ((headers != null)) {
            Iterator myVeryOwnIterator = headers.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                headerKey = (String) myVeryOwnIterator.next();
                headerValue = (String) headers.get(headerKey);
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody).addHeader(headerKey, headerValue)
                    .build();
            try {
                response = client.newCall(request).execute();
                return "" + response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                response = client.newCall(request).execute();
                return "" + response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String postRun(String url, String json) throws IOException {
        //Log.e(HTTPUtils.class.getSimpleName(), " URL:: " + url);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

//        Request request1 = createRequestBuilder(url, formBody, headers).build();
//        AllEventsModel response1 = client.newCall(request).execute();

        return response.body().string();
    }

    // Default method to create request builder
    private static Request.Builder createRequestBuilder(String url, RequestBody formBody, Map<String, String> headers) {
        // Create object of request builder
        Request.Builder buildRequest = new Request.Builder();
        buildRequest.url(url);

        // Add headers if available
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                buildRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // It only call when there are POST request. For GET, it is not required
        if (formBody != null) {
            buildRequest.post(formBody);
        }

        return buildRequest;
    }

    // When user select MediaType JSON, this method should call
    private static Request.Builder createRequestBuilder(String url, String formBody, Map<String, String> headers, MediaType mediaType) {
        // Create object of request builder
        Request.Builder buildRequest = new Request.Builder();
        buildRequest.url(url);

        // Add headers if available
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                buildRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // It only call when there are POST request. For GET, it is not required
        if (formBody != null) {
            RequestBody body = RequestBody.create(mediaType, formBody);
            buildRequest.post(body);
        }

        return buildRequest;
    }

    public static void cancelRequest(String url) throws IOException {
        Log.e(HTTPUtils.class.getSimpleName(), " URL:: " + url);

        cancelCallWithTag(client, url);
    }

    public static void cancelCallWithTag(OkHttpClient client, String tag) {
        /*for (Call call : client.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) call.cancel();
        }*/
    }
}