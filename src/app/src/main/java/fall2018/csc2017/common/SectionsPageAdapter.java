package fall2018.csc2017.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {
    /**
     * List of fragments in this page adapter
     */
    private final List<Fragment> mFragmentList = new ArrayList<>();

    /**
     * Creates Page adapter to handle fragments
     * @param fm Fragment manager needed for fragments
     */
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Method to add fragment to this page adapter
     * @param fragment the fragment to be added
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}
