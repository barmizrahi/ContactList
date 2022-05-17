package com.example.contactList;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactList.DAO.ContactDao;
import com.example.contactList.Entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
