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
    //private ViewModel mViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        edit = MSPV3.getMe().getString("editCon", "");
        if (edit.equals("true")) {
            EditExpense();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    private void EditExpense() {
        back_add.setVisibility(View.GONE);
        Contact contact = MSPV3.getMe().getObject("contact");
        title.setText("Edit Contact");
        activity.setTitle("Edit Contact");
        phoneNumber.setText(contact.getPhone_number());
        firstName.setText(contact.getFirst_name());
        lastName.setText(contact.getLast_name());
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
        String phone = phoneNumber.getText().toString();
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        if (phone.trim().isEmpty() || first.trim().isEmpty() || last.trim().isEmpty() ) {
            Toast.makeText(getContext(), "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Contact contact = new Contact(phone, first, last,MSPV3.getMe().getString("userName",""));
       // this.viewModel.deleteAllContact();
        this.viewModel.insertContact(contact);
        Navigation.findNavController(view).navigate(R.id.action_fragment_add_contact_to_fragment_main);
    }

}
