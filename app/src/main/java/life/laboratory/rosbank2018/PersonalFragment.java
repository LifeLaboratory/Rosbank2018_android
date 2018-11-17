package life.laboratory.rosbank2018;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import life.laboratory.rosbank2018.server.Person;
import life.laboratory.rosbank2018.server.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalFragment extends Fragment {

    private String UUID;
    private View view;
    private Server info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater ltInflater = getLayoutInflater();
        this.view = ltInflater.inflate(R.layout.personal, null, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.91.6.105:13452/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        info = retrofit.create(Server.class);

        (((Server) info).getUserInfo(this.UUID)).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                ((TextView) view.findViewById(R.id.message)).setText(response.body().getName());
                String[] forList = new String[response.body().getCurrency().length];
                for (int i = 0; i < response.body().getCurrency().length; i++) {
                    forList[i] = response.body().getCurrency()[i].getIdCurrency() + "#" + response.body().getCurrency()[i].getNameCurrency() + "#" + response.body().getCurrency()[i].getCost();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, forList);
                ((ListView) view.findViewById(R.id.user_money)).setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("ROSBANK2018", t.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.view;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
