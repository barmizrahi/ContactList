package com.example.contactList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.contactList.DAO.ContactDao;
import com.example.contactList.Entity.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactDatabase db = Room.databaseBuilder(getApplicationContext(),
                ContactDatabase.class, "database-name").build();
        ContactDao userDao = db.contactDao();
        List<Contact> users = userDao.getAll();
    }
}