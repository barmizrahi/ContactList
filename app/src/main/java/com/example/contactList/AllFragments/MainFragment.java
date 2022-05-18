package com.example.contactList.AllFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.contactList.R;
import com.example.contactList.MSPV3;
import com.example.contactList.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainFragment extends Fragment {
    private TextView viewAllTransaction;
    private Activity activity;
    private TextView name_text;
    private FloatingActionButton buttonAddNote;
    private View view;
    private String userName;
    private ImageView Iv_exit;
    private ImageView info;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MSPV3.getMe().putString(getString(R.string.LogInBolean), "true");
        Context context = container.getContext();
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        editName();
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
                Navigation.findNavController(view).navigate(R.id.action_fragment_main_to_firebaseGoogleLoginJavaFragment);
            }
        });
        if (MSPV3.getMe().getString("Start", "").equals("true")) {
            MSPV3.getMe().putString("Start", "false");
        }

        return view;
    }

    private void editName() {
        name_text.setText(MSPV3.getMe().getString(getString(R.string.first_name), "")+" "+(MSPV3.getMe().getString(getString(R.string.last_name), "")));
    }

    private void initView(View view) {
        activity = this.getActivity();
        activity.setTitle("Contact Manager");
        buttonAddNote = view.findViewById(R.id.button_add_note);
        Iv_exit = view.findViewById(R.id.Iv_exit);
        info = view.findViewById(R.id.info);
        viewAllTransaction = view.findViewById(R.id.viewAllTransaction);
        name_text = view.findViewById(R.id.name_text);

    }




    public void callParentMethod() {
        activity.onBackPressed();
    }

}
