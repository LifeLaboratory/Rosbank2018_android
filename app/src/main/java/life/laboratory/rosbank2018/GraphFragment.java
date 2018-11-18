package life.laboratory.rosbank2018;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import life.laboratory.rosbank2018.server.Currency_adapter;
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
    private ListView listView;
    private String[] forList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LayoutInflater ltInflater = getLayoutInflater();
        this.view = ltInflater.inflate(R.layout.graph, null, false);

        listView = (ListView) view.findViewById(R.id.quotations);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toGraph = new Intent(getContext(), Graphics_activity.class);
                toGraph.putExtra(Constants.UUID, UUID);
                toGraph.putExtra(Constants.ID_FROM, forList[position].split("#")[3]);
                toGraph.putExtra(Constants.ID_TO, forList[position].split("#")[4]);
                toGraph.putExtra(Constants.TITLE_KURS, forList[position].split("#")[1]);
                startActivity(toGraph);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        info = retrofit.create(Server.class);
        info.getQuotations(UUID, "list").enqueue(new Callback<Quotations>() {
            @Override
            public void onResponse(Call<Quotations> call, Response<Quotations> response) {
                if (response.body().getQuotation() != null) {
                    forList = new String[response.body().getQuotation().length];
                    for (Quotations.Quotation q : response.body().getQuotation()) {
                        Log.d("ROSBANK2018", q.getName());
                    }
                    for (int i = 0; i < response.body().getQuotation().length; i++) {
                        forList[i] = response.body().getQuotation()[i].getCountSale() + "#" + response.body().getQuotation()[i].getName()
                                + "#" + response.body().getQuotation()[i].getCountPurchare() + "#" + response.body().getQuotation()[i].getIdQuotationFrom() + "#" + response.body().getQuotation()[i].getIdQuotationTo();
                    }

                    Context context = getContext();
                    if (context != null) {
                        Currency_adapter adapter = new Currency_adapter(context, forList);
                        listView.setAdapter(adapter);
                    }
                } else {
                    Log.d("ROSBANK2018", "Not found data for forList");
                }
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
