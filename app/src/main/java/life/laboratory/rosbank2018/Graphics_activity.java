package life.laboratory.rosbank2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class Graphics_activity extends AppCompatActivity {
    private Retrofit retrofit;
    private Graph_interface graph_interface;
    ArrayList <Model.quatation> quotation = new ArrayList<>();
    GraphView graphView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_graph:
                    return true;
                case R.id.navigation_exit:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_activity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        graph_interface = retrofit.create(Graph_interface.class); //Создаем объект, при помощи которого будем выполнять запросы

        final LineGraphSeries <DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {});

        graphView = (GraphView) findViewById(R.id.graphView);
        Intent intent = getIntent();
        String id_from = intent.getStringExtra(Constants.ID_FROM);
        String id_to = intent.getStringExtra(Constants.ID_TO);
        String session = intent.getStringExtra(Constants.UUID);
        Log.d("ROSBANK2018", id_from + " " + id_to);
        Query_model.MyQuery temp = new Query_model.MyQuery();
        temp.setAction("graph");
        temp.setFrom(Integer.valueOf(id_from));
        temp.setQuant("second");
        temp.setTO(Integer.valueOf(id_to));
        temp.setSession(session);
        graph_interface.setQuery(temp).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
//                quotation.addAll(response.body().getQuatation());
                Model.quatation q = null;
                for (int i = 0; i < response.body().getQuatation().length; i++){
//                for (Model.quatation q : response.body().getQuatation()){
                    q = response.body().getQuatation()[i];
                    Log.d("ROSBANK2018", String.valueOf(Double.valueOf(i)) + " " + String.valueOf(q.getCountPurchase()));
                    series.appendData(new DataPoint(Double.valueOf(i), q.getCountPurchase()), true, 100);
                }

                graphView.addSeries(series);
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
            }
        });

    }

}
