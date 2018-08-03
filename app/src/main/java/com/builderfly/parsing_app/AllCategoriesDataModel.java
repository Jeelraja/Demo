package com.builderfly.parsing_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategoriesDataModel {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("child")
    @Expose
    private List<AllCategoriesDataChildModel> child = null;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<AllCategoriesDataChildModel> getChild() {
        return child;
    }

    public void setChild(List<AllCategoriesDataChildModel> child) {
        this.child = child;
    }
}
