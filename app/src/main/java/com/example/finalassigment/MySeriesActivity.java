package com.example.finalassigment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalassigment.MySeriesPagerAdapter;
import com.example.finalassigment.DownloadsFragment;
import com.example.finalassigment.RecentFragment;
import com.example.finalassigment.SubscribedFragment;
import com.example.finalassigment.UnlockedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MySeriesActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    private ViewPager2 viewPager;
    private ImageButton btnDelete;
    private ImageView imvBack;
    private MySeriesPagerAdapter pagerAdapter;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_series);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnDelete = findViewById(R.id.btn_delete_myseries);
        imvBack =  findViewById(R.id.btnBackMySeries);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pagerAdapter = new MySeriesPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("RECENT");
                    break;
                case 1:
                    tab.setText("SUBSCRIBED");
                    break;
                case 2:
                    tab.setText("DOWNLOADS");
                    break;
                case 3:
                    tab.setText("UNLOCKED");
                    break;
            }
            Log.d("MySeriesActivity", "Thiết lập tab: " + tab.getText());
        }).attach();
        btnDelete.setOnClickListener(v -> toggleDeleteMode());
    }

    private void toggleDeleteMode() {
        isDeleteMode = !isDeleteMode;
        if (!isDeleteMode) {
            // Thoát chế độ xóa, thực hiện xóa các item đã chọn
            deleteSelectedItems();
        }
        // Cập nhật chế độ xóa cho tất cả fragment
        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
            if (fragment != null) {
                if (fragment instanceof DownloadsFragment) {
                    ((DownloadsFragment) fragment).setDeleteMode(isDeleteMode);
                } else if (fragment instanceof RecentFragment) {
                    ((RecentFragment) fragment).setDeleteMode(isDeleteMode);
                } else if (fragment instanceof SubscribedFragment) {
                    ((SubscribedFragment) fragment).setDeleteMode(isDeleteMode);
                } else if (fragment instanceof UnlockedFragment) {
                    ((UnlockedFragment) fragment).setDeleteMode(isDeleteMode);
                }
            }
        }
        // Thay đổi giao diện nút để phản ánh trạng thái
        btnDelete.setImageResource(isDeleteMode ? android.R.drawable.ic_menu_save : R.drawable.ic_delete);
    }

    private void deleteSelectedItems() {

        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
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
        Toast.makeText(this, "Đã xóa các item đã chọn", Toast.LENGTH_SHORT).show();
    }



//    private void deleteSelectedItems() {
//        // Kiểm tra xem có fragment nào đang hiển thị không
//        boolean hasItemsToDelete = false;
//        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
//            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
//            if (fragment != null) {
//                if (fragment instanceof DownloadsFragment) {
//                    for (Series series : ((DownloadsFragment) fragment).getTruyenList()) {
//                        if (series.isChecked()) {
//                            hasItemsToDelete = true;
//
//                            break;
//                        }
//                    }
//                } else if (fragment instanceof RecentFragment) {
////                    for (Truyen truyen : ((RecentFragment) fragment).getTruyenList()) {
////                        if (truyen.isChecked()) {
////                            hasItemsToDelete = true;
////                            break;
////                        }
////                    }
//                } else if (fragment instanceof SubscribedFragment) {
//                    // Thêm logic kiểm tra nếu SubscribedFragment đã được triển khai
//                } else if (fragment instanceof UnlockedFragment) {
//                    // Thêm logic kiểm tra nếu UnlockedFragment đã được triển khai
//                }
//                if (hasItemsToDelete) break;
//            }
//        }
//
//        if (!hasItemsToDelete) {
//            Toast.makeText(this, "Không có item nào được chọn để xóa", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Hiển thị AlertDialog để xác nhận
//        new AlertDialog.Builder(this)
//                .setTitle("Xác nhận xóa")
//                .setMessage("Bạn có chắc chắn muốn xóa các item đã chọn?")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Thực hiện xóa khi người dùng xác nhận
//                        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
//                            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
//                            if (fragment != null) {
//                                if (fragment instanceof DownloadsFragment) {
//                                    ((DownloadsFragment) fragment).deleteSelectedItems();
//                                } else if (fragment instanceof RecentFragment) {
//                                    ((RecentFragment) fragment).deleteSelectedItems();
//                                } else if (fragment instanceof SubscribedFragment) {
//                                    ((SubscribedFragment) fragment).deleteSelectedItems();
//                                } else if (fragment instanceof UnlockedFragment) {
//                                    ((UnlockedFragment) fragment).deleteSelectedItems();
//                                }
//                            }
//                        }
//                        Toast.makeText(MySeriesActivity.this, "Đã xóa các item đã chọn", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Hủy bỏ, không làm gì cả
//                        dialog.dismiss();
//                        Toast.makeText(MySeriesActivity.this, "Hủy xóa", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setCancelable(true)
//                .show();
//    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) return false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)  // ← dùng container mới
                .commit();
        return true;
    }
}