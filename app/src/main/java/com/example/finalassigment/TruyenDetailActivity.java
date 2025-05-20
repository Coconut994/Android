package com.example.finalassigment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TruyenDetailActivity extends AppCompatActivity {
    private TextView tvTenTruyen;
    private TextView tvTrangThai;
    private ImageView item_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen_detail);

        item_image = findViewById(R.id.imageView_detail);
        tvTenTruyen = findViewById(R.id.tvTenTruyenDetail);
        tvTrangThai = findViewById(R.id.tvTrangThaiDetail);

        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tenTruyen = extras.getString("tenTruyen");
            String trangThai = extras.getString("trangThai");
            int image = extras.getInt("image");

            // Hiển thị dữ liệu
            item_image.setImageResource(image != 0 ? image : R.drawable.ic_launcher_background);
            tvTenTruyen.setText(tenTruyen != null ? tenTruyen : "Không có tiêu đề");
            tvTrangThai.setText(trangThai != null ? trangThai : "Không có trạng thái");
        }
    }
}