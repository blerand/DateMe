package se.blerand.lab1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.blerand.lab1.models.MyUser;
import se.blerand.lab1.service.MyAPI;
import se.blerand.lab1.service.ServiceGenerator;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static String STORAGE_NAME = "User_SharedPref";
    private static final String USER_NAME = "username_key";
    private static final String IDENTITY = "my_identity";
    private static final String TELE = "tele_key";
    private static final String INFO = "info_key";
    private static final String MATCH = "match_key";
    Context mContext;
    private Button mButton;
    private Button mButton3;
    private ImageButton love;

    private FrameLayout fragge;
    private TextView fra_text;
    private Button fra_button;

    private TextView t1;
    private EditText mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //FRAMELAYOUT
        fragge = (FrameLayout)findViewById(R.id.fragge);
        fragge.setVisibility(View.GONE);
        fra_text = (TextView) findViewById(R.id.fra_text);
        fra_button = (Button) findViewById(R.id.fra_button);
        fra_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragge.setVisibility(View.GONE);
            }
        });


        t1 = (TextView) findViewById(R.id.text1);
        mUser = (EditText) findViewById(R.id.editText);
        if(UserData.checkInfo(mContext,USER_NAME)) {
            t1.setText(UserData.getString(mContext, USER_NAME));
        }else{
            t1.setText("Whats your name?");
            mUser.setHint("NAME HERE PLEASE");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");

        mUser = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String anvandare = mUser.getText().toString();
                if((!UserData.checkInfo(mContext,USER_NAME) && (anvandare.length() == 0))){
                    mUser.requestFocus();
                    mUser.setError("Please write your name");
                }
                else if (anvandare.length() == 0) {
                    Log.d(TAG, "Starting Next");
                    Intent i = new Intent(MainActivity.this, NextActivity.class);
                    startActivity(i);
                }
                else if(anvandare.length() > 15){
                    mUser.requestFocus();
                    mUser.setError("Too long, please try again");
                }
                else {
                    if(!UserData.checkInfo(mContext,USER_NAME)) {
                        UserData.setString(mContext, USER_NAME, anvandare);
                        final MyAPI skapaUser = ServiceGenerator.createService(MyAPI.class);
                        Call<MyUser> myCall = skapaUser.createUser();
                        myCall.enqueue(new Callback<MyUser>() {
                            @Override
                            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                                UserData.setString(mContext, IDENTITY, response.body().getId());
                                Call<Void> secondCall = skapaUser.updateUser(
                                        UserData.getString(mContext, IDENTITY),
                                        UserData.getString(mContext, USER_NAME),
                                        UserData.getString(mContext, INFO),
                                        UserData.getString(mContext, TELE),
                                        UserData.getBoo(mContext, MATCH));

                                secondCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call,
                                                           Response<Void> response) {
                                        Log.d("USER UPDATED", ", Working!");
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("Error", t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<MyUser> call, Throwable t) {
                                Log.d("Error", t.getMessage());
                            }
                        });
                        Intent i = new Intent(MainActivity.this, NextActivity.class);
                        startActivity(i);
                    }else{
                        UserData.setString(mContext, USER_NAME, anvandare);
                        MyAPI skapaUser = ServiceGenerator.createService(MyAPI.class);
                        Call<Void> call = skapaUser.updateUser(
                                UserData.getString(mContext, IDENTITY),
                                UserData.getString(mContext, USER_NAME),
                                UserData.getString(mContext, INFO),
                                UserData.getString(mContext, TELE),
                                UserData.getBoo(mContext, MATCH));
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call,
                                                   Response<Void> response) {
                                Log.d("USER UPDATED", ", Working!");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Error", t.getMessage());
                            }
                        });
                        Intent i = new Intent(MainActivity.this, NextActivity.class);
                        startActivity(i);
                    }
                }
            }
        });

        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Showing fragment");
                Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(i);

            }
        });

        love = (ImageButton) findViewById(R.id.button4);
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Matching");
                if(!UserData.checkInfo(mContext,IDENTITY)){
                    mUser.requestFocus();
                    mUser.setError("Please register yourself");
                }else {
                    MyAPI match = ServiceGenerator.createService(MyAPI.class);
                    Call<List<MyUser>> call = match.matching(UserData.getString(mContext,IDENTITY));
                    call.enqueue(new Callback<List<MyUser>>() {
                        @Override
                        public void onResponse(Call<List<MyUser>> call, Response<List<MyUser>> response) {
                            Random rand = new Random();
                            fra_text.setText(response.body().get(rand.nextInt(
                                    response.body().size())+1).getName());
                        }

                        @Override
                        public void onFailure(Call<List<MyUser>> call, Throwable t) {
                            Log.d("Error", t.getMessage());
                        }
                    });
                    fragge.setVisibility(View.VISIBLE);
                }
            }
        });

    }


}

