package com.example.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fragment.MainFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.OrderFragment;
import com.example.fragment.PublishFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment = new ArrayList<>();
    public MainAdapter(FragmentManager fm) {
        super(fm);
        mFragment.add(new MainFragment());
        mFragment.add(new PublishFragment());
        mFragment.add(new OrderFragment());
        mFragment.add(new MyFragment());
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragment.get(0);
                break;
            case 1:
                fragment = mFragment.get(1);
                break;
            case 2:
                fragment = mFragment.get(2);
                break;
            case 3:
                fragment = mFragment.get(3);
                break;
            case 4:
                fragment = mFragment.get(4);
                break;
            default:
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
