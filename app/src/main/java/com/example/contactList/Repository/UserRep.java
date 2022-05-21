package com.example.contactList.Repository;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import com.example.contactList.DAO.UserDao;
import com.example.contactList.Entity.User;
import com.example.contactList.MSPV3;
import com.example.contactList.UserDataBase;

import okhttp3.Dispatcher;

public class UserRep {

    private User user;
    private UserDao userDao;


    public UserRep(Application application) {
        UserDataBase userDatabase;
        userDatabase = UserDataBase.getDatabase(application);
        userDao = userDatabase.userDao();
    }

    public void insertUser(User newUser) {
        InsertAsyncTask task = new InsertAsyncTask(userDao);
        task.execute(newUser);
    }

    public User getUser() {
        String user_name = MSPV3.getMe().getString("userName", "");
        user = userDao.loadByUserName(user_name);
        return user;
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        InsertAsyncTask(UserDao dao) {
            userDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            userDao.insertAll(params[0]);
            return null;
        }
    }

}
