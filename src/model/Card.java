package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Card implements Serializable {

    // Card id, unique around the cards
    private int id;
    // row indicate list position where card should show
    private int row;
    // column indicate in which position the card should show in the list
    private int column;
    // Summary of the card
    private String summary;
    // Description of the card
    private String description;
    // Due date of the card
    private LocalDate dueDate;
    
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

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
