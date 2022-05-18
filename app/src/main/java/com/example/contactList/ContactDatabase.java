package com.example.contactList;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactList.DAO.ContactDao;
import com.example.contactList.DAO.UserDao;
import com.example.contactList.Entity.Contact;

@Database(entities = {Contact.class}, version = 2, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    //public abstract UserDao userDao();
    private static ContactDatabase INSTANCE;
    public static ContactDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (ContactDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDatabase.class, "contact_list").build();
                }
            }
        }
        return INSTANCE;
    }
}
