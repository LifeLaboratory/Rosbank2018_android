package life.laboratory.rosbank2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private String UUID;

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

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        this.UUID = getIntent().getStringExtra(Constants.UUID);

        Log.d("ROSBANK2018", this.UUID);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment selectedFragment = new GraphFragment();
        ((GraphFragment) selectedFragment).setUUID(UUID);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();

//        selectedFragment.getView().setOnTouchListener(new OnSwipeTouchListener(PersonalActivity.this){
//            public void onSwipeTop() {
//                Toast.makeText(PersonalActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeRight() {
//                Toast.makeText(PersonalActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeLeft() {
//                Toast.makeText(PersonalActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeBottom() {
//                Toast.makeText(PersonalActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
