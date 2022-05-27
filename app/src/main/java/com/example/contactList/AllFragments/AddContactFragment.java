package com.example.contactList.AllFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.contactList.App;
import com.example.contactList.Entity.Contact;
import com.example.contactList.MSPV3;
import com.example.contactList.R;
import com.example.contactList.Repository.ContactRep;
import com.example.contactList.ViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;


public class AddContactFragment extends Fragment {
    private TextInputEditText phoneNumber;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private MaterialTextView title;
    private MaterialButton save;
    private ImageButton back_add;
    private Activity activity;
    private View view;
    private Context context;
    public static String edit;
    private ViewModel viewModel = App.viewModel;
    private Contact contacToEdit;
    //private ViewModel mViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //a callback method that the system call when its time for the UI to appear at the first time
        view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        initView();
        context = container.getContext();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        back_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_add_contact_to_fragment_main);
            }
        });
        edit = MSPV3.getMe().getString("editCon", ""); // if we in the page of add but need to edit
        if (edit.equals("true")) {
            EditExpense();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this.viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    private void EditExpense() {
        back_add.setVisibility(View.GONE);
        contacToEdit = MSPV3.getMe().getObject("contact");
        title.setText("Edit Contact");
        activity.setTitle("Edit Contact");
        phoneNumber.setText(contacToEdit.getPhone_number());
        firstName.setText(contacToEdit.getFirst_name());
        lastName.setText(contacToEdit.getLast_name());
        MSPV3.getMe().putString("editCon", "false");
    }

    private void initView() {
        activity = getActivity();
        phoneNumber = view.findViewById(R.id.phone_input);
        firstName = view.findViewById(R.id.first_name_input);
        lastName = view.findViewById(R.id.last_name_input);
        save = view.findViewById(R.id.addButton);
        title = view.findViewById(R.id.titleID);
        back_add = view.findViewById(R.id.back_add);
        activity.setTitle("Add Contact");
        title.setText("Add Contact");
    }

    private void saveNote() {
        //get all the data from the TextInputEditText
        String phone = phoneNumber.getText().toString();
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        if (phone.trim().isEmpty() || first.trim().isEmpty() || last.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.length()!=10){
            phoneNumber.setError("A Phone Number Must Have Exactly 10 Digits");
            return;
        }
        List<Contact> l = new ArrayList<>();
        l = MainFragment.allContactsInMain;
        for(int i=0;i<l.size();i++){
            if(l.get(i).phone_number.equals(phone)){//if there is alredy contact with the same phone number
                if(edit.equals("true")){//if we edit then maybe we dont edit the phone number
                    if(l.get(i).getId()==contacToEdit.getId())//if it is the contact we want to edit then break
                    break;
                }
                //else, im not editing im trying to add a cotnact with the same phone number
                Toast.makeText(getContext(), "You Have Already Has This Contact In Your Contact List", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //all good and we create a new contact
        Contact contact = new Contact(phone, first, last, MSPV3.getMe().getString("userName", ""));
        //insert to the dateBase
        this.viewModel.insertContact(contact);
        Navigation.findNavController(view).navigate(R.id.action_fragment_add_contact_to_fragment_main);
    }

}
