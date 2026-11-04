package com.example.poster1019;

/**
 * This Poster class contains a poster's selection status, rating, image, unique ID,
 * title, producer, and description. Most of this data is displayed in the Main and
 * Favorites pages.
 */

public class Poster {
    Boolean isSelected = false;
    float rating;
    int image;
    String id;
    String title, producer, description;
}
