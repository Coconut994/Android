package com.example.seriesranking;

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
import java.util.Arrays;
import java.util.List;

public class PopularFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_trending_popular, container, false);

        ListView listView = view.findViewById(R.id.lvTrendingPopular);
        dbHelper = new DatabaseHelper(getContext());

        // Get Cursor from DatabaseHelper
        Cursor cursor = dbHelper.getSeriesSortedByRankChangeAndLikes();
        List<SeriesModel> seriesList = new ArrayList<>();

        // Convert Cursor to List<SeriesModel>
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int categoryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY);
                int likesIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LIKES);
                int summaryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SUMMARY);
                int rankIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RANK);
                int rankChangeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RANK_CHANGE);

                // Check if any column is missing
                if (idIndex == -1 || imageIndex == -1 || nameIndex == -1 || categoryIndex == -1 ||
                        likesIndex == -1 || summaryIndex == -1 || rankIndex == -1 || rankChangeIndex == -1) {
                    Log.e("PopularFragment", "One or more columns not found: " + Arrays.toString(cursor.getColumnNames()));
                    break;
                }

                int id = cursor.getInt(idIndex);
                int image = cursor.getInt(imageIndex);
                String name = cursor.getString(nameIndex);
                String category = cursor.getString(categoryIndex);
                String likes = cursor.getString(likesIndex);
                String summary = cursor.getString(summaryIndex);
                int rank = cursor.getInt(rankIndex);
                String rankChange = cursor.getString(rankChangeIndex);

                SeriesModel series = new SeriesModel(id, image, name, category, likes, summary, rank, rankChange);
                seriesList.add(series);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Create SeriesAdapter with List<SeriesModel>
        SeriesAdapter adapter = new SeriesAdapter(getContext(), seriesList);
        listView.setAdapter(adapter);

        return view;
    }
}