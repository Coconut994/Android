package com.example.finalassigment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TruyenDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "truyen.db";
    private static final int DATABASE_VERSION = 2; // Tăng version để chạy onUpgrade
    private static final String TABLE_TRUYEN = "truyen";
    private static final String TABLE_RECENT_TRUYENS = "recent_truyens";
    private static final String TABLE_DOWNLOADED_TRUYENS = "downloaded_truyens";
    private static final String TABLE_SUBSCRIBED_TRUYENS = "subscribed_truyens";
    private static final String TABLE_UNLOCKED_TRUYENS = "unlocked_truyens";
    private static final int MAX_RECENT_TRUYENS = 20;

    // Bảng truyen chính
    private static final String CREATE_TABLE_TRUYEN = "CREATE TABLE " + TABLE_TRUYEN + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ten TEXT NOT NULL, " +
            "trangthai TEXT NOT NULL, " +
            "isChecked INTEGER DEFAULT 0, " +
            "isCheckBoxVisible INTEGER DEFAULT 0, " +
            "image INTEGER DEFAULT 0" +
            ")";

    // Bảng recent_truyens
    private static final String CREATE_TABLE_RECENT_TRUYENS = "CREATE TABLE " + TABLE_RECENT_TRUYENS + " (" +
            "truyen_id INTEGER PRIMARY KEY, " +
            "last_accessed INTEGER NOT NULL, " +
            "FOREIGN KEY(truyen_id) REFERENCES " + TABLE_TRUYEN + "(id)" +
            ")";

    // Bảng downloaded_truyens
    private static final String CREATE_TABLE_DOWNLOADED_TRUYENS = "CREATE TABLE " + TABLE_DOWNLOADED_TRUYENS + " (" +
            "truyen_id INTEGER PRIMARY KEY, " +
            "FOREIGN KEY(truyen_id) REFERENCES " + TABLE_TRUYEN + "(id)" +
            ")";

    // Bảng subscribed_truyens
    private static final String CREATE_TABLE_SUBSCRIBED_TRUYENS = "CREATE TABLE " + TABLE_SUBSCRIBED_TRUYENS + " (" +
            "truyen_id INTEGER PRIMARY KEY, " +
            "FOREIGN KEY(truyen_id) REFERENCES " + TABLE_TRUYEN + "(id)" +
            ")";

    // Bảng unlocked_truyens
    private static final String CREATE_TABLE_UNLOCKED_TRUYENS = "CREATE TABLE " + TABLE_UNLOCKED_TRUYENS + " (" +
            "truyen_id INTEGER PRIMARY KEY, " +
            "FOREIGN KEY(truyen_id) REFERENCES " + TABLE_TRUYEN + "(id)" +
            ")";

    public TruyenDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRUYEN);
        db.execSQL(CREATE_TABLE_RECENT_TRUYENS);
        db.execSQL(CREATE_TABLE_DOWNLOADED_TRUYENS);
        db.execSQL(CREATE_TABLE_SUBSCRIBED_TRUYENS);
        db.execSQL(CREATE_TABLE_UNLOCKED_TRUYENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Chuyển dữ liệu từ cấu trúc cũ sang cấu trúc mới
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT_TRUYENS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOADED_TRUYENS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBED_TRUYENS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNLOCKED_TRUYENS);
            db.execSQL(CREATE_TABLE_RECENT_TRUYENS);
            db.execSQL(CREATE_TABLE_DOWNLOADED_TRUYENS);
            db.execSQL(CREATE_TABLE_SUBSCRIBED_TRUYENS);
            db.execSQL(CREATE_TABLE_UNLOCKED_TRUYENS);

            // Chuyển dữ liệu từ cột lastAccessed trong bảng truyen (nếu có)
            Cursor cursor = db.rawQuery("SELECT id, lastAccessed FROM " + TABLE_TRUYEN + " WHERE lastAccessed > 0", null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                long lastAccessed = cursor.getLong(1);
                ContentValues values = new ContentValues();
                values.put("truyen_id", id);
                values.put("last_accessed", lastAccessed);
                db.insert(TABLE_RECENT_TRUYENS, null, values);
            }
            cursor.close();

            // Xóa cột lastAccessed từ bảng truyen
            db.execSQL("CREATE TABLE temp_truyen AS SELECT id, ten, trangthai, isChecked, isCheckBoxVisible, image FROM " + TABLE_TRUYEN);
            db.execSQL("DROP TABLE " + TABLE_TRUYEN);
            db.execSQL("ALTER TABLE temp_truyen RENAME TO " + TABLE_TRUYEN);
        }
    }

    public void insertTruyen() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Thêm truyện vào bảng truyen
        values.put("ten", "SpyX Family");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.anhbia1);
        long id1 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Dr.Stone");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.anhbia2);
        long id2 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Winter Moon");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.winter_moon);
        long id3 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Singles Royale");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.singles_royale);
        long id4 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Dark Mermaid");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.dark_mermaid);
        long id5 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Iseop’s Romance");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.iseops_romance);
        long id6 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "My Aggravating Sovereign");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.my_aggravating_sovereign);
        long id7 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Press Play, Sami");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.press_play_sami);
        long id8 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Heart Acres");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.heart_acres);
        long id9 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "The One Who Parried Death");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.the_one_who_parried_death);
        long id10 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Monster Eater");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.monster_eater);
        long id11 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Nebula’s Civilization");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.nebulas_civilization);
        long id12 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "The Witch and The Bull");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.the_witch_and_the_bull);
        long id13 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "What Death Taught Me");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.what_death_taught_me);
        long id14 = db.insert(TABLE_TRUYEN, null, values);

        values.clear();
        values.put("ten", "Phase");
        values.put("trangthai", "Đang đọc");
        values.put("image", R.drawable.phase);
        long id15 = db.insert(TABLE_TRUYEN, null, values);



        // Thêm vào recent_truyens
