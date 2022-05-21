package com.example.contactList.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.contactList.App;
import com.example.contactList.Entity.User;
import com.example.contactList.MSPV3;
import com.example.contactList.R;
import com.example.contactList.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirebaseLoginFragmentJava extends Fragment {
    private FirebaseAuth auth;
    private Button notRegistered;
    private Button login;
    private TextInputLayout firebaseEmailLogin;
    private TextInputLayout firebasePasswordLogin;
    private ProgressBar progressBar;
    private Activity activity;
    private View view;
    private ViewModel viewModel = App.viewModel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = FirebaseAuth.getInstance();

    }

    public void onStart() {
        super.onStart();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firebase_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = this.getActivity();
        activity.setTitle("Login");
        notRegistered = view.findViewById(R.id.notRegistered);
        login = view.findViewById(R.id.login);
        firebaseEmailLogin = view.findViewById(R.id.firebaseEmailLogin);
        firebasePasswordLogin = view.findViewById(R.id.firebasePasswordLogin);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private final void UpdateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_firebaseLoginJavaFragment_to_loginJavaFragment);
        }

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notRegistered.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                Navigation.findNavController(view).navigate(R.id.action_firebaseLoginJavaFragment_to_firebaseRegisterJavaFragment);
            }
        }));
        login.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                firebaseEmailLogin.setError((CharSequence) null);
                firebasePasswordLogin.setError((CharSequence) null);
                String email = firebaseEmailLogin.getEditText().getText().toString();
                String pass = firebasePasswordLogin.getEditText().getText().toString();
                if (validateInput(email, pass)) {
                    progressBar.setVisibility(View.VISIBLE);
                    String[] mailToDataBase = email.split("@");
                    MSPV3.getMe().putString("userName", mailToDataBase[0]);
                    getAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener((OnCompleteListener) (new OnCompleteListener() {
                        public final void onComplete(Task task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                MSPV3.getMe().putString("Start", "true");
                                User user = viewModel.getUser();
                                MSPV3.getMe().putString(getString(R.string.first_name), user.getFirstName());
                                MSPV3.getMe().putString(getString(R.string.last_name), user.getLastName());
                                Navigation.findNavController(view).navigate(R.id.action_firebaseLoginJavaFragment_to_fragment_main);
                            } else {
                                Context var3 = (Context) FirebaseLoginFragmentJava.this.requireActivity();
                                StringBuilder var10001 = (new StringBuilder()).append("Authentication failed: ");
                                Exception var10002 = task.getException();
                                Toast toast = Toast.makeText(var3, (CharSequence) var10001.append(var10002 != null ? var10002.getMessage() : null).toString(), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                            }
                        }
                    }));
                }

            }
        }));
    }


    private FirebaseAuth getAuth() {
        return auth;
    }

    private final boolean validateInput(String email, String pass) {
        boolean valid = true;
        if (email.isEmpty()) {
            firebaseEmailLogin.setError("Please enter an email address");
            valid = false;
        } else if (pass.length() < 8) {
            firebasePasswordLogin.setError("Password show 8 character or more");
            valid = false;
        }
        return valid;
    }
}
