package com.example.contactList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gender {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("probability")
    @Expose
    private Float probability;
    @SerializedName("count")
    @Expose
    private int count;

    public String getName() {
        return name;
    }

    public Gender setName(String name) {
        this.name = name;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Gender setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Float getProbability() {
        return probability;
    }

    public Gender setProbability(Float probability) {
        this.probability = probability;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Gender setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "gender{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", probability=" + probability +
                ", count=" + count +
                '}';
    }
}
