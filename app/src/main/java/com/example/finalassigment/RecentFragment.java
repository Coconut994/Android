package com.example.finalassigment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;



import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {
    private MySeriesAdapter adapter;
    private TruyenDatabaseHelper dbHelper;
    private List<MySeries> recentTruyens;
    private MySeriesActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);


        if (getContext() == null) {
            Log.e("RecentFragment", "Context is null, fragment not attached to activity");
            return view;
        }

        if (getActivity() instanceof MySeriesActivity) {
            activity = (MySeriesActivity) getActivity();
        } else {
            Log.e("RecentFragment", "Activity is not MySeriesActivity");
            return view;
        }
        ListView listView = view.findViewById(R.id.listView);
        dbHelper = new TruyenDatabaseHelper(getContext());
        recentTruyens = new ArrayList<>();
        adapter = new MySeriesAdapter(getContext(), recentTruyens,"recent_truyens");


        if (listView == null) {
            Log.e("RecentFragment", "ListView is null - Kiểm tra fragment.xml");
            return view;
        }
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (!adapter.isDeleteMode()) {
                adapter.setDeleteMode(true);

            }
            return true;
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (!adapter.isDeleteMode()) {
                MySeries series = adapter.getItem(position);
                openTruyenDetail(series);
            }
        });

        refreshRecentTruyens();
        return view;
    }

    public void setDeleteMode(boolean deleteMode) {
        if (adapter != null) {
            adapter.setDeleteMode(deleteMode);

        }
    }

    public void deleteSelectedItems() {
        if (adapter == null || recentTruyens == null || dbHelper == null) {
            Log.e("RecentFragment", "Error: adapter=" + adapter + ", recentTruyens=" + recentTruyens + ", dbHelper=" + dbHelper);
            Toast.makeText(getContext(), "Lỗi: Không thể xóa do dữ liệu không sẵn sàng", Toast.LENGTH_SHORT).show();
            return;
        }

        List<MySeries> selectedItems = adapter.getSelectedItems();
        if (selectedItems.isEmpty()) {
            Toast.makeText(getContext(), "Chưa chọn truyện nào để xóaokoko", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Xóa truyện truy cập gần đây")
                .setMessage("Bạn có chắc muốn xóa " + selectedItems.size() + " truyện khỏi danh sách truy cập gần đây không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    for (MySeries series : selectedItems) {
                        if (dbHelper.removeFromRecent(series.getId())) {
                            Log.d("RecentFragment", "Removed from recent: " + series.getTenTruyen());
                        } else {
                            Log.w("RecentFragment", "Failed to remove from recent: " + series.getTenTruyen());
                        }
                    }
                    refreshRecentTruyens();
                    setDeleteMode(false);
                    Toast.makeText(getContext(), "Đã xóa " + selectedItems.size() + " truyện khỏi danh sách truy cập gần đây", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();

    }

    public void refreshRecentTruyens() {
        if (dbHelper == null || adapter == null || recentTruyens == null) {
            Log.e("RecentFragment", "Error: dbHelper=" + dbHelper + ", adapter=" + adapter + ", recentTruyens=" + recentTruyens);
            return;
        }

        recentTruyens.clear();
        List<MySeries> truyensFromDb = dbHelper.getRecentTruyen();
        recentTruyens.addAll(truyensFromDb);
        adapter.notifyDataSetChanged();
        Log.d("RecentFragment", "Refreshed recent truyens, count: " + recentTruyens.size());
    }

    public List<MySeries> getTruyenList() {
        return recentTruyens;
    }

    private void openTruyenDetail(MySeries series) {
        if (getContext() == null) return;
        Intent intent = new Intent(getContext(), TruyenDetailActivity.class);
        intent.putExtra("truyen_id", series.getId());
        startActivity(intent);
    }

    public MySeriesAdapter getAdapter() {
        return adapter;
    }


}