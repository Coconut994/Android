package com.example.finalassigment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.finalassigment.DownloadsFragment;
import com.example.finalassigment.RecentFragment;
import com.example.finalassigment.SubscribedFragment;
import com.example.finalassigment.UnlockedFragment;

public class MySeriesPagerAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 4; // Định nghĩa số lượng tab
    private final String[] tabTitles = {"Recent", "Subscribed", "Downloads", "Unlocked"}; // Tiêu đề tab

    public MySeriesPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RecentFragment();
            case 1:
                return new SubscribedFragment();
            case 2:
                return new DownloadsFragment();
            case 3:
                return new UnlockedFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position); // Ném ngoại lệ nếu position không hợp lệ
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT; // Sử dụng hằng số để dễ bảo trì
    }

    // Phương thức để lấy tiêu đề tab (dùng với TabLayout)
    public String getTabTitle(int position) {
        if (position >= 0 && position < tabTitles.length) {
            return tabTitles[position];
        }
        return "";
    }
}