package com.builderfly.parsing_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategoryModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<AllCategoriesDataModel> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AllCategoriesDataModel> getData() {
        return data;
    }

    public void setData(List<AllCategoriesDataModel> data) {
        this.data = data;
    }

}
