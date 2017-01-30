package se.blerand.lab1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.blerand.lab1.service.MyAPI;
import se.blerand.lab1.service.ServiceGenerator;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */
public class NextActivity extends AppCompatActivity{
    Context mContext;
    private TextView mName;
    private String tele;
    private String info;
    private String hobbie;

    private EditText mPhoneNumber;
    private EditText mInfo;

    private Switch toggle;
    private Boolean toggle_recorder;

    private Button mSave;
    private Button mDelete;

    private static final String TAG = "NextActivity";
    private static final String USER_NAME = "username_key";
    private static final String IDENTITY = "my_identity";
    private static final String TELE = "tele_key";
    private static final String INFO = "info_key";
    private static final String HOBBIE = "hobbie_key";
    private static final String MATCH = "match_key";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_next);
        final String user_name = UserData.getString(mContext,USER_NAME);
        mName = (TextView) findViewById(R.id.textView);
        mName.setText("Hello " + user_name + "!");


        //Strings
        tele = "";
        info = "";
        hobbie = "";


        toggle = (Switch) findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle_recorder = true;
                } else {
                    toggle_recorder = false;
                }
            }
        });

        //Edittext (tele)
        mPhoneNumber = (EditText) findViewById(R.id.editText);
        mInfo = (EditText) findViewById(R.id.editText2);

        //Buttons
        mSave = (Button) findViewById(R.id.b2);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tele_ver = mPhoneNumber.getText().toString();
                final String info_ver = mInfo.getText().toString();

                Log.d(TAG, "Starting GameActivity");
                if(tele_ver.length() == 0 ){
                    Toast.makeText(getApplicationContext(),
                            "Not a valid number. Try Again!", Toast.LENGTH_SHORT).show();
                }
                else if(info_ver.length() == 0 ){
                    Toast.makeText(getApplicationContext(),
                            "Please write about yourself", Toast.LENGTH_SHORT).show();
                }
                else if(hobbie.length()== 0){
                    Toast.makeText(getApplicationContext(),
                            "Please choose your hobbies", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserData.setString(mContext, TELE, tele_ver);
                    UserData.setString(mContext, HOBBIE, hobbie);
                    UserData.setString(mContext, INFO, info_ver); //+ ". Also: " + hobbie);
                    UserData.setBoo(mContext, MATCH, toggle_recorder);

                    MyAPI skapaUser = ServiceGenerator.createService(MyAPI.class);
                    Call<Void> secondCall = skapaUser.updateUser(
                                    UserData.getString(mContext,IDENTITY),
                                    UserData.getString(mContext, USER_NAME),
                                    UserData.getString(mContext, INFO),
                                    UserData.getString(mContext, TELE),
                                    UserData.getBoo(mContext, MATCH));
                    secondCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Intent i = new Intent(NextActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Error", t.getMessage());
                        }
                    });
                }
            }

        });
        mDelete = (Button) findViewById(R.id.b1);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Deleting...");
                MyAPI deleteUser = ServiceGenerator.createService(MyAPI.class);
                Call<Void> del = deleteUser.deleteUser(UserData.getString(mContext, IDENTITY));
                del.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                        UserData.deleteInfo(mContext);
                        Intent i = new Intent(NextActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        tele = UserData.getString(mContext, TELE);
        info = UserData.getString(mContext,INFO);
        hobbie = UserData.getString(mContext, HOBBIE);
        toggle_recorder = UserData.getBoo(mContext, MATCH);
        setupUI();

    }

    public void onCheckboxClicked(View v){
        CheckBox chCoding = (CheckBox) findViewById(R.id.c1);

        CheckBox chGym = (CheckBox) findViewById(R.id.c2);

        CheckBox chFootball = (CheckBox) findViewById(R.id.c3);

        StringBuilder sb = new StringBuilder();

        if(chCoding.isChecked()) {
            sb.append(", " + chCoding.getText());
        }
        if(chGym.isChecked()) {
            sb.append(", " + chGym.getText());
        }
        if(chFootball.isChecked()) {
            sb.append(", " + chFootball.getText());
        }
        if(sb.length() > 0) {
            hobbie = sb.deleteCharAt(sb.indexOf(",")).toString();
        }else {
            hobbie = "";
        }
    }


    public void setupUI() {
        ((EditText)findViewById(R.id.editText)).setText(tele);
        ((EditText)findViewById(R.id.editText2)).setText(info);

        CheckBox chCoding = (CheckBox)findViewById(R.id.c1);
        CheckBox chGym = (CheckBox)findViewById(R.id.c2);
        CheckBox chFootball = (CheckBox)findViewById(R.id.c3);

        chCoding.setChecked(false);
        chGym.setChecked(false);
        chFootball.setChecked(false);
        toggle.setChecked(false);

        if (hobbie.contains("Coding")) {
            chCoding.setChecked(true);
        }

        if (hobbie.contains("Gym")) {
            chGym.setChecked(true);
        }

        if (hobbie.contains("Football")) {
            chFootball.setChecked(true);
        }

        if (toggle_recorder) {
            toggle.setChecked(true);
        }else{
            toggle.setChecked(false);
        }

    }
}


