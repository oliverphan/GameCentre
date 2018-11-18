package gamelauncher;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private ViewPager mViewPager;
    private String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentName = getIntent().getStringExtra("currentName");
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        addLogOutButtonListener();
        displayCurrentUser();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlidingTileActivity());
        adapter.addFragment(new ConnectFourActivity());
        adapter.addFragment(new MatchingActivity());
        viewPager.setAdapter(adapter);
    }

    /**
     * Activate the log out button.
     */
    private void addLogOutButtonListener() {
        Button logOutButton = findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentreLoginActivity();
            }
        });
    }

    /**
     * Switch to the Login view
     */
    private void switchToGameCentreLoginActivity() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
        finish();
    }

    /**
     * Display the Current user's username.
     */
    private void displayCurrentUser() {
        TextView displayName = findViewById(R.id.tv_CurrentUser);
        String temp = "Logged in as " + currentName;
        displayName.setText(temp);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "To logout press LOGOUT", Toast.LENGTH_SHORT).show();

    }

}
