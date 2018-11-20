package life.laboratory.rosbank2018;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import life.laboratory.rosbank2018.server.Currency_adapter;
import life.laboratory.rosbank2018.server.Person;
import life.laboratory.rosbank2018.server.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalFragment extends Fragment {

    private String UUID;
    private String STATUS_PACK;
    private View view;
    private Server info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater ltInflater = getLayoutInflater();
        this.view = ltInflater.inflate(R.layout.personal, null, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        info = retrofit.create(Server.class);

        (((Server) info).getUserInfo(this.UUID)).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                ((TextView) view.findViewById(R.id.message)).setText(response.body().getName());
                ((TextView) view.findViewById(R.id.user_status)).setText(STATUS_PACK);//???
//                if (response.body().getCurrency() != null) {
                    String[] forList = new String[response.body().getCurrency().length];
                    for (int i = 0; i < response.body().getCurrency().length; i++) {
                        forList[i] = response.body().getCurrency()[i].getIdCurrency() + "#" + response.body().getCurrency()[i].getNameCurrency() + "#" + String.valueOf(response.body().getCurrency()[i].getCost());
                    }
                    Currency_adapter adapter = new Currency_adapter(PersonalFragment.this.context, forList);
                    ((ListView) view.findViewById(R.id.user_money)).setAdapter(adapter);
//                }
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

    public String getSTATUS_PACK() {
        return STATUS_PACK;
    }

    public void setSTATUS_PACK(String STATUS_PACK) {
        this.STATUS_PACK = STATUS_PACK;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
}
