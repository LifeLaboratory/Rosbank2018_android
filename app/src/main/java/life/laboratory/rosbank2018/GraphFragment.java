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

import life.laboratory.rosbank2018.server.Quotations;
import life.laboratory.rosbank2018.server.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphFragment extends Fragment {

    private View view;
    private Server info;
    private String UUID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater ltInflater = getLayoutInflater();
        this.view = ltInflater.inflate(R.layout.graph, null, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.91.6.105:13452/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        info = retrofit.create(Server.class);
        info.getQuotations(UUID, "list").enqueue(new Callback<Quotations>() {
            @Override
            public void onResponse(Call<Quotations> call, Response<Quotations> response) {
                String[] forList = new String[response.body().getQuotation().length];
                for (Quotations.Quotation q : response.body().getQuotation()){
                    Log.d("ROSKBANK2018", q.getName());
                }
                for (int i = 0; i < response.body().getQuotation().length; i++) {
                    forList[i] = response.body().getQuotation()[i].getCountSale() + "#" + response.body().getQuotation()[i].getName()
                            + "#" + response.body().getQuotation()[i].getCountPurchare() + "#" + response.body().getQuotation()[i].getIdQuotationFrom() + "#" + response.body().getQuotation()[i].getIdQuotationFrom();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, forList);
                ((ListView) view.findViewById(R.id.quotations)).setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Quotations> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
