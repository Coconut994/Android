package com.example.finalassigment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainMangaActivity extends AppCompatActivity {
    Button btnPreview, btnEpisodes, btnRecommend, btnSubcribed;
    private ImageView headerImage, btnBackSeries;
    private TruyenDatabaseHelper truyenDatabaseHelper;

     int currentMangaId = 1;
    private int defaultChapterId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_main);

        headerImage  = findViewById(R.id.header_image);
        btnPreview   = findViewById(R.id.btn_preview);
        btnEpisodes  = findViewById(R.id.btn_episodes);
        btnRecommend = findViewById(R.id.btn_recommend);
        btnBackSeries = findViewById(R.id.btnBackSeries);
        btnSubcribed = findViewById(R.id.btn_subscribe);
        truyenDatabaseHelper = new TruyenDatabaseHelper(this);
        Intent myIntent = getIntent();

//        // Show ảnh bìa mặc định

        currentMangaId = myIntent.getIntExtra("story_id",2);
        truyenDatabaseHelper.addRecentTruyen(currentMangaId);
        if(currentMangaId > 2){
            currentMangaId = 1;
            Toast.makeText(this, "Truyện đang cập nhật nhé mí\nXem tạm Anya nhé >.< !", Toast.LENGTH_SHORT).show();
        }

//        else currentMangaId =2;
        MangaDatabaseHelper dbHelper = new MangaDatabaseHelper(this);
        defaultChapterId = dbHelper.getFirstChapterId(currentMangaId);
        updateHeaderImage(currentMangaId);

        // Mặc định: show Preview chương đầu của manga đầu
        replaceFragment(PreviewFragment.newInstance(defaultChapterId));
        btnPreview.setSelected(true);
        btnPreview.setOnClickListener(v -> {
            btnPreview.setSelected(false);
            btnEpisodes.setSelected(false);
            btnRecommend.setSelected(false);
            btnPreview.setSelected(true);
            replaceFragment(PreviewFragment.newInstance(defaultChapterId));
            updateHeaderImage(currentMangaId);
        });

        btnEpisodes.setOnClickListener(v -> {
            btnPreview.setSelected(false);
            btnEpisodes.setSelected(false);
            btnRecommend.setSelected(false);
            btnEpisodes.setSelected(true);
            replaceFragment(EpisodesFragment.newInstance(currentMangaId));
            updateHeaderImage(currentMangaId);
        });

        btnRecommend.setOnClickListener(v -> {
            btnPreview.setSelected(false);
            btnEpisodes.setSelected(false);
            btnRecommend.setSelected(false);
            btnRecommend.setSelected(true);
            replaceFragment(new RecommendationFragment());
        });

        btnBackSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubcribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMangaActivity.this, "Đã vào danh sách Subcribed của bạn yêu rồi nè >.<", Toast.LENGTH_SHORT).show();
                truyenDatabaseHelper.addSubscribedTruyen(currentMangaId);
            }
        });
    }

    /**
     * Cập nhật lại header_image từ database
     */
    public void updateHeaderImage(int mangaId) {
        // Lấy tên file cover_image từ DB
        MangaDatabaseHelper dbh = new MangaDatabaseHelper(this);
        String coverName = dbh.getCoverImageName(mangaId);  // phương thức mới
        // Nếu null hoặc rỗng, có thể dùng placeholder
        if (coverName == null || coverName.isEmpty()) {
            headerImage.setImageResource(R.drawable.placeholder_cover);
        } else {
            int resId = getResources().getIdentifier(
                    coverName, "drawable", getPackageName());
            if (resId != 0) {
                headerImage.setImageResource(resId);
            } else {
                headerImage.setImageResource(R.drawable.placeholder_cover);
            }
        }
    }

    public void setCurrentManga(int mangaId, int firstChapterId) {
        this.currentMangaId   = mangaId;
        this.defaultChapterId = firstChapterId;
        // Khi user chọn manga mới, đồng thời update luôn header
        updateHeaderImage(mangaId);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
