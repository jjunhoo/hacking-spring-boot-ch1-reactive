package com.grelturnquist.hackingspringboot.reactive.commerce.domain;

import org.springframework.data.annotation.Id;

import javax.xml.crypto.Data;
import java.awt.*;

public class Item {
    @Id // mongo db 컬렉션 id 필드로 사용할 필드 설정
    private String id;
    private String name;
    private String description;
    private double price;
    private Data releaseDate;
    private int availableUnits;
    private Point location;
    private boolean active;

    private Item() {}

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Data getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Data releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
