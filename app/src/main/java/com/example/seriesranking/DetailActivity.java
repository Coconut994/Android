package com.example.seriesranking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


public class DetailActivity extends AppCompatActivity {

    Button btnBack;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailCategory = findViewById(R.id.detailCategory);
        TextView detailLikes = findViewById(R.id.detailLikes);
        TextView detailRank = findViewById(R.id.detailRank);
        TextView detailSummary = findViewById(R.id.detailSummary);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int image = intent.getIntExtra("image", 0);
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        String likes = intent.getStringExtra("likes");
        int rank = intent.getIntExtra("rank", 0);
        String summary = intent.getStringExtra("summary");

        // Set dữ liệu lên giao diện
        detailImage.setImageResource(image);
        detailName.setText(name);
        detailCategory.setText(category);
        detailLikes.setText("Likes: " + likes);
        detailRank.setText("Rank: #" + rank);
        detailSummary.setText(summary);

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}