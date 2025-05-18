package com.example.seriesranking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class SeriesAdapter extends BaseAdapter {

    private final Context context;
    private final List<SeriesModel> seriesList;

    public SeriesAdapter(Context context, List<SeriesModel> seriesList) {
        this.context = context;
        this.seriesList = seriesList;
    }


    @Override
    public int getCount() {
        return seriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return seriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return seriesList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_series, parent, false);
        }

        ImageView imgSeries = convertView.findViewById(R.id.imgSeries);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvLikes = convertView.findViewById(R.id.tvLikes);
        TextView tvSummary = convertView.findViewById(R.id.tvSummary);

        SeriesModel model = seriesList.get(position);

        imgSeries.setImageResource(model.getImage());
        tvName.setText(model.getName());
        tvCategory.setText(model.getCategory());
        tvLikes.setText(String.valueOf(model.getLikes()));
        tvSummary.setText(model.getSummary());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("image", model.getImage());
            intent.putExtra("name", model.getName());
            intent.putExtra("category", model.getCategory());
            intent.putExtra("likes", model.getLikes());
            intent.putExtra("summary", model.getSummary());
            intent.putExtra("rank", model.getRank());
            context.startActivity(intent);
        });

        return convertView;
    }
}
