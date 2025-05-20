package com.example.finalassigment;

import java.io.Serializable;

public class SeriesModel implements Serializable {
    private final int id;
    private final int image;
    private final String name;
    private final String category;
    private final String likes;
    private final String summary;
    private final int rank;
    private final String rankChange;

    public SeriesModel(int id, int image, String name, String category, String likes, String summary, int rank, String rankChange) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.category = category;
        this.likes = likes;
        this.summary = summary;
        this.rank = rank;
        this.rankChange = rankChange;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getLikes() {
        return likes;
    }

    public String getSummary() {
        return summary;
    }

    public int getRank() {
        return rank;
    }

    public String getRankChange() {
        return rankChange;
    }
}
