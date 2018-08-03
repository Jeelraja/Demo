package com.builderfly.parsing_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.builderfly.parsing_app.network.OkHttpInterface;
import com.builderfly.parsing_app.network.OkHttpRequest;
import com.builderfly.parsing_app.network.RequestParam;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements OkHttpInterface {

    private Button mBtnParse, mBtnRecycler;
    private TextView mTvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnParse = findViewById(R.id.btnParse);
        mBtnRecycler = findViewById(R.id.btnRecycler);
        mTvResponse = findViewById(R.id.tvResponse);

        mBtnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiForUserRegistration("ICXE1wOphgKgcyMoHr0hVHbbJ", "jeel@zapserver.com", "123456");
            }
        });

        mBtnRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void callApiForUserRegistration(String apiKey, String emailAddress, String password) {
        // Check internet connection to proceed
        if (!CommonUtils.isInternetAvailable(MainActivity.this)) {
            return;
        }

        new OkHttpRequest(MainActivity.this, OkHttpRequest.Method.POST,
                Constants.USER_LOGIN,
                RequestParam.userLogin(apiKey, emailAddress, password),
                RequestParam.getNull(),
                Constants.CODE_USER_LOGIN,
                true,
                this,
                RequestParam.getNull());
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        Log.i("Response", "++" + response);
        if (requestId == Constants.CODE_USER_LOGIN) {
            handleResponseUserLogin(response);
        }
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }

    private void handleResponseUserLogin(String output) {
        final Gson gson = new Gson();
        try {
            CustomerLoginModel customerLoginModel = gson.fromJson(output, CustomerLoginModel.class);
            if (customerLoginModel != null) {
                if (customerLoginModel.getStatus() == 1) {
                    mTvResponse.setText("" + output);
                } else {
                }
            } else {
                CommonUtils.displayToast(MainActivity.this, "" + customerLoginModel.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.displayToast(MainActivity.this, "Something worng");
        }
    }
}
