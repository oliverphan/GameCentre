package fall2018.csc2017.gamelauncher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.SectionsPageAdapter;

public class MainActivity extends AppCompatActivity implements SaveAndLoadFiles {

    /**
     * TAG for this container activity
     */
    private static final String TAG = "Main";

    /**
     * The name of the current User.
     */
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = loadCurrentUsername();
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        ViewPager mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(getIntent().getIntExtra("frgToLoad", 1));
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        addLogOutButtonListener();
        displayCurrentUser();
    }

    /**
     * Set up the slider for the three fragments.
     *
     * @param viewPager the viewPager to set up
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlidingFragment());
        adapter.addFragment(new FourFragment());
        adapter.addFragment(new MatchingFragment());
        viewPager.setAdapter(adapter);
    }

    /**
     * Display the Current user's username.
     */
    private void displayCurrentUser() {
        TextView displayName = findViewById(R.id.tv_CurrentUser);
        String temp = "Logged in as " + currentUser;
        displayName.setText(temp);
    }

    /**
     * Activate the log out button.
     */
    private void addLogOutButtonListener() {
        Button logOutButton = findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(v -> switchToLoginActivity());
    }

    /**
     * Switch to the Login view
     */
    private void switchToLoginActivity() {
        Intent tmp = new Intent(this, LoginActivity.class);
        createToast("Logged Out");
        startActivity(tmp);
        finish();
    }

    @Override
    public void onBackPressed() {
        createToast("To logout press LOGOUT");
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(
                this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Passes context of the activity to utility interface
     *
     * @return Context of current activity
     */
    public Context getActivity() {
        return this;
    }
}
