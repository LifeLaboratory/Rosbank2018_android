package life.laboratory.rosbank2018;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Graphics_activity extends AppCompatActivity{
    public Dialog dia;

    public String id_from;
    public String id_to;
    public String session;
    private Retrofit retrofit;
    private Graph_interface graph_interface;
    private Buying_interface buying_interface;
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

    //Переход в окно оплаты покупки
    private View.OnClickListener buyListener = new View.OnClickListener() {
        public void onClick(View v) {
            final TextView count = (TextView) findViewById(R.id.count);
            final Buying.Buy_class toSend = new Buying.Buy_class();
            toSend.setFrom(Integer.valueOf(id_from));
            toSend.setTo(Integer.valueOf(id_to));
            toSend.setSession(session);
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_IP) //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build();
            buying_interface = retrofit.create(Buying_interface.class); //Создаем объект, при помощи которого будем выполнять за// просы
            AlertDialog.Builder builder = new AlertDialog.Builder(Graphics_activity.this);
            dia = builder.create();
            LayoutInflater inflater = Graphics_activity.this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.buy_dialog, null))
                    .setPositiveButton("Купить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            toSend.setAction("sales");
                            toSend.setCount_send(Integer.valueOf(count.getText().toString()));
                            buying_interface.setBuying(toSend).enqueue(new Callback<Buying>() {
                                @Override
                                public void onResponse(Call<Buying> call, Response<Buying> response) {
                                    if(response.body().getStatus().equals(200)){
                                        Toast.makeText(getApplicationContext(),"Покупка совершена",LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Покупка не совершена",LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Buying> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Произошла ошибка соединения с сервером",LENGTH_LONG).show();
                                    dia.cancel();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Продать", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            toSend.setAction("purchase");
                            toSend.setCount_send(Integer.valueOf(count.getText().toString()));
                            //меняем местами для продажи from и to
                            Integer temp = toSend.getTo();
                            toSend.setTo(toSend.getFrom());
                            toSend.setFrom(temp);
                            buying_interface.setBuying(toSend).enqueue(new Callback<Buying>() {
                                @Override
                                public void onResponse(Call<Buying> call, Response<Buying> response) {
                                    if(response.body().getStatus().equals(200)){
                                        Toast.makeText(getApplicationContext(),"Продажа совершена",LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Продажа не совершена",LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Buying> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Произошла ошибка соединения с сервером",LENGTH_LONG).show();
                                    dia.cancel();
                                }
                            });
                        }
                    });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_activity);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        graph_interface = retrofit.create(Graph_interface.class); //Создаем объект, при помощи которого будем выполнять запросы

        final LineGraphSeries <DataPoint> series_one = new LineGraphSeries<DataPoint>(new DataPoint[] {});
        final LineGraphSeries <DataPoint> series_two = new LineGraphSeries<DataPoint>(new DataPoint[] {});

        graphView = (GraphView) findViewById(R.id.graphView);
        graphView.getViewport().setYAxisBoundsManual(true);
        Intent intent = getIntent();
        id_from = intent.getStringExtra(Constants.ID_FROM);
        id_to = intent.getStringExtra(Constants.ID_TO);
        session = intent.getStringExtra(Constants.UUID);
        Log.d("ROSBANK2018", id_from + " " + id_to);
        String id_from = intent.getStringExtra(Constants.ID_FROM);
        String id_to = intent.getStringExtra(Constants.ID_TO);
        session = intent.getStringExtra(Constants.UUID);

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
        graph_interface.setQuery(temp).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model.quatation q = null;
                Model.quatation[] arr = response.body().getQuatation();
                for (int i = 0; i < arr.length; i++) {
                    q = arr[i];
                    Log.d("ROSBANK2018", String.valueOf(Double.valueOf(i)) + " " + String.valueOf(q.getCountPurchase()));
                    Log.d("ROSBANK2018", String.valueOf(Double.valueOf(i)) + " " + String.valueOf(q.getCountSale()));
                    series_one.appendData(new DataPoint((double) i, q.getCountPurchase()), true, );
                    series_two.appendData(new DataPoint((double) i, q.getCountSale()), true, 500);
                }
                series_one.setColor(Color.BLACK);
                series_two.setColor(Color.RED);
                graphView.addSeries(series_one);
                graphView.addSeries(series_two);
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Нет соединения с сервером",LENGTH_LONG).show();
            }
        });
        Button buy = (Button) findViewById(R.id.buy_button);
        buy.setOnClickListener(buyListener);

    }

}
