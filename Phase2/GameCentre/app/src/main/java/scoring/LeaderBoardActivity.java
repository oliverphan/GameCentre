package scoring;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.SaveAndLoad;
import fall2018.csc2017.R;
import gamelauncher.MainActivity;
import gamelauncher.SectionsPageAdapter;
import gamelauncher.SlidingTileActivity;
import users.User;

//TODO: Make 3 fragments, one for each game, and put in some of the code here for each one.

public class LeaderBoardActivity extends AppCompatActivity implements SaveAndLoad {
    /**
     * A HashMap of user accounts, by  name.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The name of the current user.
     */
    private User currentUser;


    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(getIntent().getIntExtra("frgToLoad", 0));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlidingTileLeaderBoardActivity());
        adapter.addFragment(new ConnectFourLeaderBoardActivity());
        adapter.addFragment(new MemoryLeaderBoardActivity());
        viewPager.setAdapter(adapter);
    }

    /**
     * Switch to the MainActivity view.
     */
    private void switchToTitleActivity() {
        Intent tmp = new Intent(this, MainActivity.class);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed(){
        switchToTitleActivity();
        finish();
    }

    public Context getActivity(){
        return this;
    }
}
