package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetails {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("user_details")
    private User_detailsEntity user_details;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User_detailsEntity getUser_details() {
        return user_details;
    }

    public void setUser_details(User_detailsEntity user_details) {
        this.user_details = user_details;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class User_detailsEntity {
        @Expose
        @SerializedName("allusercategory")
        private List<AllusercategoryEntity> allusercategory;
        @Expose
        @SerializedName("familystatus_nameen")
        private String familystatus_nameen;
        @Expose
        @SerializedName("familystatus_name")
        private String familystatus_name;
        @Expose
        @SerializedName("stars")
        private String stars;
        @Expose
        @SerializedName("commercialrecord")
        private String commercialrecord;
        @Expose
        @SerializedName("familystatus")
        private String familystatus;
        @Expose
        @SerializedName("iban")
        private String iban;
        @Expose
        @SerializedName("bankname")
        private String bankname;
        @Expose
        @SerializedName("active")
        private String active;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("mobile")
        private String mobile;
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public List<AllusercategoryEntity> getAllusercategory() {
            return allusercategory;
        }

        public void setAllusercategory(List<AllusercategoryEntity> allusercategory) {
            this.allusercategory = allusercategory;
        }

        public String getFamilystatus_nameen() {
            return familystatus_nameen;
        }

        public void setFamilystatus_nameen(String familystatus_nameen) {
            this.familystatus_nameen = familystatus_nameen;
        }

        public String getFamilystatus_name() {
            return familystatus_name;
        }

        public void setFamilystatus_name(String familystatus_name) {
            this.familystatus_name = familystatus_name;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public String getCommercialrecord() {
            return commercialrecord;
        }

        public void setCommercialrecord(String commercialrecord) {
            this.commercialrecord = commercialrecord;
        }

        public String getFamilystatus() {
            return familystatus;
        }

        public void setFamilystatus(String familystatus) {
            this.familystatus = familystatus;
        }

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

    public static class AllusercategoryEntity {
        @Expose
        @SerializedName("nameen")
        private String nameen;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("cat_id")
        private String cat_id;

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
