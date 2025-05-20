package com.example.finalassigment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TruyenDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "truyendb";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "truyen";
    private static final int MAX_RECENT_TRUYENS = 20;

    public TruyenDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE truyen (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "trangthai TEXT, " +
                "isChecked INTEGER DEFAULT 0, " +
                "isCheckBoxVisible INTEGER DEFAULT 0, " +
                "lastAccessed LONG DEFAULT 0, " +
                "image INTEGER DEFAULT 0)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS truyen");
        onCreate(db);
    }

    public ArrayList<Series> getAllTruyen() {
        ArrayList<Series> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM truyen", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String trangthai = cursor.getString(2);
            boolean isChecked = cursor.getInt(3) == 1;
            boolean isCheckBoxVisible = cursor.getInt(4) == 1;
            long lastAccessed = cursor.getLong(5);
            int image = cursor.getInt(6);
            Series series = new Series(id, ten, trangthai);
            series.setChecked(isChecked);
            series.setCheckBoxVisible(isCheckBoxVisible);
            series.setLastAccessed(lastAccessed);
            series.setImage(image);
            list.add(series);
        }

        cursor.close();
        return list;
    }

    public ArrayList<Series> getHaiTruyenDauTien() {
        ArrayList<Series> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM truyen LIMIT 2", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String trangthai = cursor.getString(2);
            boolean isChecked = cursor.getInt(3) == 1;
            boolean isCheckBoxVisible = cursor.getInt(4) == 1;
            long lastAccessed = cursor.getLong(5);
            int image = cursor.getInt(6);
            Series series = new Series(id, ten, trangthai);
            series.setChecked(isChecked);
            series.setCheckBoxVisible(isCheckBoxVisible);
            series.setLastAccessed(lastAccessed);
            series.setImage(image);
            list.add(series);
        }
        cursor.close();
        return list;
    }

    public ArrayList<Series> getRecentTruyen() {
        ArrayList<Series> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM truyen WHERE lastAccessed > 0 ORDER BY lastAccessed DESC LIMIT ?",
                new String[]{String.valueOf(MAX_RECENT_TRUYENS)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String trangthai = cursor.getString(2);
            boolean isChecked = cursor.getInt(3) == 1;
            boolean isCheckBoxVisible = cursor.getInt(4) == 1;
            long lastAccessed = cursor.getLong(5);
            int image = cursor.getInt(6);
            Series series = new Series(id, ten, trangthai);
            series.setChecked(isChecked);
            series.setCheckBoxVisible(isCheckBoxVisible);
            series.setLastAccessed(lastAccessed);
            series.setImage(image);
            list.add(series);
        }

        cursor.close();
        return list;
    }

    public boolean deleteTruyen(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public void updateLastAccessed(int id, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cập nhật lastAccessed cho truyện
        ContentValues values = new ContentValues();
        values.put("lastAccessed", timestamp);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        // Kiểm tra số lượng truyện có lastAccessed > 0
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE lastAccessed > 0", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > MAX_RECENT_TRUYENS) {
                // Xóa các truyện xa nhất
                db.execSQL("UPDATE " + TABLE_NAME + " SET lastAccessed = 0 WHERE id IN (" +
                                "SELECT id FROM " + TABLE_NAME + " WHERE lastAccessed > 0 " +
                                "ORDER BY lastAccessed ASC LIMIT ?)",
                        new String[]{String.valueOf(count - MAX_RECENT_TRUYENS)});
            }
        }
        cursor.close();
    }

    public void insertTruyen() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put("ten", "Dế Mèn Phiêu Lưu Ký");
        values1.put("trangthai", "Đã đọc");
        values1.put("isChecked", 0);
        values1.put("isCheckBoxVisible", 0);
        values1.put("lastAccessed", 0);
        values1.put("image", R.drawable.anhbongbong);
        db.insert(TABLE_NAME, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("ten", "Tắt Đèn");
        values2.put("trangthai", "Chưa đọc");
        values2.put("isChecked", 0);
        values2.put("isCheckBoxVisible", 0);
        values2.put("lastAccessed", 0);
        values2.put("image", R.drawable.anhcay);
        db.insert(TABLE_NAME, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("ten", "Chí Phèo");
        values3.put("trangthai", "Đang đọc");
        values3.put("isChecked", 0);
        values3.put("isCheckBoxVisible", 0);
        values3.put("lastAccessed", 0);
        values3.put("image", R.drawable.dacvu);
        db.insert(TABLE_NAME, null, values3);

        ContentValues values4 = new ContentValues();
        values4.put("ten", "Doraemon");
        values4.put("trangthai", "Đã đọc");
        values4.put("isChecked", 0);
        values4.put("isCheckBoxVisible", 0);
        values4.put("lastAccessed", 0);
        values4.put("image", R.drawable.doraemon);
        db.insert(TABLE_NAME, null, values4);

        ContentValues values5 = new ContentValues();
        values5.put("ten", "Người con gái nam xương");
        values5.put("trangthai", "Đã đọc");
        values5.put("isChecked", 0);
        values5.put("isCheckBoxVisible", 0);
        values5.put("lastAccessed", 0);
        values5.put("image", R.drawable.ti_xung);
        db.insert(TABLE_NAME, null, values5);

        ContentValues values6 = new ContentValues();
        values6.put("ten", "Cuộc phiêu lưu kì thú");
        values6.put("trangthai", "Đã đọc");
        values6.put("isChecked", 0);
        values6.put("isCheckBoxVisible", 0);
        values6.put("lastAccessed", 0);
        values6.put("image", R.drawable.anhcay);
        db.insert(TABLE_NAME, null, values6);

        ContentValues values7 = new ContentValues();
        values7.put("ten", "Hành trình trở thành ma vương");
        values7.put("trangthai", "Đã đọc");
        values7.put("isChecked", 0);
        values7.put("isCheckBoxVisible", 0);
        values7.put("lastAccessed", 0);
        values7.put("image", R.drawable.congnghe);
        db.insert(TABLE_NAME, null, values7);

        ContentValues values8 = new ContentValues();
        values8.put("ten", "Ta là bất khả chiến bại");
        values8.put("trangthai", "Đã đọc");
        values8.put("isChecked", 0);
        values8.put("isCheckBoxVisible", 0);
        values8.put("lastAccessed", 0);
        values8.put("image", R.drawable.goku);
        db.insert(TABLE_NAME, null, values8);

        ContentValues values9 = new ContentValues();
        values9.put("ten", "Bố tôi là đặc vụ");
        values9.put("trangthai", "Đã đọc");
        values9.put("isChecked", 0);
        values9.put("isCheckBoxVisible", 0);
        values9.put("lastAccessed", 0);
        values9.put("image", R.drawable.chim);
        db.insert(TABLE_NAME, null, values9);
        android.util.Log.d("TruyenDatabaseHelper", "Đã chèn 9 truyện mẫu");
    }
}