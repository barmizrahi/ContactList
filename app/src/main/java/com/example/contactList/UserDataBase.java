package com.example.contactList;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactList.DAO.UserDao;
import com.example.contactList.Entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDataBase INSTANCE;

    public static UserDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDataBase.class, "user_list").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}

