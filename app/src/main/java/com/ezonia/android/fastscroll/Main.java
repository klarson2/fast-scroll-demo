package com.ezonia.android.fastscroll;

public class Main {
    public Main() {
    }

    private String continent;
    private String description;
    private boolean selected;

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int _id;

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}