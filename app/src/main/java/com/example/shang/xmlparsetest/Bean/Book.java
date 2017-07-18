package com.example.shang.xmlparsetest.Bean;

/**
 * Created by shang on 2017/7/18.
 */

public class Book {
    int id;
    String name;
    float price;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id: "+id + ", name: "+name+", price: "+price;
    }
}
