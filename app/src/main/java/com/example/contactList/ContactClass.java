package com.example.contactList;

import android.content.Context;

import androidx.room.Room;

import com.example.contactList.Entity.Contact;

import java.util.List;

public class ContactClass {
    private static ContactDatabase contactDatabase;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    public ContactClass(Context context){
        this.contactDatabase = Room.databaseBuilder(context,
                ContactDatabase.class, "database-name").build();
    }

    public ContactClass(Context context,String PhoneNumber ,String first , String last){
        this.contactDatabase = Room.databaseBuilder(context,
                ContactDatabase.class, "database-name").build();
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = PhoneNumber;
    }

    public ContactClass(String PhoneNumber ,String first , String last){
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = PhoneNumber;
    }

    public void insertContact(Contact contact){
        this.contactDatabase.contactDao().insertAll(contact);
    }
    public List<Contact> getAllContacts(){
        return this.contactDatabase.contactDao().getAll();
    }

    public Contact getContactByPhoneNumber(String phone){
        return this.contactDatabase.contactDao().loadAllByPhoneNumber(phone);
    }

    public Contact findByName (String first ,String last){
        return this.contactDatabase.contactDao().findByName(first,last);
    }

    public void DeleteAContact(Contact contact){
        this.contactDatabase.contactDao().delete(contact);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ContactClass setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ContactClass setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactClass setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
