package com.example.pest_application;

import java.io.Serializable;

public class Pest implements Serializable {
    private  int id;
    private String name;
    private int height;
    private int weight;
    private String country;
    private String category;
    private String diet;
    private String ways;
    private String tips;
    private String imageURL;

    public Pest() {
    }

    public Pest(int id, String name, int height, int weight, String country, String category, String diet, String ways, String tips, String imageURL){
        id = id;
        name = name;
        weight = weight;
        height = height;
        country = country;
        category = category;
        diet = diet;
        ways = ways;
        tips = tips;
        imageURL = imageURL;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWays() {
        return ways;
    }

    public void setWays(String ways) {
        this.ways = ways;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
