package com.example.contactList.Repository;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.contactList.ContactDatabase;
import com.example.contactList.DAO.ContactDao;
import com.example.contactList.Entity.Contact;
import com.example.contactList.MSPV3;

public class ContactRep
{
    private static final String NAME = "first_name";
    private static final String PHONE = "phone_number";
    private static final String LASTNAME = "last_name";


    private MutableLiveData<List<Contact>> searchResults = new MutableLiveData<>();
    private LiveData<List<Contact>> allContacts;
    private ContactDao contactDao;

    //CONSTRUCTOR
    public ContactRep(Application application)
    {
        ContactDatabase db;
        db = ContactDatabase.getDatabase(application);
        contactDao = db.contactDao();
        String user_name = MSPV3.getMe().getString("userName","");
        allContacts = contactDao.getAllContacts(user_name);
    }

    //ASYNC CALLS (insert, delete, find)
    public void insertContact(Contact newContact)
    {
        InsertAsyncTask task = new InsertAsyncTask(contactDao);
        task.execute(newContact);
    }
    public void deleteContact(String phone)
    {
        DeleteAsyncTask task = new DeleteAsyncTask(contactDao);
        task.execute(phone);
    }
    public void findName(String name)
    {
        QueryAsyncTask task = new QueryAsyncTask(contactDao, NAME);
        task.delegate = this;
        task.execute(name);
    }
    public void findPhone(String phone)
    {
        QueryAsyncTask task = new QueryAsyncTask(contactDao, PHONE);
        task.delegate = this;
        task.execute(phone);
    }

    public void deleteAllContact(){
        DeleteAllAsyncTask task = new DeleteAllAsyncTask(contactDao);
        task.execute();

    }

    //GETTERS & SETTERS
    public LiveData<List<Contact>> getAllContacts() { return allContacts;}
    public MutableLiveData<List<Contact>> getSearchResults() { return searchResults; }
    private void asyncFinished(List<Contact> results) { searchResults.setValue(results); }


    //ASYNC QUERY (Find)
    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Contact>>
    {
        private String criterion;
        private ContactDao asyncTaskDao;
        private static ContactRep delegate = null;

        //Constructor
        QueryAsyncTask(ContactDao dao, String criterion)
        {
            this.criterion= criterion;
            asyncTaskDao = dao;
        }

        @Override
        protected List<Contact> doInBackground(final String... params)
        {
            if (criterion == NAME) return asyncTaskDao.findName(params[0]);

            else if (criterion == PHONE) return asyncTaskDao.findPhone(params[0]);

            else return new ArrayList<Contact>();
        }

        @Override
        protected void onPostExecute(List<Contact> result) { delegate.asyncFinished(result); }
    }

    //ASYNC INSERT
    private static class InsertAsyncTask extends AsyncTask< Contact, Void, Void>
    {
        private  ContactDao asyncTaskDao;
        InsertAsyncTask( ContactDao dao) { asyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Contact... params)
        {
            asyncTaskDao.insertContact(params[0]);
            return null;
        }
    }

    //ASYNC DELETE
    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void>
    {
        private ContactDao asyncTaskDao;
        DeleteAsyncTask(ContactDao dao) { asyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final String... params)
        {
            asyncTaskDao.deleteContact(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<String, Void, Void>
    {
        private ContactDao asyncTaskDao;
        DeleteAllAsyncTask(ContactDao dao) { asyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final String... params)
        {
            asyncTaskDao.nukeTable();
            return null;
        }
    }

}
