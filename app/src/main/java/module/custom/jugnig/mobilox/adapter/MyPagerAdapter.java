package module.custom.jugnig.mobilox.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import module.custom.jugnig.mobilox.view.SelectPreferenceFragment;
import module.custom.jugnig.mobilox.view.SelectShowFragment;


public class MyPagerAdapter extends FragmentStatePagerAdapter {
//    static final String[] tabs = new String[]{"Home", "Chats", "Notifications", "Search", "Profile"};
    private static final String TAG = MyPagerAdapter.class.getSimpleName();

    private final int count;
    private Bundle bundle;

    public MyPagerAdapter(FragmentManager fragmentManager, int count, Bundle bundle) {
        super(fragmentManager);
        this.count = count;
        this.bundle = bundle;
    }

    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new SelectPreferenceFragment();
                break;
            default:
                fragment = new SelectShowFragment();
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public int getCount() {
        return count;
    }

//    public CharSequence getPageTitle(int position) {
//        return tabs[position];
//    }
}
