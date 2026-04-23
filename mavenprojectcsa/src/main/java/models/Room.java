/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author senuthmi
 */

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String id;
    private String name;
    private List<String> sensorIds;
    private int capacity;

    public Room() {
        this.sensorIds = new ArrayList<>();
    }

    public Room(String id, String name, List<String> sensorIds, int capacity) {
        this.id = id;
        this.name = name;
        this.sensorIds = (sensorIds != null) ? new ArrayList<>(sensorIds) : new ArrayList<>();
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = (sensorIds != null) ? new ArrayList<>(sensorIds) : new ArrayList<>();
    }

    public void addSensorId(String sensorId) {
        this.sensorIds.add(sensorId);
    }

    public void removeSensorId(String sensorId) {
        this.sensorIds.remove(sensorId);
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}