//        values.clear();
//        values.put("truyen_id", id1);
//        values.put("last_accessed", System.currentTimeMillis() - 1000);
//        db.insert(TABLE_RECENT_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id2);
//        values.put("last_accessed", System.currentTimeMillis() - 5000);
//        db.insert(TABLE_RECENT_TRUYENS, null, values);
//
//        // Thêm vào downloaded_truyens
//        values.clear();
//        values.put("truyen_id", id2);
//        db.insert(TABLE_DOWNLOADED_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id4);
//        db.insert(TABLE_DOWNLOADED_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id5);
//        db.insert(TABLE_DOWNLOADED_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id6);
//        db.insert(TABLE_DOWNLOADED_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id3);
//        db.insert(TABLE_DOWNLOADED_TRUYENS, null, values);
//
//        // Thêm vào subscribed_truyens và unlocked_truyens
//        values.clear();
//        values.put("truyen_id", id2);
//        db.insert(TABLE_SUBSCRIBED_TRUYENS, null, values);
//
//        values.clear();
//        values.put("truyen_id", id3);
//        db.insert(TABLE_UNLOCKED_TRUYENS, null, values);

        db.close();
    }

    public void updateLastAccessed(int truyenId, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("truyen_id", truyenId);
        values.put("last_accessed", timestamp);

        // Kiểm tra xem truyen_id đã tồn tại trong recent_truyens chưa
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECENT_TRUYENS + " WHERE truyen_id = ?",
                new String[]{String.valueOf(truyenId)});
        if (cursor.moveToFirst()) {
            // Cập nhật nếu đã tồn tại
            db.update(TABLE_RECENT_TRUYENS, values, "truyen_id = ?", new String[]{String.valueOf(truyenId)});
        } else {
            // Thêm mới nếu chưa tồn tại
            db.insert(TABLE_RECENT_TRUYENS, null, values);
        }
        cursor.close();

        // Giới hạn số lượng truyện truy cập gần đây
