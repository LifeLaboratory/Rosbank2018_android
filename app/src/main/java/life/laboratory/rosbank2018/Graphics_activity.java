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
                .baseUrl("http://10.91.6.105:13452/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        graph_interface = retrofit.create(Graph_interface.class); //Создаем объект, при помощи которого будем выполнять запросы

        LineGraphSeries <DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        GraphView graphView;// = new LineGraphView(this, "График каких-то данных");
        graphView = (GraphView) findViewById(R.id.graphView);
        graphView.addSeries(series);
        //Intent intent = getIntent();
        //Integer id_from = intent.getIntExtra(id_quatation_from);
        //Integer id_to = intent.getIntExtra(id_quatation_to);
        Query_model.MyQuery temp = new Query_model.MyQuery();
        temp.setAction("graph");
        temp.setFrom(1);
        temp.setQuant("second");
        temp.setTO(2);
        temp.setSession("863af63e-c005-d8a7-0bc3-2cfdc5b59d52");
        graph_interface.setQuery(temp).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                for(Model.quatation temp1 : response.body().getQuatation()){
                    quotation.add(temp1);
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
            }
        });

    }

}
