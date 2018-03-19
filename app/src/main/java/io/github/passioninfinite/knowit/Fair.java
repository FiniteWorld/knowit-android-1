package io.github.passioninfinite.knowit;

/**
 * Created by passioninfinite on 18/3/18.
 */

public class Fair {

    private String id, name, location, start_time, end_time, date;

    public Fair() {

    }

    public Fair(String id, String name, String location, String start_time, String end_time, String date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getStartTime() {
        return this.start_time;
    }

    public String getEndTime() {
        return this.end_time;
    }

    public String getDate() {
        return this.date;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public void setEndTime(String end_time) {
        this.end_time = end_time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }
}
