package life.laboratory.rosbank2018;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;


public class Graphics_activity extends AppCompatActivity{
    public Dialog dia;
    public Double fixPriceBuy;
    public Double fixPriceSell;
    public String id_from;
    public String id_to;
    public String session;
    private Retrofit retrofit;
    private Graph_interface graph_interface;
    private Buying_interface buying_interface;
    ArrayList <Model.quatation> quotation = new ArrayList<>();
    GraphView graphView;
    boolean touchGraph = false;
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
            fixPriceBuy = Double.valueOf(((TextView)findViewById(R.id.moment_price_buy)).getText().toString().split(" ")[0]);
            fixPriceSell = Double.valueOf(((TextView)findViewById(R.id.moment_price_sell)).getText().toString().split(" ")[0]);
            Log.e("ROSBANK2018", ((TextView)findViewById(R.id.moment_price_sell)).getText().toString());
            final EditText count = (EditText) findViewById(R.id.count);

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
            View view = inflater.inflate(R.layout.buy_dialog, null);
            TextView title = (TextView) view.findViewById(R.id.dialog_title);
            title.setText("Покупка");
            builder.setView(view)
                    .setPositiveButton("Купить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            toSend.setAction("sales");
                            EditText count = view.findViewById(R.id.count);
                            toSend.setCount_send(Integer.valueOf(count.getText().toString()));
                            toSend.setCost_user(fixPriceBuy);
                            buying_interface.setBuying(toSend).enqueue(new Callback<Buying>() {
                                @Override
                                public void onResponse(Call<Buying> call, Response<Buying> response) {
                                    Log.d("ROSBANK2018", String.valueOf(response.body().getStatus()));
                                    if(response.body().getStatus()==200){
                                        Toast.makeText(getApplicationContext(),"Покупка совершена",LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Покупка не совершена",Toast.LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Buying> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Произошла ошибка соединения с сервером",Toast.LENGTH_LONG).show();
                                    dia.cancel();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dia.cancel();
                        }
                    });
            builder.show();
        }
    };

