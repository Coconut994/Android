package com.example.finalassigment;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




public class HomePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private DatabaseHelper dbHelper;
    private TextView tvTrending, tvPopular;
    private TextView edtSearch;
    private ImageView imgSearch;
    private TruyenDatabaseHelper truyenDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        truyenDatabaseHelper = new TruyenDatabaseHelper(this);
//        dbHelper = new DatabaseHelper(getContext());
        // Thiết lập RecyclerView cũ
        edtSearch = findViewById(R.id.searchEditText);
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Chờ Dev của chúng mình cập nhật nhé >.<", Toast.LENGTH_SHORT).show();
            }
        });
        imgSearch = findViewById(R.id.searchIcon);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Kĩ năng của chúng mình chưa làm được á >.<", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Story> trendingStories = new ArrayList<>();
        trendingStories.add(new Story(1,R.drawable.anhbia1, "SpyX Family", 5590000));
        trendingStories.add(new Story(2,R.drawable.anhbia2, "Dr.Stone", 28800000));
        trendingStories.add(new Story(3,R.drawable.singles_royale, "Singles Royale", 2500000));
        trendingStories.add(new Story(4,R.drawable.dark_mermaid, "Dark Mermaid", 1500000));
        trendingStories.add(new Story(5,R.drawable.iseops_romance, "Iseop’s Romance", 4500000));
        trendingStories.add(new Story(6,R.drawable.my_aggravating_sovereign, "My Aggravating ", 3200000));
        StoryAdapter trendingAdapter = new StoryAdapter(trendingStories, new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Story story) {
                // Xử lý click vào item Trending
                truyenDatabaseHelper.addRecentTruyen(story.getId());
                Intent intent = new Intent(HomePage.this, MainMangaActivity.class);
                intent.putExtra("story_id", story.getId());
                intent.putExtra("story_title", story.getTitle());
                startActivity(intent);

            }
        });
        rvTrending.setAdapter(trendingAdapter);

        RecyclerView rvPopular = findViewById(R.id.rvPopular);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPopular.setLayoutManager(layoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        rvPopular.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));
        List<Story> popularStories = new ArrayList<>();
        popularStories.add(new Story(7,R.drawable.my_aggravating_sovereign, "My Aggravating Sovereign", 2676000));
        popularStories.add(new Story(8,R.drawable.webtoon_now, "Heart Acres", 2150000));
        popularStories.add(new Story(9,R.drawable.heart_acres, "Singles Royale", 2500000));
        popularStories.add(new Story(10,R.drawable.monster_eater, "Monster Eater", 1260000));
        popularStories.add(new Story(11,R.drawable.iseops_romance, "Iseop’s Romance", 4500000));
        popularStories.add(new Story(12,R.drawable.my_aggravating_sovereign, "My Aggravating Sovereign", 3200000));
        StoryAdapter popularAdapter = new StoryAdapter(trendingStories, new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Story story) {
                // Xử lý click vào item Trending
                truyenDatabaseHelper.addRecentTruyen(story.getId());
                Intent intent = new Intent(HomePage.this, MainMangaActivity.class);
                intent.putExtra("story_id", story.getId());
                intent.putExtra("story_title", story.getTitle());
                startActivity(intent);
//                Toast.makeText(HomePage.this, "" + story.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        rvPopular.setAdapter(popularAdapter);


        tvTrending = findViewById(R.id.trendingTitle);
        tvPopular = findViewById(R.id.popularTitle);
        tvTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,MainRankingActivity.class));
//                finish();
            }
        });
        tvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,MainRankingActivity.class));
//                finish();
            }
        });
        // Thiết lập Bottom Navigation
        bottomNavigation = findViewById(R.id.bottom_navigation);
        // Hiển thị ForYouFragment khi mở app
        if (savedInstanceState == null) {
            loadFragment(new ForYouFragment());
        }
        // Sử dụng listener kiểu cũ để tránh lỗi "constant expression required"
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_for_you) {
                    selectedFragment = new ForYouFragment();
                } else if (id == R.id.nav_my) {
//                    selectedFragment = new MyFragment();
                    startActivity(new Intent(HomePage.this, MySeriesActivity.class));
                } else if (id == R.id.nav_more) {
//                    selectedFragment = new MoreFragment();
//                    Toast.makeText(HomePage.this, "Chuc nang dang duoc phat trien", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(HomePage.this)
                            .setTitle("Đăng xuất tài khoản!")
                            .setMessage("Ở lại đi mà! Hãy cùng mình đi tiếp câu chuyện còn dang dở >.<")
                            .setPositiveButton("Đăng xuất", (dialog, which) -> {
                                startActivity(new Intent(HomePage.this, login.class));
                                finish();
                            })
                            .setNegativeButton("Ở lại", null)
                            .setCancelable(true)
                            .show();
                }
                return loadFragment(selectedFragment);
            }
        });
    }
