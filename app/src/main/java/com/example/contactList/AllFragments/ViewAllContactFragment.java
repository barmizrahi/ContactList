package com.example.contactList.AllFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contactList.Adapters.AllContactsAdapter;
import com.example.contactList.App;
import com.example.contactList.Entity.Contact;
import com.example.contactList.MSPV3;
import com.example.contactList.R;
import com.example.contactList.ViewModel;
import java.util.ArrayList;
import java.util.List;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class ViewAllContactFragment extends Fragment {
    private RecyclerView contactView;
    private ImageButton back;
    private Activity activity;
    private List<Contact> contactTables = new ArrayList<>();
    private MaterialDialog mDialog;
    private RecyclerView.ViewHolder viewHolder1;
    private Context context;
    private final AllContactsAdapter adapter = new AllContactsAdapter();
    private View view;
    private ViewModel mViewModel = App.viewModel;
    private TextView deleteAll;
    private MaterialDialog mDialogDeleteAll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //a callback method that the system call when its time for the UI to appear at the first time
        view = inflater.inflate(R.layout.fragment_view_all_contact, container, false);
        this.mViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        context = container.getContext();
        initVeiw();
        activity = getActivity();
        activity.setTitle("All Contacts");
        addContactToView();
        initMDialog(contactView, adapter);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDeleteAll.show();
            }
        });
        mDialog = new MaterialDialog.Builder(activity)
                .setTitle("Delete!")
                .setMessage("Are you sure you want to delete this contact?")
                .setCancelable(false)
                .setPositiveButton("Yes", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Log.e("g","delteing");
                        delete(dialogInterface);
                    }
                })
                .setNegativeButton("No", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        addContactToView();
                    }
                })
                .build();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewHolder1 = viewHolder;
                if (direction == ItemTouchHelper.RIGHT) {
                    mDialog.show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    EditExpense();
                }

            }


        }).attachToRecyclerView(contactView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentViewAllContact_to_fragment_main);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void EditExpense() {
        Contact c = contactTables.get(viewHolder1.getAdapterPosition());
        this.mViewModel.deleteContact(c.getPhone_number());
        viewHolder1.itemView.setVisibility(View.GONE);
        MSPV3.getMe().putString("editCon", "true");
        MSPV3.getMe().putObject("contact", c);
        Navigation.findNavController(view).navigate(R.id.action_fragmentViewAllContact_to_fragment_add_contact);
    }

    private void initMDialog(RecyclerView recyclerView, AllContactsAdapter adapter) {
        mDialogDeleteAll = new MaterialDialog.Builder(activity)
                .setTitle("Delete?")
                .setMessage("Are You Sure You Want To Delete All The Contacts?")
                .setCancelable(false)
                .setPositiveButton("Delete", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        mViewModel.deleteAllContact();
                        dialogInterface.dismiss();
                        Navigation.findNavController(view).navigate(R.id.action_fragmentViewAllContact_to_fragment_main);
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
    }


    private void delete(DialogInterface dialogInterface) {
        Contact c = contactTables.get(viewHolder1.getAdapterPosition());
        this.mViewModel.deleteContact(c.getPhone_number());
        dialogInterface.dismiss();
        addContactToView();
    }

    private void addContactToView() {
        contactView.setLayoutManager(new LinearLayoutManager(context));
        contactView.setAdapter(adapter);
        adapter.setOnClickListener(new AllContactsAdapter.OnItemClickListner() {
            public void onItemClick(String name) {

            }
        });
        mViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactTables = contacts;
                if (contactTables != null) {
                    adapter.setNotes(contacts);
                }
            }
        });
    }

    private void initVeiw() {
        contactView = view.findViewById(R.id.recycler_view2);
        back = view.findViewById(R.id.back_all);
        deleteAll = view.findViewById(R.id.deleteAllContacts);
    }
}


