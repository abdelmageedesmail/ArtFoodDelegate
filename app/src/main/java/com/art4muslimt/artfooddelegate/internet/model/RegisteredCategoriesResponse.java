package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisteredCategoriesResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("category_productivefamily")
    private List<Category_productivefamilyEntity> category_productivefamily;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category_productivefamilyEntity> getCategory_productivefamily() {
        return category_productivefamily;
    }

    public void setCategory_productivefamily(List<Category_productivefamilyEntity> category_productivefamily) {
        this.category_productivefamily = category_productivefamily;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Category_productivefamilyEntity {
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
        @SerializedName("cat_id")
        private String cat_id;

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

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }
    }
}
