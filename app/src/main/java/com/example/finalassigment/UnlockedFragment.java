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

public class UnlockedFragment extends Fragment {
    private MySeriesAdapter adapter;
    private TruyenDatabaseHelper dbHelper;
    private List<MySeries> unlockedTruyens;
    private MySeriesActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        if (getActivity() instanceof MySeriesActivity) {
            activity = (MySeriesActivity) getActivity();
        } else {
            Log.e("UnlockedFragment", "Activity is not MySeriesActivity");
            return view;
        }

        dbHelper = new TruyenDatabaseHelper(getContext());
        unlockedTruyens = new ArrayList<>();
        adapter = new MySeriesAdapter(getContext(), unlockedTruyens, "unlocked_truyens");

        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (!adapter.isDeleteMode()) {
                MySeries series = adapter.getItem(position);
                openTruyenDetail(series);
            }
        });

        refreshUnlockedTruyens();
        return view;
    }

    public void refreshUnlockedTruyens() {
        if (dbHelper == null || adapter == null || unlockedTruyens == null) {
            Log.e("UnlockedFragment", "Error: dbHelper=" + dbHelper + ", adapter=" + adapter + ", unlockedTruyens=" + unlockedTruyens);
            return;
        }

        unlockedTruyens.clear();
        unlockedTruyens.addAll(dbHelper.getUnlockedTruyen());
        adapter.notifyDataSetChanged();
        Log.d("UnlockedFragment", "Refreshed unlocked truyens, count: " + unlockedTruyens.size());
    }

    public List<MySeries> getTruyenList() {
        return unlockedTruyens;
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
//    public void deleteSelectedItems(){
//        Toast.makeText(activity, "Không hỗ trợ xóa ở đây!", Toast.LENGTH_SHORT).show();
//    }
public void setDeleteMode(boolean deleteMode) {
    if (adapter != null) {
        adapter.setDeleteMode(deleteMode);

        Log.d("DownloadsFragment", "Đã đặt deleteMode: " + deleteMode);
    }
}

    public void deleteSelectedItems() {
        if (adapter == null || unlockedTruyens == null || dbHelper == null) {
            Log.e("DownloadsFragment", "Error: adapter=" + adapter + ", seriesList=" + unlockedTruyens + ", dbHelper=" + dbHelper);
            Toast.makeText(getContext(), "Lỗi: Không thể xóa do dữ liệu không sẵn sàng", Toast.LENGTH_SHORT).show();
            return;
        }

        List<MySeries> itemsToDelete = adapter.getSelectedItems();
        if (itemsToDelete.isEmpty()) {
            Toast.makeText(getContext(), "Chưa chọn truyện nào để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Xóa truyện đã tải")
                .setMessage("Bạn có chắc muốn xóa " + itemsToDelete.size() + " truyện khỏi danh sách đã tải không?" )
                .setPositiveButton("Xóa", (dialog, which) -> {
                    for (MySeries series : itemsToDelete) {
                        if (dbHelper.removeFromUnlocked(series.getId())) {
                            Log.d("DownloadsFragment", "Removed from downloads: " + series.getTenTruyen());
                        } else {
                            Log.w("DownloadsFragment", "Failed to remove from downloads: " + series.getTenTruyen());
                        }
                    }
                    refreshUnlockedTruyens();
                    setDeleteMode(false);
                    Toast.makeText(getContext(), "Đã xóa " + itemsToDelete.size() + " truyện khỏi danh sách đã mở khóa", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();

    }
    @Override
    public void onResume(){
        super.onResume();
        refreshUnlockedTruyens();
    }

}