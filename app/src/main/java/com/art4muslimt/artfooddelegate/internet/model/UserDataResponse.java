package com.art4muslimt.artfooddelegate.internet.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private DataEntity data;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
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
        @SerializedName("city_nameen")
        private String city_nameen;
        @Expose
        @SerializedName("city_name")
        private String city_name;
        @Expose
        @SerializedName("typevehicle_nameen")
        private String typevehicle_nameen;
        @Expose
        @SerializedName("typevehicle_name")
        private String typevehicle_name;
        @Expose
        @SerializedName("nationality_nameen")
        private String nationality_nameen;
        @Expose
        @SerializedName("nationality_name")
        private String nationality_name;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("hourto")
        private String hourto;
        @Expose
        @SerializedName("hourfrom")
        private String hourfrom;
        @Expose
        @SerializedName("vehicleplatenumber")
        private String vehicleplatenumber;
        @Expose
        @SerializedName("license")
        private String license;
        @Expose
        @SerializedName("typevehicle")
        private String typevehicle;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("nationality")
        private String nationality;
        @Expose
        @SerializedName("idphoto")
        private String idphoto;
        @Expose
        @SerializedName("stars")
        private String stars;
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

        public String getCity_nameen() {
            return city_nameen;
        }

        public void setCity_nameen(String city_nameen) {
            this.city_nameen = city_nameen;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getTypevehicle_nameen() {
            return typevehicle_nameen;
        }

        public void setTypevehicle_nameen(String typevehicle_nameen) {
            this.typevehicle_nameen = typevehicle_nameen;
        }

        public String getTypevehicle_name() {
            return typevehicle_name;
        }

        public void setTypevehicle_name(String typevehicle_name) {
            this.typevehicle_name = typevehicle_name;
        }

        public String getNationality_nameen() {
            return nationality_nameen;
        }

        public void setNationality_nameen(String nationality_nameen) {
            this.nationality_nameen = nationality_nameen;
        }

        public String getNationality_name() {
            return nationality_name;
        }

        public void setNationality_name(String nationality_name) {
            this.nationality_name = nationality_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHourto() {
            return hourto;
        }

        public void setHourto(String hourto) {
            this.hourto = hourto;
        }

        public String getHourfrom() {
            return hourfrom;
        }

        public void setHourfrom(String hourfrom) {
            this.hourfrom = hourfrom;
        }

        public String getVehicleplatenumber() {
            return vehicleplatenumber;
        }

        public void setVehicleplatenumber(String vehicleplatenumber) {
            this.vehicleplatenumber = vehicleplatenumber;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getTypevehicle() {
            return typevehicle;
        }

        public void setTypevehicle(String typevehicle) {
            this.typevehicle = typevehicle;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getIdphoto() {
            return idphoto;
        }

        public void setIdphoto(String idphoto) {
            this.idphoto = idphoto;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
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
}
