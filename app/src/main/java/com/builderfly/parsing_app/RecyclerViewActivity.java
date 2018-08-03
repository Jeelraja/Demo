package com.builderfly.parsing_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.builderfly.parsing_app.network.OkHttpInterface;
import com.builderfly.parsing_app.network.OkHttpRequest;
import com.builderfly.parsing_app.network.RequestParam;
import com.google.gson.Gson;

public class RecyclerViewActivity extends AppCompatActivity implements OkHttpInterface {

    RecyclerView mRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecycler = findViewById(R.id.recycler_shop_list);
        callAllCategoryService();
    }

    /**
     * @param
     * @return
     * @throws
     * @purpose to send request to send request to the server for AllCategory
     */
    private void callAllCategoryService() {
        // Check internet connection to proceed
        if (!CommonUtils.isInternetAvailable(RecyclerViewActivity.this)) {
            return;
        }
        new OkHttpRequest(RecyclerViewActivity.this, OkHttpRequest.Method.POST,
                Constants.Urls.ALL_CATEGORIES,
                RequestParam.allCategories(),
                RequestParam.getCommonHeaderWithContentType(),
                Constants.CODE_ALL_CATEGORIES,
                true,
                this,
                RequestParam.getCommonHeaderWithContentType());
    }


    /**
     * @param output
     * @return
     * @throws
     * @purpose to handle response from server for AllCategories
     */
    private void handleResponseAllCategories(String output) {
        //AppLog.LogE("handleResponseAllCategories", output);
        final Gson gson = new Gson();
        try {
            AllCategoryModel allCategoryModel = gson.fromJson(output, AllCategoryModel.class);
            if (allCategoryModel != null) {
                if (allCategoryModel.getStatus() == 1) {
                    mRecycler.setHasFixedSize(true);
                    mRecycler.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
                    mRecycler.setAdapter(new ShopListAdapter(this, allCategoryModel.getData()));
                } else {
                    Toast.makeText(RecyclerViewActivity.this, "" + allCategoryModel.getStatus(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RecyclerViewActivity.this, "" + allCategoryModel.getStatus(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.displayToast(RecyclerViewActivity.this, "str_something_went_worng");
        }
    }

    @Override
    public void onOkHttpStart(int requestId) {

    }

    @Override
    public void onOkHttpSuccess(int requestId, int statusCode, String response) {
        if (requestId == Constants.CODE_ALL_CATEGORIES) {
            handleResponseAllCategories(response);
        }
    }

    @Override
    public void onOkHttpFailure(int requestId, int statusCode, String response, Throwable error) {

    }

    @Override
    public void onOkHttpFinish(int requestId) {

    }
}
