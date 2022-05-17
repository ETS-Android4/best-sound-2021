package com.example.appnhac.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PlayMusicAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> arrayFragment = new ArrayList<>();

    public PlayMusicAdapter(@NonNull @NotNull FragmentManager fm, int behavior)
    {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment)
    {
        arrayFragment.add(fragment);
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
