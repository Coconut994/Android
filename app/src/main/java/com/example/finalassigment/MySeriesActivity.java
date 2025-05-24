package com.example.finalassigment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MySeriesActivity extends AppCompatActivity {
    private MySeriesPagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private ImageButton btnDeleteSelected;
    private ImageView btnBackMySeries;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_series);
        btnBackMySeries = findViewById(R.id.btnBackMySeries);
        btnBackMySeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Khởi tạo ViewPager2 và TabLayout
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        pagerAdapter = new MySeriesPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Recent");
                    break;
                case 1:
                    tab.setText("Subscribed");
                    break;
                case 2:
                    tab.setText("Download");
                    break;
                case 3:
                    tab.setText("Unlocked");
                    break;
            }
        }).attach();

        // Khởi tạo nút xóa
        btnDeleteSelected = findViewById(R.id.btn_delete_myseries);
        if (btnDeleteSelected == null) {
            Toast.makeText(this, "Không tìm thấy nút xóa, kiểm tra layout", Toast.LENGTH_LONG).show();
            return;
        }
        //        btnDeleteSelected.setVisibility(View.GONE); // Ẩn nút xóa mặc định

        // Xử lý sự kiện nhấn nút xóa
        btnDeleteSelected.setOnClickListener(v -> toggleDeleteMode());
    }

    // Phương thức để hiển thị/ẩn nút xóa
//    public void setDeleteButtonVisibility(int visibility) {
//        if (btnDeleteSelected != null) {
//            btnDeleteSelected.setVisibility(visibility);
//        } else {
//            Toast.makeText(this, "Lỗi: Nút xóa không được khởi tạo", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void toggleDeleteMode() {
        isDeleteMode = !isDeleteMode;
        if (!isDeleteMode) {
            // Thoát chế độ xóa, thực hiện xóa các item đã chọn
            deleteSelectedItems_activity();
        }
        // Cập nhật chế độ xóa cho tất cả fragment

        int currentItem = viewPager.getCurrentItem();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);
        if (fragment != null) {
            if (fragment instanceof DownloadsFragment) {
                btnDeleteSelected.setVisibility(View.VISIBLE);
                ((DownloadsFragment) fragment).setDeleteMode(isDeleteMode);
            } else if (fragment instanceof RecentFragment) {
                btnDeleteSelected.setVisibility(View.VISIBLE);
                ((RecentFragment) fragment).setDeleteMode(isDeleteMode);

            } else if (fragment instanceof SubscribedFragment) {
                btnDeleteSelected.setVisibility(View.GONE);

            } else if (fragment instanceof UnlockedFragment) {
                btnDeleteSelected.setVisibility(View.GONE);

            }
        }


        btnDeleteSelected.setVisibility(View.VISIBLE);

        // Thay đổi giao diện nút để phản ánh trạng thái
        btnDeleteSelected.setImageResource(isDeleteMode ? android.R.drawable.ic_menu_save : R.drawable.ic_delete);
    }

    private void deleteSelectedItems_activity() {
        int currentItem = viewPager.getCurrentItem();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);
        if (fragment != null) {
            if (fragment instanceof DownloadsFragment) {
                ((DownloadsFragment) fragment).deleteSelectedItems();
            } else if (fragment instanceof RecentFragment) {
                ((RecentFragment) fragment).deleteSelectedItems();
            } else if (fragment instanceof SubscribedFragment) {
                ((SubscribedFragment) fragment).deleteSelectedItems();
            } else if (fragment instanceof UnlockedFragment) {
                ((UnlockedFragment) fragment).deleteSelectedItems();
            }
        }
    }
    public void refreshAllFragments() {
        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
            if (fragment != null) {
                if (fragment instanceof RecentFragment) {
                    ((RecentFragment) fragment).refreshRecentTruyens();
                } else if (fragment instanceof DownloadsFragment) {
                    ((DownloadsFragment) fragment).refreshTruyenList();
                } else if (fragment instanceof SubscribedFragment) {
                    ((SubscribedFragment) fragment).refreshSubscribedTruyens();
                } else if (fragment instanceof UnlockedFragment) {
                    ((UnlockedFragment) fragment).refreshUnlockedTruyens();
                }
            }
        }
    }

}