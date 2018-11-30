package fall2018.csc2017.scoring;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.SectionsPageAdapter;
import fall2018.csc2017.gamelauncher.MainActivity;

public class LeaderBoardActivity extends AppCompatActivity implements SaveAndLoadFiles {

    private ViewPager mViewPager;


    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(getIntent().getIntExtra("frgToLoad", 0));
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlidingTileLeaderBoardFragment());
        adapter.addFragment(new FourLeaderBoardFragment());
        adapter.addFragment(new MatchingLeaderBoardFragment());
        viewPager.setAdapter(adapter);
    }

    /**
     * Switch to the MainActivity view.
     */
    private void switchToTitleActivity(int page) {
        Intent tmp = new Intent(this, MainActivity.class);
        tmp.putExtra("frgToLoad", page);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToTitleActivity(mViewPager.getCurrentItem());
        finish();
    }

    public Context getActivity() {
        return this;
    }
}
