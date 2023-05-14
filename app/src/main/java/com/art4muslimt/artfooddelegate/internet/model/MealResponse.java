package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("status")
    private boolean status;

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

    public static class DataEntity {
        @Expose
        @SerializedName("prod_additions")
        private List<Prod_additionsEntity> prod_additions;
        @Expose
        @SerializedName("expectedtime")
        private String expectedtime;
        @Expose
        @SerializedName("distance")
        private String distance;
        @Expose
        @SerializedName("date_insert")
        private String date_insert;
        @Expose
        @SerializedName("active")
        private String active;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("user_id")
        private String user_id;
        @Expose
        @SerializedName("calories")
        private String calories;
        @Expose
        @SerializedName("descriptionen")
        private String descriptionen;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("price")
        private String price;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public List<Prod_additionsEntity> getProd_additions() {
            return prod_additions;
        }

        public void setProd_additions(List<Prod_additionsEntity> prod_additions) {
            this.prod_additions = prod_additions;
        }

        public String getExpectedtime() {
            return expectedtime;
        }

        public void setExpectedtime(String expectedtime) {
            this.expectedtime = expectedtime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDate_insert() {
            return date_insert;
        }

        public void setDate_insert(String date_insert) {
            this.date_insert = date_insert;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getDescriptionen() {
            return descriptionen;
        }

        public void setDescriptionen(String descriptionen) {
            this.descriptionen = descriptionen;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Prod_additionsEntity {
        @Expose
        @SerializedName("add_product")
        private List<AddProductEntity> add_product;
        @Expose
        @SerializedName("product")
        private String product;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public List<AddProductEntity> getAdd_product() {
            return add_product;
        }

        public void setAdd_product(List<AddProductEntity> add_product) {
            this.add_product = add_product;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class AddProductEntity {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("price")
        private String price;
        @Expose
        @SerializedName("calories")
        private String calories;
        @Expose
        @SerializedName("additions")
        private String additions;
        @Expose
        @SerializedName("image")
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameen() {
            return nameen;
        }

        public void setNameen(String nameen) {
            this.nameen = nameen;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getAdditions() {
            return additions;
        }

        public void setAdditions(String additions) {
            this.additions = additions;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
