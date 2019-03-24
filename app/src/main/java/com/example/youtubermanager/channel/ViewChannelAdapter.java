package com.example.youtubermanager.channel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewChannelAdapter extends FragmentPagerAdapter {

    List<Fragment> list = new ArrayList<>();

    ViewChannelAdapter(FragmentManager fm) {
        super(fm);
    }

    void removeItem(int postion){
        list.remove(postion);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    void addFragments(Fragment f){
        list.add(f);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=list.get(position).toString();
        return title;
    }
}
