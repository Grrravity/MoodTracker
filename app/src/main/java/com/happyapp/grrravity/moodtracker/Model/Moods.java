package com.happyapp.grrravity.moodtracker.model;

public class Moods {
    private String name;
    private int drawableId;
    private int colorId;
    private int index;
    private String comment;
    private String date;

    public Moods(String name, int drawableId, int colorId, int index, String comment) {
        this.name = name;
        this.drawableId = drawableId;
        this.colorId = colorId;
        this.index = index;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "Moods{"+
                "name ='" + name + '\'' +
                ", drawableId = " + drawableId +
                ", colorId = " + colorId +
                ", index = " + index +
                ", comment" + comment +
                ", dateInMs" + date +
                "}";
    }
}
