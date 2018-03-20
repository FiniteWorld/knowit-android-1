package io.github.passioninfinite.knowit;

import org.json.JSONObject;

/**
 * Created by passioninfinite on 19/3/18.
 */

public class Company {
    private String name, location, imageUrl;

    private JSONObject sticker;

    public JSONObject getSticker() {
        return sticker;
    }

    public void setSticker(JSONObject sticker) {
        this.sticker = sticker;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company() {

    }

    public Company(String name, String location, String imageUrl, JSONObject sticker) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.sticker = sticker;
    }
}
