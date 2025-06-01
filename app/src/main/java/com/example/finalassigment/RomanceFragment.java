package com.example.finalassigment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
public class RomanceFragment extends Fragment {
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_romance, container, false);

        ListView listView = view.findViewById(R.id.lvRomance);
        dbHelper = new DatabaseHelper(getContext());

        List<SeriesModel> romanceList = getRomanceSeries();
        SeriesAdapter adapter = new SeriesAdapter(getContext(), romanceList);
        listView.setAdapter(adapter);
        return view;
    }

    private List<SeriesModel> getRomanceSeries() {
        List<SeriesModel> list = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_SERIES + " WHERE " + DatabaseHelper.COLUMN_CATEGORY + " = 'Romance'", null);
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
            cursor.close();
        } else {
            Log.e("RomanceFragment", "Cursor rỗng hoặc không hợp lệ");
            if (cursor != null) {
                cursor.close();
            }
        }

        list.sort((s1, s2) -> parseLikes(s2.getLikes()) - parseLikes(s1.getLikes()));
        return list;
    }

    private int parseLikes(String likes) {
        if (likes == null || likes.equals("N/A")) return 0;
        try {
            String cleaned = likes.replaceAll("[^0-9.]", "");
            float value = Float.parseFloat(cleaned);
            if (likes.contains("M")) value *= 1_000_000;
            else if (likes.contains("K")) value *= 1_000;
            return (int) value;
        } catch (NumberFormatException e) {
            Log.e("RomanceFragment", "Lỗi parse likes: " + likes, e);
            return 0;
        }
    }
}
