package com.example.contactList.AllFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactList.Adapters.AllContactsAdapter;
import com.example.contactList.App;
import com.example.contactList.Entity.Contact;
import com.example.contactList.R;
import com.example.contactList.MSPV3;
import com.example.contactList.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private TextView viewAllTransaction;
    private Activity activity;
    private TextView name_text;
    private FloatingActionButton buttonAddNote;
    private View view;
    private String userName;
    private ImageView Iv_exit;
    public static List<Contact> allContactsInMain;
    private ImageView info;
    //private LinearLayoutManager HorizontalLayout;
    private RecyclerView recyclerView;
    private ViewModel mViewModel = App.viewModel;
    private final AllContactsAdapter adapter = new AllContactsAdapter();
    private Context context;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //a callback method that the system call when its time for the UI to appear at the first time
        MSPV3.getMe().putString(getString(R.string.LogInBolean), "true");
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        editName();
        editLastItems();
       // recyclerView.setHasFixedSize(true);


        //add an expenses
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_main_to_fragment_add_contact);
            }
        });
        //to show all expneses
        viewAllTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_main_to_fragmentViewAllContacts);
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_main_to_fragmentInfoMain);
            }
        });

        //exit from the account
        Iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSPV3.getMe().putString("Start", "false");
                MSPV3.getMe().putString(getString(R.string.first_name), "");
                MSPV3.getMe().putString(getString(R.string.last_name), "");
                MSPV3.getMe().putString(getString(R.string.LogInBolean), "false");
                MSPV3.getMe().putString("userName", "");
                Navigation.findNavController(view).navigate(R.id.action_fragment_main_to_firebaseGoogleLoginJavaFragment);
            }
        });
        if (MSPV3.getMe().getString("Start", "").equals("true")) {
            MSPV3.getMe().putString("Start", "false");
        }

        return view;
    }

    private void editLastItems() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.setLayoutManager(HorizontalLayout);
        allContactsInMain = new ArrayList<>();
        recyclerView.setAdapter(adapter);
        mViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                allContactsInMain = contacts;
                for(int i=0;i<contacts.size();i++){
                    Log.e("TT",contacts.get(i).toString());
                }
                if (allContactsInMain != null) {
                    adapter.setNotes(contacts);
                    //recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void editName() {
        name_text.setText(MSPV3.getMe().getString(getString(R.string.first_name), "") + " " + (MSPV3.getMe().getString(getString(R.string.last_name), "")));
    }

    private void initView(View view) {
        activity = this.getActivity();
        activity.setTitle("Contact Manager");
        buttonAddNote = view.findViewById(R.id.button_add_note);
        Iv_exit = view.findViewById(R.id.Iv_exit);
        info = view.findViewById(R.id.info);
        viewAllTransaction = view.findViewById(R.id.viewAllContacts);
        name_text = view.findViewById(R.id.name_text);
        recyclerView = view.findViewById(R.id.recycler_view);

    }


    public void callParentMethod() {
        activity.onBackPressed();
    }

}
