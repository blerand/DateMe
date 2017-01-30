package se.blerand.lab1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.blerand.lab1.models.MyUser;
import se.blerand.lab1.service.MyAPI;
import se.blerand.lab1.service.ServiceGenerator;

/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */

public class UserListViewAdapter extends ArrayAdapter<MyUser> {
    private Context mContext;
    private List<MyUser> mUsersList;
    private UserListViewAdapter adapter;

    public UserListViewAdapter(Context context, int res, List<MyUser> object) {
        super(context,res,object);
        this.mContext = context;
        this.mUsersList = object;
        this.adapter = this;
    }
    //@Override
    public View getView(int position, View counterView, ViewGroup parent){
        //return super.getView(position,counterView,parent);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.row_style, parent,false);
        final MyUser user = mUsersList.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(user.getName());
        TextView subtext = (TextView) view.findViewById(R.id.subtext);
        subtext.setText(user.getInfo());
        ImageButton img2 = (ImageButton) view.findViewById(R.id.img2);
        ImageView imgw = (ImageView) view.findViewById(R.id.icon);

        if(user.isActive()){
            imgw.setImageResource(R.drawable.heart);
        }else{
            imgw.setImageResource(R.drawable.broken);
        }
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAPI deleteUser = ServiceGenerator.createService(MyAPI.class);
                Call<Void> call = deleteUser.deleteUser(user.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(mContext, "User deleted", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
                mUsersList.remove(user);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
