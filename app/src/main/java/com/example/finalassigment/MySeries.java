package com.example.finalassigment;

import java.io.Serializable;

public class MySeries implements Serializable {
    private int id;
    private String tenTruyen;
    private String trangThai;
    private int image;
    private boolean isChecked = false;
    private boolean isCheckBoxVisible = false;
    private long lastAccessed = 0;

    public MySeries(int id, String tenTruyen, String trangThai) {
        this.id = id;
        this.tenTruyen = (tenTruyen != null) ? tenTruyen : ""; // Thêm kiểm tra null
        this.trangThai = (trangThai != null) ? trangThai : ""; // Thêm kiểm tra null
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
        this.tenTruyen = (tenTruyen != null) ? tenTruyen : "";
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = (trangThai != null) ? trangThai : "";
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