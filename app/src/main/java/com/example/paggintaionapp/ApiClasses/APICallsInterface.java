package com.example.paggintaionapp.ApiClasses;


import com.example.paggintaionapp.Classes.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICallsInterface {

    @GET("v1/passenger")
    Call<Example> getPassenger(@Query("page") int page,
                               @Query("size") int size);


    /*https://api.instantwebtools.net/v1/passenger?page=0&size=10*/

}
