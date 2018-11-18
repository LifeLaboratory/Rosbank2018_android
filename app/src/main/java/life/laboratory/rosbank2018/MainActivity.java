package life.laboratory.rosbank2018;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
    TextView loginView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        login = retrofit.create(Server.class);

        loginView = (TextView) findViewById(R.id.login);
        passwordView = (TextView) findViewById(R.id.password);

        ((Button) findViewById(R.id.log_button_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(loginView.getText().toString(), passwordView.getText().toString(), "client");
            }
        });

//        ((Button) findViewById(R.id.log_button_employee)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendRequest(loginView.getText().toString(), passwordView.getText().toString(), "employee");
//            }
//        });
    }

    private void sendRequest(String loginStr, String passwordStr, final String typeStr) {
        login.auth(new User(loginStr, passwordStr, typeStr)).enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.body() != null && !response.body().getSession().equals("")) {
                    Log.d("ROSBANK2018", "Session: " + response.body().getSession());
                    switch (typeStr) {
                        case "client": {
                            Intent toPersonalArea = new Intent(MainActivity.this, PersonalActivity.class);
                            toPersonalArea.putExtra(Constants.UUID, response.body().getSession());
                            toPersonalArea.putExtra(Constants.STATUS_PACK, response.body().getStatusPack());
                            startActivity(toPersonalArea);
                        } break;
                        case "employee": {
                            Snackbar.make(((LinearLayout) findViewById(R.id.main_view)), "Авторизация администратора", Snackbar.LENGTH_LONG)
                                    .setAction("Закрыть", null).show();
                        } break;
                    }
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


}