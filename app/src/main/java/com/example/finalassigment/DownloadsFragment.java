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

public class DownloadsFragment extends Fragment {
    private TruyenDatabaseHelper dbHelper;
    private MySeriesAdapter adapter;

    private List<MySeries> seriesList;
    private MySeriesActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        if (getContext() == null) {
            Log.e("DownloadsFragment", "Context is null, fragment not attached to activity");
            return view;
        }

        if (getActivity() instanceof MySeriesActivity) {
            activity = (MySeriesActivity) getActivity();
        } else {
            Log.e("DownloadsFragment", "Activity is not MySeriesActivity");
            return view;
        }

        dbHelper = new TruyenDatabaseHelper(getContext());
//        if (dbHelper.getDownloadedTruyen().isEmpty()) {
//            dbHelper.insertTruyen();
//            Log.d("DownloadsFragment", "Đã chèn truyện mẫu");
//        } else {
//            Log.d("DownloadsFragment", "Bảng đã có dữ liệu, số lượng: " + dbHelper.getDownloadedTruyen().size());
//        }

        ListView listView = view.findViewById(R.id.listView);
        if (listView == null) {
            Log.e("DownloadsFragment", "ListView is null - Kiểm tra fragment.xml");
            return view;
        }

        seriesList = new ArrayList<>();
        adapter = new MySeriesAdapter(getContext(), seriesList, "downloaded_truyens");
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

        refreshTruyenList();
        return view;
    }

    public void setDeleteMode(boolean deleteMode) {
        if (adapter != null) {
            adapter.setDeleteMode(deleteMode);

            Log.d("DownloadsFragment", "Đã đặt deleteMode: " + deleteMode);
        }
    }

    public void deleteSelectedItems() {
        if (adapter == null || seriesList == null || dbHelper == null) {
            Log.e("DownloadsFragment", "Error: adapter=" + adapter + ", seriesList=" + seriesList + ", dbHelper=" + dbHelper);
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
                        if (dbHelper.removeFromDownloads(series.getId())) {
                            Log.d("DownloadsFragment", "Removed from downloads: " + series.getTenTruyen());
                        } else {
                            Log.w("DownloadsFragment", "Failed to remove from downloads: " + series.getTenTruyen());
                        }
                    }
                    refreshTruyenList();
                    setDeleteMode(false);
                    Toast.makeText(getContext(), "Đã xóa " + itemsToDelete.size() + " truyện khỏi danh sách đã tải", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();

    }

    public void refreshTruyenList() {
        if (adapter != null && seriesList != null) {
            seriesList.clear();
            seriesList.addAll(dbHelper.getDownloadedTruyen());
            adapter.notifyDataSetChanged();
            Log.d("DownloadsFragment", "Số truyện tải: " + seriesList.size());
        }
    }

    public List<MySeries> getTruyenList() {
        return seriesList;
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
    @Override
    public void onResume(){
        super.onResume();
        refreshTruyenList();
    }

}