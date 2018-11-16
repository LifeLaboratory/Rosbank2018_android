package life.laboratory.rosbank2018;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import life.laboratory.rosbank2018.server.Server;
import life.laboratory.rosbank2018.server.Session;
import life.laboratory.rosbank2018.server.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Server login;
    private TextView loginView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        login = retrofit.create(Server.class); //Создаем объект, при помощи которого будем выполнять запросы

        loginView = (TextView) findViewById(R.id.login);
        passwordView = (TextView) findViewById(R.id.password);

        ((Button) findViewById(R.id.log_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.auth(new User(loginView.getText().toString(), passwordView.getText().toString())).enqueue(new Callback<Session>() {
                    @Override
                    public void onResponse(Call<Session> call, Response<Session> response) {
                        if (response.body() != null) {
                            Log.d("ROSBANK2018", "Session: " + response.body().getSession());
                            Intent toPersonalArea = new Intent(MainActivity.this, PersonalActivity.class);
                            toPersonalArea.putExtra(Constants.UUID, response.body().getSession());
                            startActivity(toPersonalArea);
                        } else {
                            Snackbar.make(((LinearLayout) findViewById(R.id.main_view)), "Ошибка авторизации", Snackbar.LENGTH_LONG)
                                    .setAction("Закрыть", null).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Session> call, Throwable t) {
                        Snackbar.make(((LinearLayout) findViewById(R.id.main_view)), "Ошибка авторизации: " + t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Закрыть", null).show();
                    }
                });
            }
        });
    }
}
