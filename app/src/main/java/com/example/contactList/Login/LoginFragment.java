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
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.contactList.MSPV3;
import com.example.contactList.R;
import com.example.finalprojectexpensemanager.Repository.ExpenseRepository;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private Button continueButton;
    private View view;
    private ImageView info;
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
            }
            else
                lastnameGood = true;

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
                saveCredentials(name, last);
                MSPV3.getMe().putString("Start", "true");
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_fragment_main);
            }
        }));
    }

    private void saveCredentials(String name, String last) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.EXPENSE_TABLE_APP));
        myRef.child(ExpenseRepository.userName).child(getString(R.string.NAMEDB)).setValue(name);
        myRef.child(ExpenseRepository.userName).child(getString(R.string.BUDGETDB)).setValue("" + budget);
        MSPV3.getMe().putString("MPAmount", "" + budget);
        myRef.child(ExpenseRepository.userName).child(getString(R.string.RESET)).setValue("" + budget);
        myRef.child(ExpenseRepository.userName).child(getString(R.string.INCOMEDB)).setValue("" + income);
        ExpenseRepository.reset = Integer.parseInt(budget);
        MSPV3.getMe().putString("MPReset", budget);
        //insert into MPV3
        ExpenseRepository.amount = Integer.parseInt(budget);
        ExpenseRepository.counter = 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String coin = parent.getItemAtPosition(position).toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.EXPENSE_TABLE_APP));
        switch (coin) {
            case "NIS":
                myRef.child(ExpenseRepository.userName).child(getString(R.string.COIN)).setValue("₪");
                MSPV3.getMe().putString("MPCoin", "₪");
                ExpenseRepository.coin = "₪";
                //insert into MPV3
                break;
            case "Dollar":
                myRef.child(ExpenseRepository.userName).child(getString(R.string.COIN)).setValue("$");
                MSPV3.getMe().putString("MPCoin", "$");
                ExpenseRepository.coin = "$";
                //insert into MPV3
                break;
            case "Euro":
                myRef.child(ExpenseRepository.userName).child(getString(R.string.COIN)).setValue("€");
                MSPV3.getMe().putString("MPCoin", "€");
                ExpenseRepository.coin = "€";
                //insert into MPV3
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
