package com.builderfly.parsing_app.network;

public interface OkHttpInterface {
    void onOkHttpStart(int requestId);

    void onOkHttpSuccess(int requestId, int statusCode, String response);

    void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error);

    void onOkHttpFinish(int requestId);
}