//        cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_RECENT_TRUYENS, null);
//        if (cursor.moveToFirst()) {
//            int count = cursor.getInt(0);
//            if (count > MAX_RECENT_TRUYENS) {
//                db.execSQL("DELETE FROM " + TABLE_RECENT_TRUYENS + " WHERE truyen_id IN (" +
//                                "SELECT truyen_id FROM " + TABLE_RECENT_TRUYENS + " " +
//                                "ORDER BY last_accessed ASC LIMIT ?)",
//                        new String[]{String.valueOf(count - MAX_RECENT_TRUYENS)});
//            }
//        }
        cursor.close();
        db.close();
    }

    public boolean removeFromRecent(int truyenId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_RECENT_TRUYENS, "truyen_id = ?", new String[]{String.valueOf(truyenId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean removeFromDownloads(int truyenId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_DOWNLOADED_TRUYENS, "truyen_id = ?", new String[]{String.valueOf(truyenId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean removeFromSubscribed(int truyenId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_SUBSCRIBED_TRUYENS, "truyen_id = ?", new String[]{String.valueOf(truyenId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean removeFromUnlocked(int truyenId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_UNLOCKED_TRUYENS, "truyen_id = ?", new String[]{String.valueOf(truyenId)});
        db.close();
        return rowsAffected > 0;
    }

    public List<MySeries> getRecentTruyen() {
        List<MySeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT t.* FROM " + TABLE_TRUYEN + " t " +
                        "INNER JOIN " + TABLE_RECENT_TRUYENS + " rt ON t.id = rt.truyen_id " +
                        "ORDER BY rt.last_accessed DESC LIMIT ?",
                new String[]{String.valueOf(MAX_RECENT_TRUYENS)});

        while (cursor.moveToNext()) {
            MySeries series = new MySeries(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            series.setImage(cursor.getInt(5));
            list.add(series);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<MySeries> getDownloadedTruyen() {
        List<MySeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT t.* FROM " + TABLE_TRUYEN + " t " +
                "INNER JOIN " + TABLE_DOWNLOADED_TRUYENS + " dt ON t.id = dt.truyen_id", null);

        while (cursor.moveToNext()) {
            MySeries series = new MySeries(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            series.setImage(cursor.getInt(5));
            list.add(series);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<MySeries> getSubscribedTruyen() {
        List<MySeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT t.* FROM " + TABLE_TRUYEN + " t " +
                "INNER JOIN " + TABLE_SUBSCRIBED_TRUYENS + " st ON t.id = st.truyen_id", null);

        while (cursor.moveToNext()) {
            MySeries series = new MySeries(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            series.setImage(cursor.getInt(5));
            list.add(series);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<MySeries> getUnlockedTruyen() {
        List<MySeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT t.* FROM " + TABLE_TRUYEN + " t " +
                "INNER JOIN " + TABLE_UNLOCKED_TRUYENS + " ut ON t.id = ut.truyen_id", null);

        while (cursor.moveToNext()) {
            MySeries series = new MySeries(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            series.setImage(cursor.getInt(5));
            list.add(series);
        }
        cursor.close();
        db.close();
        return list;
    }


    public boolean deleteTruyen(int id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(tableName,  "truyen_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
    public List<MySeries> getAllTruyen() {
        List<MySeries> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRUYEN, null);

        while (cursor.moveToNext()) {
            MySeries series = new MySeries(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ten")),
                    cursor.getString(cursor.getColumnIndexOrThrow("trangthai"))
            );
            series.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow("isChecked")) == 1);
            series.setCheckBoxVisible(cursor.getInt(cursor.getColumnIndexOrThrow("isCheckBoxVisible")) == 1);
            series.setImage(cursor.getInt(cursor.getColumnIndexOrThrow("image")));
            list.add(series);
        }
        cursor.close();
        db.close();
        return list;
    }
    private boolean isTruyenExists(String tableName, int truyenId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + tableName + " WHERE truyen_id = ?", new String[]{String.valueOf(truyenId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public void addRecentTruyen(int truyenId) {
        updateLastAccessed(truyenId, System.currentTimeMillis());
    }
    public void addDownloadedTruyen(int truyenId) {
        if (!isTruyenExists("downloaded_truyens", truyenId)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("truyen_id", truyenId);
            db.insert("downloaded_truyens", null, values);
            db.close();
        }
    }

    public void addSubscribedTruyen(int truyenId) {
        if (!isTruyenExists("subscribed_truyens", truyenId)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("truyen_id", truyenId);
            db.insert("subscribed_truyens", null, values);
            db.close();
        }
    }

    public void addUnlockedTruyen(int truyenId) {
        if (!isTruyenExists("unlocked_truyens", truyenId)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("truyen_id", truyenId);
            db.insert("unlocked_truyens", null, values);
            db.close();
        }
    }

}