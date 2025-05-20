package com.example.finalassigment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;
//import java.security.AccessControlContext;
import java.util.Arrays;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ranking.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_SERIES = "Series";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_LIKES = "likes";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_RANK_CHANGE = "rank_change";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SERIES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_IMAGE + " INTEGER, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_LIKES + " TEXT, "
                + COLUMN_SUMMARY + " TEXT, "
                + COLUMN_RANK + " INTEGER, "
                + COLUMN_RANK_CHANGE + " TEXT)";

        db.execSQL(createTable);

        // Thêm dữ liệu cố định từ UI
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_SERIES + " VALUES " +
                "(1, " + R.drawable.lookism + ", 'Lookism', 'Drama', '55.9M', 'Daniel, a loner, wakes up in a new body.', 1, '+30')," +
                "(2, " + R.drawable.webtoon_now + ", 'WEBTOON Now', 'Informative', '2.4M', 'Latest updates on popular webtoons.', 2, '+21')," +
                "(3, " + R.drawable.winter_moon + ", 'Winter Moon', 'Fantasy', '37.7M', 'Epic fantasy adventure.', 3, '+24')," +
                "(4, " + R.drawable.singles_royale + ", 'Singles Royale', 'Romance', '21.9K', 'A royal court romance.', 4, '+32')," +
                "(5, " + R.drawable.dark_mermaid + ", 'Dark Mermaid', 'Fantasy', '476.1K', 'Underwater drama with a twist.', 5, '+19')," +
                "(6, " + R.drawable.iseops_romance + ", 'Iseop’s Romance', 'Romance', '4M', 'Heartfelt love story.', 6, '+12')," +
                "(7, " + R.drawable.my_aggravating_sovereign + ", 'My Aggravating Sovereign', 'Romance', '267.6K', 'Romantic entanglements in the kingdom.', 7, '+18')," +
                "(8, " + R.drawable.press_play_sami + ", 'Press Play, Sami', 'Romance', 'N/A', 'Romantic drama with music.', 8, '0')," +
                "(9, " + R.drawable.heart_acres + ", 'Heart Acres', 'Romance', '215K', 'A romantic tale in the countryside.', 9, '-2')," +
                "(10, " + R.drawable.the_one_who_parried_death + ", 'The One Who Parried Death', 'Action', '416.8K', 'A warrior defies death itself.', 10, '+7')," +
                "(11, " + R.drawable.monster_eater + ", 'Monster Eater', 'Fantasy', '228.6K', 'A hero consumes monsters to gain power.', 11, '-4')," +
                "(12, " + R.drawable.nebulas_civilization + ", 'Nebula’s Civilization', 'Fantasy', '336.1K', 'A cosmic civilization-building journey.', 12, '-4')," +
                "(13, " + R.drawable.the_witch_and_the_bull + ", 'The Witch and The Bull', 'Fantasy', '8.3M', 'A magical bond between a witch and a bull.', 13, '-8')," +
                "(14, " + R.drawable.what_death_taught_me + ", 'What Death Taught Me', 'Romance', '319.9K', 'Lessons of love through loss.', 14, '+11')," +
                "(15, " + R.drawable.phase + ", 'Phase', 'Romance', '7.7M', 'A story of love in different phases of life.', 15, '-4');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERIES);
        onCreate(db);
    }

    public SeriesModel getSeriesById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SERIES, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        SeriesModel series = null;
        if (cursor.moveToFirst()) {
            // Kiểm tra các chỉ số cột
            int imageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
            int likesIndex = cursor.getColumnIndex(COLUMN_LIKES);
            int summaryIndex = cursor.getColumnIndex(COLUMN_SUMMARY);
            int rankIndex = cursor.getColumnIndex(COLUMN_RANK);
            int rankChangeIndex = cursor.getColumnIndex(COLUMN_RANK_CHANGE);

            // Kiểm tra xem tất cả các cột có tồn tại không
            if (imageIndex == -1 || nameIndex == -1 || categoryIndex == -1 ||
                    likesIndex == -1 || summaryIndex == -1 || rankIndex == -1 || rankChangeIndex == -1) {
                Log.e("DatabaseHelper", "One or more columns not found in Cursor: " + Arrays.toString(cursor.getColumnNames()));
                cursor.close();
                return null; // Trả về null hoặc xử lý lỗi
            }

            // Truy xuất dữ liệu
            int image = cursor.getInt(imageIndex);
            String name = cursor.getString(nameIndex);
            String category = cursor.getString(categoryIndex);
            String likes = cursor.getString(likesIndex);
            String summary = cursor.getString(summaryIndex);
            int rank = cursor.getInt(rankIndex);
            String rankChange = cursor.getString(rankChangeIndex);

            series = new SeriesModel(id, image, name, category, likes, summary, rank, rankChange);
            cursor.close();
        }
        return series;
    }



//    public Cursor getAllSeriesByCategory(String category) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_SERIES, null, COLUMN_CATEGORY + "=?",
//                new String[]{category}, null, null, COLUMN_RANK + " ASC");
//    }

//    public Cursor getAllSeries() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_SERIES, null, null, null, null, null, COLUMN_RANK + " ASC");
//    }

//    public Cursor getSeriesOrderedByLikesDesc() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_SERIES, null, null, null, null, null, COLUMN_LIKES + " DESC");
//    }

    public Cursor getSeriesSortedByRankChangeAndLikes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String orderBy = "CAST(REPLACE(" + COLUMN_RANK_CHANGE + ", '+', '') AS INTEGER) DESC, " +
                "CAST(" + COLUMN_LIKES + " AS INTEGER) DESC";
        return db.query(TABLE_SERIES, null, null, null, null, null, orderBy);
    }


}
