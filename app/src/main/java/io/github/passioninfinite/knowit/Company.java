package io.github.passioninfinite.knowit;

/**
 * Created by passioninfinite on 19/3/18.
 */

public class Company {
    private String name, location, imageUrl;

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

    public Company(String name, String location, String imageUrl) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }
}
