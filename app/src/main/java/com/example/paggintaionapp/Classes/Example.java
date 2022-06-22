package com.example.paggintaionapp.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable {

    @SerializedName("totalPassengers")
    @Expose
    private Integer totalPassengers;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Datum implements Serializable {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("trips")
        @Expose
        private Integer trips;
        @SerializedName("airline")
        @Expose
        private List<Airline> airline = null;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTrips() {
            return trips;
        }

        public void setTrips(Integer trips) {
            this.trips = trips;
        }

        public List<Airline> getAirline() {
            return airline;
        }

        public void setAirline(List<Airline> airline) {
            this.airline = airline;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
}
