package life.laboratory.rosbank2018;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Graphics_activity extends AppCompatActivity {
    private Retrofit retrofit;
    private Graph_interface graph_interface;
    ArrayList <Model.quatation> quotation = new ArrayList<>();
    GraphView graphView;
    String session, titleForGraph;
    LineGraphSeries <DataPoint> series_one, series_two;
    DataPoint[] oneData, twoData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_activity);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        graph_interface = retrofit.create(Graph_interface.class); //Создаем объект, при помощи которого будем выполнять запросы

//        series_one = new LineGraphSeries<DataPoint>(new DataPoint[] {});
//        series_two = new LineGraphSeries<DataPoint>(new DataPoint[] {});

        graphView = (GraphView) findViewById(R.id.graphView);
        Intent intent = getIntent();
        String id_from = intent.getStringExtra(Constants.ID_FROM);
        String id_to = intent.getStringExtra(Constants.ID_TO);
        session = intent.getStringExtra(Constants.UUID);
        titleForGraph = intent.getStringExtra(Constants.TITLE_KURS);

        ((TextView) findViewById(R.id.kurs_title)).setText(titleForGraph);

        ((Button) findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPersonal = new Intent(Graphics_activity.this, PersonalActivity.class);
                toPersonal.putExtra(Constants.UUID, session);
                startActivity(toPersonal);
            }
        });

        Query_model.MyQuery temp = new Query_model.MyQuery();
        temp.setAction("graph");
        temp.setFrom(Integer.valueOf(id_to));
        temp.setQuant("second");
        temp.setTO(Integer.valueOf(id_from));
        temp.setSession(session);
        graph_interface.setQuery(temp).subscribeOn(Schedulers.newThread())
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(5, TimeUnit.SECONDS)))
                .retryWhen(a -> a.flatMap(n -> Observable.timer(5, TimeUnit.SECONDS)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            Model.quatation q = null;
                            Model.quatation[] arr = response.getQuatation();
                            oneData = new DataPoint[arr.length];
                            twoData = new DataPoint[arr.length];
                            for (int i = 1; i <= arr.length; i++) {
                                q = arr[arr.length - i];
                                oneData[i - 1] = new DataPoint(i, q.getCountPurchase());
                                twoData[i - 1] = new DataPoint(i, q.getCountSale());
                            }
                            series_one = new LineGraphSeries<DataPoint>(oneData);
                            series_two = new LineGraphSeries<DataPoint>(twoData);
                            series_one.setColor(Color.BLACK);
                            series_two.setColor(Color.RED);
                            graphView.removeAllSeries();
                            graphView.addSeries(series_one);
                            graphView.addSeries(series_two);
                        }, e -> {
                            Log.d("ROSBANK2018", e.getMessage());
                        }
                );

//        graph_interface.setQuery(temp).enqueue(new Callback<Model>() {
//            @Override
//            public void onResponse(Call<Model> call, Response<Model> response) {
//                Model.quatation q = null;
//                Model.quatation[] arr = response.body().getQuatation();
//                oneData = new DataPoint[arr.length];
//                twoData = new DataPoint[arr.length];
//                for (int i = 1; i <= arr.length; i++) {
//                    q = arr[arr.length - i];
////                    Log.d("ROSBANK2018", String.valueOf(Double.valueOf(i)) + " " + String.valueOf(q.getCountPurchase()));
////                    Log.d("ROSBANK2018", String.valueOf(Double.valueOf(i)) + " " + String.valueOf(q.getCountSale()));
//                    oneData[i-1] = new DataPoint(i, q.getCountPurchase());
//                    twoData[i-1] = new DataPoint(i, q.getCountSale());
//                }
//                series_one = new LineGraphSeries<DataPoint>(oneData);
//                series_two = new LineGraphSeries<DataPoint>(twoData);
//                series_one.setColor(Color.BLACK);
//                series_two.setColor(Color.RED);
//                graphView.addSeries(series_one);
//                graphView.addSeries(series_two);
//            }
//            @Override
//            public void onFailure(Call<Model> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
//            }
//        });

    }

}
