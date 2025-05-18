package com.example.seriesranking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TrendingFragment();
            case 1: return new PopularFragment();
            case 2: return new DramaFragment();
            case 3: return new FantasyFragment();
            case 4: return new RomanceFragment();
            default: return new TrendingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
