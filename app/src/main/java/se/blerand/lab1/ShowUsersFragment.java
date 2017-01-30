package se.blerand.lab1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.blerand.lab1.models.MyUser;
import se.blerand.lab1.service.MyAPI;
import se.blerand.lab1.service.ServiceGenerator;


/**
 * Created by Blerand Bahtiri on 2016-11-18.
 */
public class ShowUsersFragment extends Fragment {
    public static final String TAG = "SHOW USERS FRAGMENT";

    @BindView(R.id.lv)
    ListView lv;

    List<MyUser> lu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_users, container, false);

        ButterKnife.bind(this,view);

        MyAPI userClient = ServiceGenerator.createService(MyAPI.class);
        Call<List<MyUser>> call = userClient.listUsers();
        call.enqueue(new Callback<List<MyUser>>() {
            @Override
            public void onResponse(Call<List<MyUser>> call, Response<List<MyUser>> response) {
                lu = response.body();
                UserListViewAdapter adapt = new UserListViewAdapter(getActivity(),
                        R.layout.row_style, lu);
                lv.setAdapter(adapt);
            }

            @Override
            public void onFailure(Call<List<MyUser>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
