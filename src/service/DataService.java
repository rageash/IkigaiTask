package service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import callback.ViewUpdate;
import exception.FileReadWriteException;
import model.Board;
import model.Card;
import model.ListData;
import repository.DataRepo;
import resource.Strings;

/**
 * Service call to handle the data create/update and read
 */
public class DataService {
    
    // Data read/write repo
    private DataRepo dataRepo;

    // Listener to Update the UI
    private ViewUpdate viewUpdate;

    public DataService(DataRepo dataRepo, ViewUpdate viewUpdate) {
        this.dataRepo = dataRepo;
        this.viewUpdate = viewUpdate;
    }

    /**
     * Get all the boards
     * 
     * Listen to ViewUpdate.onBoardDataUpdate(boards) to retrieve the list of boards
     */
    public void getBoards() {
        RequestQueue.getInstance().addTask(() -> {
            List<Board> boards = new ArrayList<>();

            try {
                // Using datarepo to retrieve the boards
                // if board is not available will throw an exception, don't stop the code flow.
                boards = dataRepo.getBoards();
            } catch (Exception e) {
                // Callback to update the UI
                viewUpdate.onDataRequestFailed(Strings.NO_BOARD_FOUND);
            }

            // Callback to send data to the UI
            viewUpdate.onBoardDataUpdate(boards);
        });
    }