    private View.OnClickListener saleListener = new View.OnClickListener() {
        public void onClick(View v) {
            fixPriceBuy = Double.valueOf(((TextView)findViewById(R.id.moment_price_buy)).getText().toString().split(" ")[0]);
            fixPriceSell = Double.valueOf(((TextView)findViewById(R.id.moment_price_sell)).getText().toString().split(" ")[0]);
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
            View view = inflater.inflate(R.layout.buy_dialog, null);
            TextView title = (TextView) view.findViewById(R.id.dialog_title);
            title.setText("Продажа");
            builder.setView(view)
                    .setPositiveButton("Продать", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText count = view.findViewById(R.id.count);
                            toSend.setAction("purchase");
                            toSend.setCount_send(Integer.valueOf(count.getText().toString()));
                            //меняем местами для продажи from и to
                            Integer temp = toSend.getTo();
                            toSend.setTo(Integer.valueOf(id_from));
                            toSend.setFrom(Integer.valueOf(id_to));
                            toSend.setCost_user(fixPriceSell);
                            buying_interface.setBuying(toSend).enqueue(new Callback<Buying>() {
                                @Override
                                public void onResponse(Call<Buying> call, Response<Buying> response) {
                                    if(response.body().getStatus().equals(200)){
                                        Toast.makeText(getApplicationContext(),"Продажа совершена",Toast.LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                    else{
                                        Log.e("ROSBANK2018", String.valueOf(response.body().getStatus()));
                                        Toast.makeText(getApplicationContext(),"Продажа не совершена",Toast.LENGTH_LONG).show();
                                        dia.cancel();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Buying> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Произошла ошибка соединения с сервером",Toast.LENGTH_LONG).show();
                                    dia.cancel();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dia.cancel();

                        }
                    });
            builder.show();
        }
    };

    String titleForGraph;
    LineGraphSeries <DataPoint> series_one, series_two;
    DataPoint[] oneData, twoData;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_activity);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        graph_interface = retrofit.create(Graph_interface.class); //Создаем объект, при помощи которого будем выполнять запросы

        graphView = (GraphView) findViewById(R.id.graphView);
        Intent intent = getIntent();
        id_from = intent.getStringExtra(Constants.ID_FROM);
        id_to = intent.getStringExtra(Constants.ID_TO);
        session = intent.getStringExtra(Constants.UUID);
        Log.d("ROSBANK2018", id_from + " " + id_to);
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
        ((Button) findViewById(R.id.buy_btn)).setOnClickListener(buyListener);
        ((Button) findViewById(R.id.sell_btn)).setOnClickListener(saleListener);

        graphView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchGraph = true;
                return Graphics_activity.super.onTouchEvent(event);
            }
        });
        graphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchGraph = true;
            }
        });
        String patternFrom = "yyyy-MM-dd HH:mm:ss.SSSSSS";
        SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(patternFrom);
        String patternTo = "HH:mm";
        SimpleDateFormat simpleDateFormatTo = new SimpleDateFormat(patternTo);
        graph_interface.setQuery(temp).subscribeOn(Schedulers.newThread())
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS)))
                .retryWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            Model.quatation q = null;
                            Model.quatation[] arr = response.getQuatation();
                            oneData = new DataPoint[arr.length];
                            twoData = new DataPoint[arr.length];
                            Double min = 9999999.0, max = 0.0;
                            for (int i = 1; i <= arr.length; i++) {
                                q = arr[arr.length - i];
                                Date date = simpleDateFormatFrom.parse(q.getQuant());
//                                Log.d("ROSBANK2018", date.toString());
//                                Log.d("ROSBANK2018", q.getQuant());
                                oneData[i - 1] = new DataPoint(date, q.getCountPurchase());
                                twoData[i - 1] = new DataPoint(date, q.getCountSale());
//                                Log.d("ROSBANK2018", String.valueOf(q.getCountPurchase()) + " " + String.valueOf(q.getCountSale()));
                                if (q.getCountPurchase() < min) {
                                    min = q.getCountPurchase();
                                }
                                if (q.getCountSale() < min) {
                                    min = q.getCountSale();
                                }
                                if (q.getCountPurchase() > max) {
                                    max = q.getCountPurchase();
                                }
                                if (q.getCountSale() > max) {
                                    max = q.getCountSale();
                                }
                            }

                            ((TextView) findViewById(R.id.moment_price_sell)).setText(String.valueOf(arr[0].getCountSale()) + " " + titleForGraph.split("/")[1]);
                            ((TextView) findViewById(R.id.moment_price_buy)).setText(String.valueOf(arr[0].getCountPurchase()) + " " + titleForGraph.split("/")[1]);

                            series_one = new LineGraphSeries<DataPoint>(oneData);
                            series_two = new LineGraphSeries<DataPoint>(twoData);
                            series_one.setColor(Color.BLACK);
                            series_two.setColor(Color.RED);
//                            if (!touchGraph) {
                                graphView.removeAllSeries();
                                graphView.addSeries(series_one);
                                graphView.addSeries(series_two);
//                            }
                            Log.d("ROSBANK2018", String.valueOf(max) + " " + String.valueOf(min));
                            graphView.getViewport().setMaxY(max);
                            graphView.getViewport().setMinY(min);
                            graphView.getViewport().setScalable(true);
                            graphView.getViewport().scrollToEnd();
                            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                                @Override
                                public String formatLabel(double value, boolean isValueX) {
                                    if (isValueX) {
                                        return simpleDateFormatTo.format(new Date((long) value));
                                    } else {
                                        return super.formatLabel(value, false);
                                    }
                                }
                            });
                            graphView.getGridLabelRenderer().setNumHorizontalLabels(6);
                        }, e -> {
                            Log.d("ROSBANK2018", e.getMessage());
                        }
                );
    }

}
