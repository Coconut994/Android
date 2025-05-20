package com.example.finalassigment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_trending, container, false);

        ListView listView = view.findViewById(R.id.lvTrending);
        dbHelper = new DatabaseHelper(getContext());

        // Lấy danh sách Series xếp theo lượt likes giảm dần
        List<SeriesModel> trendingList = getTrendingSeries();
        SeriesAdapter adapter = new SeriesAdapter(getContext(), trendingList);
        listView.setAdapter(adapter);
        return view;
    }

    private List<SeriesModel> getTrendingSeries() {
        List<SeriesModel> list = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_SERIES + " ORDER BY CAST(" + DatabaseHelper.COLUMN_LIKES + " AS INTEGER) DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));
                String likes = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LIKES));
                String summary = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SUMMARY));
                int rank = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RANK));
                String rankChange = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RANK_CHANGE));
                list.add(new SeriesModel(id, image, name, category, likes, summary, rank, rankChange));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}
