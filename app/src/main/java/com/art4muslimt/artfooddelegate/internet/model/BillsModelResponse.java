package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillsModelResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("balance")
    private String balance;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public static class DataEntity {
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("user_id")
        private String user_id;
        @Expose
        @SerializedName("price")
        private String price;
        @Expose
        @SerializedName("operation_date")
        private String operation_date;
        @Expose
        @SerializedName("operation_number")
        private String operation_number;
        @Expose
        @SerializedName("id")
        private String id;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOperation_date() {
            return operation_date;
        }

        public void setOperation_date(String operation_date) {
            this.operation_date = operation_date;
        }

        public String getOperation_number() {
            return operation_number;
        }

        public void setOperation_number(String operation_number) {
            this.operation_number = operation_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
