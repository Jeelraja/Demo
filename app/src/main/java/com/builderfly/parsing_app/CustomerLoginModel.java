package com.builderfly.parsing_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerLoginModel {


    @Expose
    @SerializedName("applied_coupon")
    private String appliedCoupon;
    @Expose
    @SerializedName("cart_count")
    private int cartCount;
    @Expose
    @SerializedName("userdetails")
    private Userdetails userdetails;
    @Expose
    @SerializedName("status")
    private int status;

    public String getAppliedCoupon() {
        return appliedCoupon;
    }

    public void setAppliedCoupon(String appliedCoupon) {
        this.appliedCoupon = appliedCoupon;
    }

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public Userdetails getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(Userdetails userdetails) {
        this.userdetails = userdetails;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Userdetails {
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("lastname")
        private String lastname;
        @Expose
        @SerializedName("firstname")
        private String firstname;
        @Expose
        @SerializedName("userid")
        private String userid;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
