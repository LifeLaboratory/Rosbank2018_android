package life.laboratory.rosbank2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class PersonalActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private String UUID;
    private String STATUS_PACK;

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    selectedFragment = new PersonalFragment();
                    ((PersonalFragment) selectedFragment).setContext(PersonalActivity.this);
                    ((PersonalFragment) selectedFragment).setUUID(UUID);
                    ((PersonalFragment) selectedFragment).setSTATUS_PACK(STATUS_PACK);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
                } break;
                case R.id.navigation_graph: {
                    selectedFragment = new GraphFragment();
                    ((GraphFragment) selectedFragment).setUUID(UUID);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
                } break;
                case R.id.navigation_exit: {
                    Intent toPersonalArea = new Intent(PersonalActivity.this, MainActivity.class);
                    startActivity(toPersonalArea);
                } break;
            }
            return true;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        this.UUID = getIntent().getStringExtra(Constants.UUID);
        this.STATUS_PACK = getIntent().getStringExtra(Constants.STATUS_PACK);

        Log.d("ROSBANK2018", this.UUID);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment selectedFragment = new GraphFragment();
        ((GraphFragment) selectedFragment).setUUID(UUID);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
    }
}
