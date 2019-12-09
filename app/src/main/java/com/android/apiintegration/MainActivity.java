package com.android.apiintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    JsonPlaceHolderApi iMyService;
    Retrofit retrofit;
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZGU3NmU3OTQ5OGM2ODNmNWM2ZGMwNzkiLCJpYXQiOjE1NzU4NzQ3OTZ9.e00XWinKbb-5Y7k0DYzibcYRerdvyS2czH5PvyT9KeA";
    String s ="";

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.textView1);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi  = retrofit.create(JsonPlaceHolderApi.class);
       // loginUser();
        getData();

    }


    public  void getData(){
        Call<JsonElement> calls = jsonPlaceHolderApi.getPosts(token);

        calls.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if(!response.isSuccessful()){
                    txt.setText("Code" + response.code());
                    return;
                }

                try {
                    JSONArray object = new JSONArray(response.body().toString());
                    for(int i= 0;i< object.length() ; i++){

                        JSONObject obj = object.getJSONObject(i);
                        String name = obj.getString("todo");
                        String isD = obj.getString("isDone");
                        String id = obj.getString("_id");

                        s = s + "\n" +"Name : "+name + "\n" +"IsDone : " + isD + "\n" +"Id : "+id +"\n\n\n";


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                txt.setText(s);



            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                txt.setText(t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void loginUser(){
        HashMap<String,String> SendData =new HashMap<>();
        SendData.put("email","test1@gmail.com");
        SendData.put("password","1234567890");


        final JsonPlaceHolderApi request= retrofit.create(JsonPlaceHolderApi.class);

        request.registerU(SendData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();

                    try {
                        JSONObject object = new JSONObject(response.body().toString());
                       token = object.getString("token");

                        txt.setText(""+ token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }



    }


