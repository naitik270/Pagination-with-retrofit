package com.example.paggintaionapp.ApiClasses;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.paggintaionapp.Classes.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private Context context;
    private AppExecutor mAppExecutor;
    View view;

    public Repository(Context context) {
        this.mAppExecutor = new AppExecutor();
        this.context = context;
        this.view = view;
    }

    // Common call post API method.
    public MutableLiveData<Example> callPostAPI(int page,int size) {
        final MutableLiveData<Example> mutableLiveData = new MutableLiveData<>();

        APICallsInterface myInterface = ApiClient.getRetrofitInstanceGET().create(APICallsInterface.class);
        Call<Example> call = myInterface.getPassenger(page,size);
        Log.e("--login--", "call: " + call.request().url());

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.body() != null && response.code() == 200) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                try {
                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mutableLiveData;
    }


}
