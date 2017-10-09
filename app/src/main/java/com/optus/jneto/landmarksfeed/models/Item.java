package com.optus.jneto.landmarksfeed.models;

import java.io.Serializable;

/**
 * Created by jneto on 10/6/17.
 */

public class Item implements Serializable {

    private int id;
    private String name;
    private FromCentral fromcentral;
    private Location location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FromCentral getFromCentral() {
        return fromcentral;
    }

    public void setFromCentral(FromCentral fromCentral) {
        this.fromcentral = fromCentral;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class FromCentral implements Serializable {

        public final String car;
        public final String train;

        public FromCentral(String car, String train) {
            this.car = car;
            this.train = train;
        }

        public String getCar() {
            return car;
        }

        public String getTrain() {
            return train;
        }

        @Override
        public String toString() {
            return "FromCentral{" +
                    "car='" + car + '\'' +
                    ", train='" + train + '\'' +
                    '}';
        }
    }

    public static class Location implements Serializable {

        public final Double latitude;
        public final Double longitude;

        public Location(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
