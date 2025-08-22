package model;

import java.io.Serializable;

public class ListData implements Serializable {

    // ListData id, unique around listData
    private int id;
    // row indicate in which postion the list should show horizontally
    private int row;
    // Summary of the list
    private String summary;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