//    private void setupTrending() {
//        RecyclerView rvTrending = findViewById(R.id.rvTrending);
//        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        List<Story> trendingStories = new ArrayList<>();
//        trendingStories.add(new Story(1,R.drawable.anhbia1, "SpyX Family", 5590000));
//        trendingStories.add(new Story(2,R.drawable.anhbia2, "Dr.Stone", 28800000));
//        trendingStories.add(new Story(3,R.drawable.singles_royale, "Singles Royale", 2500000));
//        trendingStories.add(new Story(4,R.drawable.dark_mermaid, "Dark Mermaid", 1500000));
//        trendingStories.add(new Story(5,R.drawable.iseops_romance, "Iseop’s Romance", 4500000));
//        trendingStories.add(new Story(6,R.drawable.my_aggravating_sovereign, "My Aggravating ", 3200000));
//        StoryAdapter trendingAdapter = new StoryAdapter(trendingStories);
//        rvTrending.setAdapter(trendingAdapter);
//    }

//    private void setupPopular() {
//        RecyclerView rvPopular = findViewById(R.id.rvPopular);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        rvPopular.setLayoutManager(layoutManager);
//
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
//        rvPopular.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));
//
//        List<Story> trendingStories = new ArrayList<>();
//
////        "(7, " + R.drawable.my_aggravating_sovereign + ", 'My Aggravating Sovereign', 'Romance', '267.6K', 'Romantic entanglements in the kingdom.', 7, '+18')," +
////                "(8, " + R.drawable.press_play_sami + ", 'Press Play, Sami', 'Romance', 'N/A', 'Romantic drama with music.', 8, '0')," +
////                "(9, " + R.drawable.heart_acres + ", 'Heart Acres', 'Romance', '215K', 'A romantic tale in the countryside.', 9, '-2')," +
////                "(10, " + R.drawable.the_one_who_parried_death + ", 'The One Who Parried Death', 'Action', '416.8K', 'A warrior defies death itself.', 10, '+7')," +
////                "(11, " + R.drawable.monster_eater + ", 'Monster Eater', 'Fantasy', '228.6K', 'A hero consumes monsters to gain power.', 11, '-4')," +
////                "(12, " + R.drawable.nebulas_civilization + ", 'Nebula’s Civilization', 'Fantasy', '336.1K', 'A cosmic civilization-building journey.', 12, '-4')," +
////                "(13, " + R.drawable.the_witch_and_the_bull + ", 'The Witch and The Bull', 'Fantasy', '8.3M', 'A magical bond between a witch and a bull.', 13, '-8')," +
////                "(14, " + R.drawable.what_death_taught_me + ", 'What Death Taught Me', 'Romance', '319.9K', 'Lessons of love through loss.', 14, '+11')," +
//
//
//        trendingStories.add(new Story(7,R.drawable.my_aggravating_sovereign, "My Aggravating Sovereign", 2676000));
//        trendingStories.add(new Story(8,R.drawable.webtoon_now, "Heart Acres", 2150000));
//        trendingStories.add(new Story(9,R.drawable.heart_acres, "Singles Royale", 2500000));
//        trendingStories.add(new Story(10,R.drawable.monster_eater, "Monster Eater", 1260000));
//        trendingStories.add(new Story(11,R.drawable.iseops_romance, "Iseop’s Romance", 4500000));
//        trendingStories.add(new Story(12,R.drawable.my_aggravating_sovereign, "My Aggravating Sovereign", 3200000));
//
//        StoryAdapter popularAdapter = new StoryAdapter(trendingStories);
//        rvPopular.setAdapter(popularAdapter);
//    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) return false;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)  // ← dùng container mới
                .commit();
        return true;
    }
}
