package com.example.contactList.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_list")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "contactId")
    public int id;

    @ColumnInfo(name = "phone_number")
    public String phone_number;

    @ColumnInfo(name = "first_name")
    public String first_name;

    @ColumnInfo(name = "last_name")
    public String last_name;

    @ColumnInfo(name = "user_name")
    public String user_name;


    public Contact(String phone_number ,String first_name , String last_name, String user_name)
    {
        this.last_name = last_name;
        this.first_name = first_name;
        this.phone_number = phone_number;
        this.id = id;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Contact setId(int id) {
        this.id = id;
        return this;
    }

    public Contact setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public Contact setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public Contact setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public Contact setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }
}
