package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Board implements Serializable {
    // Board id unique around boards
    private int id;
    // Name of the board
    private String boardName;
    // Description of the board
    private String description;
    // Creation time of the board
    private LocalDateTime createdDateTime;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setDescription(String description) {
        this. description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
}
