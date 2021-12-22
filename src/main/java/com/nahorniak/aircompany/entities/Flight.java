package com.nahorniak.aircompany.entities;

public class Flight {
    private int id,depAirportId,arrAirportId;
    private String depTime,arrTime;
    private int planeId;
    private double range;

    public Flight(int id, int depAirportId, int arrAirportId, String depTime, String arrTime, int planeId, double range) {
        this.id = id;
        this.depAirportId = depAirportId;
        this.arrAirportId = arrAirportId;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.planeId = planeId;
        this.range = range;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepAirportId() {
        return depAirportId;
    }

    public void setDepAirportId(int depAirportId) {
        this.depAirportId = depAirportId;
    }

    public int getArrAirportId() {
        return arrAirportId;
    }

    public void setArrAirportId(int arrAirportId) {
        this.arrAirportId = arrAirportId;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
