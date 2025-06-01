package com.example.finalassigment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySeriesAdapter extends ArrayAdapter<MySeries> {
    private Context context;
    private List<MySeries> seriesList;
    private boolean deleteMode = false;
    private TruyenDatabaseHelper dbHelper;
    private String tableName; // Để biết adapter đang làm việc với bảng nào

    public MySeriesAdapter(Context context, List<MySeries> objects, String tableName) {
        super(context, 0, objects);
        this.context = context;
        this.seriesList = objects;
        this.dbHelper = new TruyenDatabaseHelper(context);
        this.tableName = tableName;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        if (!deleteMode) {
            for (MySeries series : seriesList) {
                series.setChecked(false);
                series.setCheckBoxVisible(false);
            }
        }
        notifyDataSetChanged();
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public List<MySeries> getSelectedItems() {
        List<MySeries> selectedItems = new ArrayList<>();
        for (MySeries series : seriesList) {
            if (series.isChecked()) {
                selectedItems.add(series);
            }
        }
        return selectedItems;
    }

    static class ViewHolder {
        ImageView item_image;
        TextView tvTen;
        TextView tvTrangThai;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MySeries series = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_truyen, parent, false);
            holder = new ViewHolder();
            holder.item_image = convertView.findViewById(R.id.item_image);
            holder.tvTen = convertView.findViewById(R.id.tvTen);
            holder.tvTrangThai = convertView.findViewById(R.id.tvTrangThai);
            holder.checkBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (series == null) {
            holder.item_image.setImageResource(R.drawable.ic_launcher_background);
            holder.tvTen.setText("");
            holder.tvTrangThai.setText("");
            holder.checkBox.setVisibility(View.GONE);
            return convertView;
        }

        // Hiển thị thông tin truyện
        holder.item_image.setImageResource(series.getImage() != 0 ? series.getImage() : R.drawable.ic_launcher_background);
        holder.tvTen.setText(series.getTenTruyen() != null ? series.getTenTruyen() : "");
        holder.tvTrangThai.setText(series.getTrangThai() != null ? series.getTrangThai() : "");

        // Điều khiển CheckBox dựa trên deleteMode
        if (deleteMode) {
            series.setCheckBoxVisible(true);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(series.isChecked());
        } else {
            series.setCheckBoxVisible(false);
            holder.checkBox.setVisibility(View.GONE);
        }

        // Set listener cho CheckBox
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isCheckedNew) -> {
            series.setChecked(isCheckedNew);
        });

        // Long-click để xóa item
//        convertView.setOnLongClickListener(v -> {
//            if (!deleteMode) {
//                new AlertDialog.Builder(context)
//                        .setTitle("Xóa truyện")
//                        .setMessage("Bạn có chắc muốn xóa truyện \"" + series.getTenTruyen() + "\" không?")
//                        .setPositiveButton("Xóa", (dialog, which) -> {
//                            Log.d("SeriesAdapter", "Attempting to delete ID: " + series.getId() + " from table: " + tableName);
//                            if (dbHelper.deleteTruyen(series.getId(), tableName)) {
//                                seriesList.remove(series);
//                                notifyDataSetChanged();
//                                Toast.makeText(context, "Đã xóa truyện", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(context, "Xóa thất bại, kiểm tra ID hoặc bảng", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Hủy", null)
//                        .show();
//                return true;
//            }
//            return false;
//        });

        // Set click cho toàn bộ item
        convertView.setOnClickListener(v -> {
            if (deleteMode) {
                boolean newCheckedState = !series.isChecked();
                series.setChecked(newCheckedState);
                holder.checkBox.setChecked(newCheckedState);
            } else {
                openTruyenDetail(series);
            }
        });

        return convertView;
    }

    private void openTruyenDetail(MySeries series) {
        Context context = getContext();
        if (context instanceof Activity) {
            // Lưu vào danh sách truy cập gần đây
            if (dbHelper != null) {
                series.setLastAccessed(System.currentTimeMillis());
                dbHelper.addRecentTruyen(series.getId());
            }

            ((MySeriesActivity) context).refreshAllFragments();

            // Mở chi tiết
            Intent intent = new Intent(context, MainMangaActivity.class);
            intent.putExtra("story_id", (Serializable) series.getId());
            context.startActivity(intent);
        } else {
            Log.e("SeriesAdapter", "Context is not an Activity, cannot start TruyenDetailActivity");
        }
    }
    private void openTruyenDetail(int id) {
        Context context = getContext();
        if (context instanceof Activity) {
            // Lưu vào danh sách truy cập gần đây
            if (dbHelper != null) {
                dbHelper.addRecentTruyen(id);
            }
        } else {
            Log.e("SeriesAdapter", "Context is not an Activity, cannot start TruyenDetailActivity");
        }
    }
}