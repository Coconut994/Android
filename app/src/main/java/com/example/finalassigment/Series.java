package com.example.finalassigment;

public class Series {
    private int id;
    private String tenTruyen;
    private String trangThai;
    private boolean isChecked = false;
    private int image;
    private boolean isCheckBoxVisible = false;
    private long lastAccessed = 0;

    public Series(int id, String tenTruyen, String trangThai) {
        this.id = id;
        this.tenTruyen = tenTruyen;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isCheckBoxVisible() {
        return isCheckBoxVisible;
    }

    public void setCheckBoxVisible(boolean checkBoxVisible) {
        isCheckBoxVisible = checkBoxVisible;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}