    /**
     * Get all the listDatas
     * 
     * Listen to ViewUpdate.onListDataUpdate(listData) to retrieve the list of ListData
     */
    public void getListDatas(int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<ListData> listDatas = new ArrayList<>();

            try {
                // Using datarepo to retrieve the boards
                // if board is not available will throw an exception, don't stop the code flow.
                listDatas = dataRepo.getListDatas(String.valueOf(boardId));
            } catch (Exception e) {
                // Callback to update the UI
                viewUpdate.onDataRequestFailed(Strings.NO_LIST_FOUND);
            }

            // Callback to send data to the UI
            viewUpdate.onListDataUpdate(listDatas);
        });
    }

    /**
     * Get all the cards
     * 
     * Listen to ViewUpdate.onCardDataUpdate(cards) to retrieve the list of cards
     */
    public void getCards(int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<Card> cards = new ArrayList<>();

            try {
                // Using datarepo to retrieve the boards
                // if board is not available will throw an exception, don't stop the code flow.
                cards = dataRepo.getCards(String.valueOf(boardId));
            } catch (Exception e) {
                // Callback to update the UI
                viewUpdate.onDataRequestFailed(Strings.NO_CARD_FOUND);
            }

            // Callback to send data to the UI
            viewUpdate.onCardDataUpdate(cards);
        });
    }

    /**
     * Silent method to fetch list of board
     * 
     * This is the helper method for Create and Update method 
     * @return list of boards
     */
    private List<Board> fetchBoards() {
        List<Board> boards = null;
        try {
            boards = dataRepo.getBoards();
        } catch (Exception e) {
            // Avoid throwing exception or displaying in the UI
            // This helper method will be used across method
            System.out.println(Strings.NO_BOARD_AVAILABLE);
        }

        // If boards is null, most likely file is not created
        // Assign empty array list
        if (boards == null) {
            boards = new ArrayList<>();
        }

        return boards;
    }

    /**
     * Silent method to fetch list of listData
     * 
     * This is the helper method for Create and Update method 
     * @return list of listData
     */
    private List<ListData> fetchListData(int boardId) {
        List<ListData> listDatas = null;
        try {
            listDatas = dataRepo.getListDatas(String.valueOf(boardId));
        } catch (Exception e) {
            // Avoid throwing exception or displaying in the UI
            // This helper method will be used across method
            System.out.println(Strings.NO_LIST_AVAILABLE);
        }

        // If listDatas is null, most likely file is not created
        // Assign empty array list
        if (listDatas == null) {
            listDatas = new ArrayList<>();
        }

        return listDatas;
    }

    /**
     * Silent method to fetch list of cards
     * 
     * This is the helper method for Create and Update method 
     * @return list of cards
     */
    private List<Card> fetchCards(int boardId) {
        List<Card> cards = null;
        try {
            cards = dataRepo.getCards(String.valueOf(boardId));
        } catch (Exception e) {
            // Avoid throwing exception or displaying in the UI
            // This helper method will be used across method
            System.out.println(Strings.NO_CARD_AVAILABLE);
        }

        // If cards is null, most likely file is not created
        // Assign empty array list
        if (cards == null) {
            cards = new ArrayList<>();
        }

        return cards;
    }

    /**
     * Create new board
     * 
     * Listen to @Link{ViewUpdate.onBoardWriteComplete(message, boards)} to retrieve the list of updated boards
     * @param board
     */
    public void createBoard(Board board) {
        RequestQueue.getInstance().addTask(() -> {
            List<Board> boards = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidBoard(board)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_BOARD_NAME);
                    return;
                }

                // Get available boards
                boards = fetchBoards();

                int maxId = 0;
                for (Board boarddata: boards) {
                    // Retrieve the max board id
                    maxId = Math.max(maxId, boarddata.getId());

                    // Check if board name already exists
                    if (board.getBoardName().toLowerCase().equals(boarddata.getBoardName().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.BOARD_NAME_EXISTS);
                        return;
                    }
                }
                maxId++;

                // Assign id and board create date
                board.setId(maxId);
                board.setCreatedDateTime(LocalDateTime.now());

                // Add the new board to the board list
                boards.add(board);

                // Update the board in the repo
                dataRepo.createBoard(boards);

                // Send the update board list to UI
                viewUpdate.onBoardWriteComplete(Strings.BOARD_CREATE_SUCCESSFUL, boards);

            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }

    /**
     * Validate board required fields are not empty
     */
    private boolean isValidBoard(Board board) {
        return board != null 
        && board.getBoardName() != null 
        && board.getBoardName().length() > 0;
    }

    /**
     * Update the existing board
     * 
     * Listen to @Link{ViewUpdate.onBoardWriteComplete(message, boards)} to retrieve the list of updated boards
     * @param board
     */
    public void updateBoard(Board board) {
        RequestQueue.getInstance().addTask(() -> {
            List<Board> boards = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidBoard(board)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_BOARD_NAME);
                    return;
                }

                // Get available boards
                boards = fetchBoards();

                for (Board boardData: boards) {
                    // Verify the entered board doesn't exist
                    if (boardData.getId() != board.getId() 
                        && board.getBoardName().toLowerCase().equals(boardData.getBoardName().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.BOARD_NAME_EXISTS);
                        return;
                    } else if (board.getId() == boardData.getId()) {
                        // Copy the updated data to existing board data
                        boardData.setBoardName(board.getBoardName());
                        boardData.setDescription(board.getDescription());
                    }
                }

                // Update the board in the repo
                dataRepo.createBoard(boards);

                // Send the update board list to UI
                viewUpdate.onBoardWriteComplete(Strings.BOARD_UPDATE_SUCCESSFUL, boards);

            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }
    
    /**
     * Create new List
     * 
     * Listen to @Link{ViewUpdate.onListDataWriteComplete(message, listDatas)} to retrieve the list of updated listDatas
     * @param listData
     * @param boardId
     */
    public void createListData(ListData listData, int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<ListData> listDatas = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidListData(listData)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_LIST_SUMMARY);
                    return;
                }

                // Get the list data
                listDatas = fetchListData(boardId);

                int maxId = 0;
                for (ListData data: listDatas) {
                    // Retrieve the max list id
                    maxId = Math.max(maxId, data.getId());

                    // Check if the list name already exists
                    if (listData.getSummary().toLowerCase().equals(data.getSummary().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.LIST_SUMMARY_EXISTS);
                        return;
                    }
                }
                maxId++;

                // Assign id to the list
                listData.setId(maxId);
                
                // Add the new listData to the listData list
                listDatas.add(listData);

                // Update the listData in the repo
                dataRepo.createListData(String.valueOf(boardId), listDatas);

                // Send the update listData list to UI
                viewUpdate.onListDataWriteComplete(Strings.LIST_CREATE_SUCCESSFUL, listDatas);
            
            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }

    /**
     * Validate listdata required fields are not empty
     */
    private boolean isValidListData(ListData listData) {
        return listData != null 
        && listData.getSummary() != null 
        && listData.getSummary().length() > 0;
    }

    /**
     * Update the existing list data
     * 
     * Listen to @Link{ViewUpdate.onListDataWriteComplete(message, listDatas)} to retrieve the list of updated listDatas
     * @param listData
     * @param boardId
     */
    public void updateListData(ListData listData, int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<ListData> listDatas = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidListData(listData)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_LIST_SUMMARY);
                    return;
                }

                // Get the list data
                listDatas = fetchListData(boardId);

                for (ListData data: listDatas) {

                    // Verify entered list data doesn't exists
                    if (data.getId() != listData.getId() 
                        && data.getSummary().toLowerCase().equals(listData.getSummary().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.LIST_SUMMARY_EXISTS);
                        return;

                    } else if (data.getId() == listData.getId()) {
                        // Copy the updated data to existing list data
                        data.setRow(listData.getRow());
                        data.setSummary(listData.getSummary());
                    }
                }

                // Update the listData in the repo
                dataRepo.createListData(String.valueOf(boardId), listDatas);

                // Send the update listData list to UI
                viewUpdate.onListDataWriteComplete(Strings.LIST_UPDATE_SUCCESSFUL, listDatas);
            
            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }

    /**
     * Create new Card
     * 
     * Listen to @Link{ViewUpdate.onCardWriteComplete(message, cards)} to retrieve the list of updated cards
     * @param card
     * @param boardId
     */
    public void createCard(Card card, int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<Card> cards = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidCard(card)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_CARD_SUMMARY);
                    return;
                }

                // Get all cards
                cards = fetchCards(boardId);

                int maxId = 0;
                for (Card data: cards) {
                    // Retrieve the max card id
                    maxId = Math.max(maxId, data.getId());

                    // Check if the card summary already exists
                    if (card.getSummary().toLowerCase().equals(data.getSummary().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.CARD_SUMMARY_EXISTS);
                        return;
                    }
                }
                maxId++;

                // Assign id to the card
                card.setId(maxId);
                
                // Add the new card to the cards list
                cards.add(card);

                // Update the card in the repo
                dataRepo.createCard(String.valueOf(boardId), cards);

                // Send the update card list to UI
                viewUpdate.onCardWriteComplete(Strings.CARD_CREATE_SUCCESSFUL, cards);

            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }

    /**
     * Validate card required field are not empty
     */
    private boolean isValidCard(Card card) {
        return card != null 
            && card.getSummary() != null
            && card.getSummary().length() > 0;
    }

    /**
     * Update the existing Card
     * 
     * Listen to @Link{ViewUpdate.onCardWriteComplete(message, cards)} to retrieve the list of updated cards
     * @param card
     * @param boardId
     */
    public void updateCard(Card card, int boardId) {
        RequestQueue.getInstance().addTask(() -> {
            List<Card> cards = new ArrayList<>();

            try {
                // Ensure required fields for filled up
                if (!isValidCard(card)) {
                    viewUpdate.onDataRequestFailed(Strings.INVALID_CARD_SUMMARY);
                    return;
                }

                // Get the card data
                cards = fetchCards(boardId);

                for (Card data: cards) {

                    // Verify entered card doesn't exists
                    if (data.getId() != card.getId() 
                        && data.getSummary().toLowerCase().equals(card.getSummary().toLowerCase())) {
                        viewUpdate.onDataRequestFailed(Strings.CARD_SUMMARY_EXISTS);
                        return;

                    } else if (data.getId() == card.getId()) {
                        // Copy the updated data to existing card
                        data.setRow(card.getRow());
                        data.setColumn(card.getColumn());
                        data.setSummary(card.getSummary());
                        data.setDescription(card.getDescription());
                        data.setDueDate(card.getDueDate());
                    }
                }

                // Update the card in the repo
                dataRepo.createCard(String.valueOf(boardId), cards);

                // Send the update card list to UI
                viewUpdate.onCardWriteComplete(Strings.CARD_UPDATE_SUCCESSFUL, cards);

            } catch (FileReadWriteException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_READ_FILE);
                return;
            } catch (FileNotFoundException e) {
                // Callback to Update the UI
                viewUpdate.onDataRequestFailed(Strings.CANNOT_LOCATE_FILE);
                return;
            }
        });
    }
}