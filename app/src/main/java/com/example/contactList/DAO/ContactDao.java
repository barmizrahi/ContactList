package com.example.contactList.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.contactList.Entity.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insertContact(Contact... product);

    @Query("SELECT * FROM contact_list WHERE first_name LIKE ('%'||:name||'%') ORDER BY first_name")
    List<Contact> findName(String name);

    @Query("SELECT * FROM contact_list WHERE phone_number LIKE ('%'||:phone||'%') ORDER BY phone_number")
    List<Contact> findPhone(String phone);

    @Query("DELETE FROM contact_list WHERE phone_number = :phone") //delete a contact by given phone number
    void deleteContact(String phone);

    @Query("SELECT * FROM contact_list WHERE user_name LIKE :user ORDER BY first_name")//get all the contacts of specific user
    LiveData<List<Contact>> getAllContacts(String user);

    @Query("DELETE FROM contact_list")
    public void nukeTable();
}
