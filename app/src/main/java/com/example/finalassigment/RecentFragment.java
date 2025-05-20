package com.example.finalassigment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.finalassigment.MySeriesAdapter;
import com.example.finalassigment.R;
import com.example.finalassigment.Series;
import com.example.finalassigment.TruyenDatabaseHelper;
import com.example.finalassigment.TruyenDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment  extends Fragment {
    private TruyenDatabaseHelper dbHelper;
    private MySeriesAdapter adapter;
    private ListView listView;
    private List<Series> seriesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        dbHelper = new TruyenDatabaseHelper(getContext());
        if (dbHelper.getAllTruyen().isEmpty()) {
            dbHelper.insertTruyen();
            Log.d("DownloadsFragment", "Đã chèn truyện mẫu");
        } else {
            Log.d("DownloadsFragment", "Bảng đã có dữ liệu, số lượng: " + dbHelper.getAllTruyen().size());
        }

        listView = view.findViewById(R.id.listView);
        if (listView == null) {
            Log.e("DownloadsFragment", "ListView is null - Kiểm tra fragment_downloads.xml");
            return view;
        }

        seriesList = new ArrayList<>();
        adapter = new MySeriesAdapter(getContext(), seriesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Series series = seriesList.get(position);
                if (!adapter.isDeleteMode()) {
                    // Cập nhật thời gian truy cập
                    dbHelper.updateLastAccessed(series.getId(), System.currentTimeMillis());
                    // Mở màn hình chi tiết
                    Intent intent = new Intent(getContext(), TruyenDetailActivity.class);
                    intent.putExtra("tenTruyen", series.getTenTruyen());
                    intent.putExtra("trangThai", series.getTrangThai());
                    startActivity(intent);
                }
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
        if (adapter != null) {
            List<Series> itemsToDelete = new ArrayList<>();
            for (Series series : seriesList) {
                if (series.isChecked()) {
                    itemsToDelete.add(series);
                    dbHelper.deleteTruyen(series.getId());
                    Log.d("DownloadsFragment", "Xóa truyện: " + series.getTenTruyen());
                }
            }
            seriesList.removeAll(itemsToDelete);
            adapter.notifyDataSetChanged();
            Log.d("DownloadsFragment", "Số truyện còn lại: " + seriesList.size());
        }
    }

    public void refreshTruyenList() {
        if (adapter != null) {
            seriesList.clear();
            seriesList.addAll(dbHelper.getHaiTruyenDauTien());
            adapter.notifyDataSetChanged();
            Log.d("DownloadsFragment", "Số truyện tải: " + seriesList.size() + ", Dữ liệu: " + seriesList.toString());
        }
    }
    public List<Series> getTruyenList() {
        return seriesList;
    }

}