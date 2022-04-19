package com.example.appnhac.Adapter;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter
{

    private ArrayList<Fragment> arrayFragment = new ArrayList<>();
    private ArrayList<String> arrayTitle = new ArrayList<>();

    public MainViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior)
    {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment,String title)
    {
        arrayFragment.add(fragment);
        arrayTitle.add(title);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return arrayTitle.get(position);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position)
    {
        return arrayFragment.get(position);
    }

    @Override
    public int getCount()
    {
        return arrayFragment.size();
    }
}
