package com.example.finalassigment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TruyenDetailActivity extends AppCompatActivity {
    private TextView tvTenTruyen;
    private TextView tvTrangThai;
    private ImageView item_image;
    private TruyenDatabaseHelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen_detail);

        item_image = findViewById(R.id.imageView_detail);
        tvTenTruyen = findViewById(R.id.tvTenTruyenDetail);
        tvTrangThai = findViewById(R.id.tvTrangThaiDetail);
        dbhelper = new TruyenDatabaseHelper(TruyenDetailActivity.this);
        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            MySeries series  = (MySeries) extras.getSerializable("series");

            String tenTruyen = series.getTenTruyen();
            String trangThai = series.getTrangThai()    ;
            int image = series.getImage();

            // Hiển thị dữ liệu
            item_image.setImageResource(image != 0 ? image : R.drawable.ic_launcher_background);
            tvTenTruyen.setText(tenTruyen != null ? tenTruyen : "Không có tiêu đề");
            tvTrangThai.setText(trangThai != null ? trangThai : "Không có trạng thái");
        }
    }
}