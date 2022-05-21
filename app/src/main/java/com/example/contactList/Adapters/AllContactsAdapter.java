package com.example.contactList.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactList.Entity.Contact;
import com.example.contactList.GenderAPI;
import com.example.contactList.R;
import com.example.contactList.Gender;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactHolder> {
    private List<Contact> contacts = new ArrayList<>();
    OnItemClickListner listner;

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.phoneNumber.setText(contact.getPhone_number());
        holder.firstName.setText(contact.getFirst_name() + " " + contact.getLast_name());
        // holder.lastName.setText(contact.getLast_name());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GenderAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GenderAPI genderAPI = retrofit.create(GenderAPI.class);
        Call<Gender> gender = genderAPI.getGander(contact.getFirst_name());
        gender.enqueue(new Callback<Gender>() {
            @Override
            public void onResponse(Call<Gender> call, Response<Gender> response) {
                Gender g = response.body();
                String name = g.getName();
                String gender = g.getGender();
                float f = g.getProbability();
                int t = g.getCount();
                if (gender == null) {
                    return;
                }
                if (gender.equals("male")) {
                    holder.sexImage.setImageResource(R.drawable.male_img);
                } else {
                    holder.sexImage.setImageResource(R.drawable.female_img);
                }
            }

            @Override
            public void onFailure(Call<Gender> call, Throwable t) {
                Log.e("error", "failed");
            }
        });
    }


    public void setNotes(List<Contact> expenses) {
        this.contacts = expenses;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView phoneNumber;
        private TextView firstName;
        private TextView lastName;
        private ImageView sexImage;

        public ContactHolder(View view) {
            super(view);
            phoneNumber = view.findViewById(R.id.phone_number);
            firstName = view.findViewById(R.id.con_first_name);
            //lastName = view.findViewById(R.id.con_last_name);
            sexImage = view.findViewById(R.id.sexImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION)
                        listner.onItemClick(contacts.get(position).getPhone_number());
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(String phone);
    }

    public void setOnClickListener(AllContactsAdapter.OnItemClickListner onClickListener) {
        this.listner = onClickListener;
    }

}
