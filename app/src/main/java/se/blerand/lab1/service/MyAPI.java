package se.blerand.lab1.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import se.blerand.lab1.models.MyUser;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */

public interface MyAPI {

    @GET("users") Call<List<MyUser>> listUsers();

    @GET("users/{id}/match") Call<List<MyUser>> matching(@Path("id") String identity);

    @POST("users")
    Call<MyUser> createUser();


    @FormUrlEncoded
    @PUT("users/{id}")
    Call<Void> updateUser(@Path("id") String id,
            @Field("name") String name,
            @Field("info") String info,
            @Field("telno") String telno,
            @Field("active") boolean active);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String identity);


}
