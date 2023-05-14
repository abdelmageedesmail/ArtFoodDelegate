package com.art4muslimt.artfooddelegate.internet.model;

import android.graphics.Bitmap;

import java.io.File;

public class AddAdditionModel {

    private String name, price, calories;
    private File image;
    private Bitmap imageBm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Bitmap getImageBm() {
        return imageBm;
    }

    public void setImageBm(Bitmap imageBm) {
        this.imageBm = imageBm;
    }
}
