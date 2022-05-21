package com.example.contactList;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactList.Entity.Contact;
import com.example.contactList.Entity.User;
import com.example.contactList.Repository.ContactRep;
import com.example.contactList.Repository.UserRep;

public class ViewModel extends AndroidViewModel {
    private static ContactRep repository;
    private static UserRep userRep;
    private LiveData<List<Contact>> allContacts;
    private User user;
    private MutableLiveData<List<Contact>> searchResults;

    //CONSTRUCTOR
    public ViewModel(Application application) {
        super(application);
        userRep = new UserRep(application);
        repository = new ContactRep(application);
        //this.user = userRep.getUser();
        allContacts = repository.getAllContacts();
        searchResults = repository.getSearchResults();
    }

    //GETTERS
    public MutableLiveData<List<Contact>> getSearchResults() {
        return searchResults;
    }

    public LiveData<List<Contact>> getAllContacts() {
        allContacts = repository.getAllContacts();
        return allContacts;
    }

    public User getUser() {
        return userRep.getUser();
    }

    //REPOSITORY OPERATIONS (insert, find, delete)
    public void insertContact(Contact contact) {
        repository.insertContact(contact);
    }

    public void findName(String name) {
        repository.findName(name);
    }

    public void findPhone(String phone) {
        repository.findPhone(phone);
    }

    public void deleteContact(String name) {
        repository.deleteContact(name);
    }

    public void deleteAllContact() {
        repository.deleteAllContact();
    }

    public void insertUser(User user) {
        userRep.insertUser(user);
    }
}