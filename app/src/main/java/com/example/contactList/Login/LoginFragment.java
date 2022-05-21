package com.example.contactList.Login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.contactList.App;
import com.example.contactList.Entity.User;
import com.example.contactList.MSPV3;
import com.example.contactList.R;
import com.example.contactList.ViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private Button continueButton;
    private View view;
    private ImageView info;
    private ViewModel viewModel = App.viewModel;
    private TextWatcher boardingTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String firstName = LoginFragment.this.firstName.getEditText().getText().toString();
            String lastName = LoginFragment.this.lastName.getEditText().getText().toString();
            boolean firstnameGood = false, lastnameGood = false;

            if (firstName.isEmpty()) {
                continueButton.setEnabled(false);
                LoginFragment.this.firstName.setError("This Field Cannot Be Empty");
            } else {
                firstnameGood = true;
            }

            if (lastName.isEmpty()) {
                continueButton.setEnabled(false);
                LoginFragment.this.lastName.setError("This Field Cannot Be Empty");
            } else {
                LoginFragment.this.lastName.setErrorEnabled(false);
                lastnameGood = true;
            }

            if (firstnameGood && lastnameGood) {
                continueButton.setEnabled(true);


            }

        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle("Personal Details");
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        continueButton = view.findViewById(R.id.continueButton);
        info = view.findViewById(R.id.info);
        continueButton.setEnabled(false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSPV3.getMe().putString("userName", firstName.getEditText().getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_fragmentInfoLogIn);
            }
        });
        firstName.getEditText().addTextChangedListener(boardingTextWatcher);
        lastName.getEditText().addTextChangedListener(boardingTextWatcher);
        continueButton.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                String name = firstName.getEditText().getText().toString();
                String last = lastName.getEditText().getText().toString();
                MSPV3.getMe().putString(getString(R.string.first_name), name);
                MSPV3.getMe().putString(getString(R.string.last_name), last);
                MSPV3.getMe().putString("Start", "true");
                User user = new User(MSPV3.getMe().getString("userName", ""), name, last);
                viewModel.insertUser(user);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_fragment_main);
            }
        }));
    }

}
