package com.example.seriesranking;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ranking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Thiết lập ViewPager2 và TabLayout
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Tạo adapter cho ViewPager2
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0: return new TrendingFragment();
                    case 1: return new PopularFragment();
                    case 2: return new DramaFragment();
                    case 3: return new FantasyFragment();
                    default: return new TrendingFragment(); // Mặc định
                }
            }

            @Override
            public int getItemCount() {
                return 5; // TRENDING, POPULAR, DRAMA, FANTASY, ROMANCE
            }
        };

        viewPager.setAdapter(adapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("TRENDING"); break;
                case 1: tab.setText("POPULAR"); break;
                case 2: tab.setText("DRAMA"); break;
                case 3: tab.setText("FANTASY"); break;
                case 4: tab.setText("ROMANCE"); break;
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        Drawable searchIcon = searchItem.getIcon();
        if (searchIcon != null) {
            searchIcon.mutate();
            searchIcon.setTint(ContextCompat.getColor(this, android.R.color.black));
            searchItem.setIcon(searchIcon);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        } else if (id == R.id.action_search) {
            // Xử lý hành động tìm kiếm
            // TODO: Thêm logic tìm kiếm
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}