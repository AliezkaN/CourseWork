package com.nahorniak.aircompany.entities;

public class Plane {
    private int id,totalSeats,crewCapacity;
    private String name,type;
    private double fuelCapacity,fuelUsage,maxRange,loadCapacity;

    public Plane(int id,
                 String name, String type,
                 double fuelCapacity, double fuelUsage,int totalSeats,
                 double maxRange, double loadCapacity,int crewCapacity) {
        this.id = id;
        this.totalSeats = totalSeats;
        this.crewCapacity = crewCapacity;
        this.name = name;
        this.type = type;
        this.fuelCapacity = fuelCapacity;
        this.fuelUsage = fuelUsage;
        this.maxRange = maxRange;
        this.loadCapacity = loadCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getCrewCapacity() {
        return crewCapacity;
    }

    public void setCrewCapacity(int crewCapacity) {
        this.crewCapacity = crewCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getFuelUsage() {
        return fuelUsage;
    }

    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
