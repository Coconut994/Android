package com.example.finalassigment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
    public void deleteSelectedItems(){
        Toast.makeText(activity, "Không hỗ trợ xóa ở đây!", Toast.LENGTH_SHORT).show();
    }
}