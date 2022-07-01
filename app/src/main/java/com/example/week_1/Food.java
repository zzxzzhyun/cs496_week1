package com.example.week_1;

public class Food {
    private String Restaurant;
    private String Category;
    private int Thumbnail;

    public Food(String restaurant, String category, int thumbnail) {
        Restaurant = restaurant;
        Category = category;
        Thumbnail = thumbnail;
    }

    public String getRestaurant(){
        return Restaurant;
    }

    public String getCategory(){
        return Category;
    }

    public int getThumbnail(){
        return Thumbnail;
    }
}
