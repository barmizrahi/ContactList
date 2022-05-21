package com.example.contactList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GenderAPI {
    String BASE_URL = "https://api.genderize.io/";

    @Headers("Content-type: application/json")
    @GET("?")
    Call<Gender> getGander(@Query("name") String name);
}
