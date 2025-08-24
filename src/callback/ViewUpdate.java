package callback;

import java.util.List;

import model.Board;
import model.Card;
import model.ListData;

public interface ViewUpdate {

    /**
     * Callback for returning list of Boards
     * 
     * Call to @Link{DataService.getBoards()}
     * @param boards
     */
    void onBoardDataUpdate(List<Board> boards);

    /**
     * Callback for returning list of ListDatas
     * 
     * Call to @Link{DataService.getListDatas(boardId)}
     * @param listDatas
     */
    void onListDataUpdate(List<ListData> listDatas);

    /**
     * Callback for returning list of cards
     * 
     * Call to @Link{DataService.getCards(boardId)}
     * @param cards
     */
    void onCardDataUpdate(List<Card> cards);
    
    /**
     * Callback for updating the board, and retrieve updated boards list
     * 
     * Call to @Link{DataService.createBoard(board)}
     * Call to @Link{DataService.updateBoard(board)}
     * @param message
     * @param boards
     */
    void onBoardWriteComplete(String message, List<Board> boards);

    /**
     * Callback for update the ListData, and retrieve the updated ListData list
     * 
     * Call to @Link{DataService.createListData(listdata, boardId)}
     * Call to @Link{DataService.updateListData(listdata, boardId)}
     * @param message
     * @param listDatas
     */
    void onListDataWriteComplete(String message, List<ListData> listDatas);

    /**
     * Callback for update the Card, and retrieve the updated Card list
     * 
     * Call to @Link{DataService.createCard(card, boardId)}
     * Call to @Link{DataService.updateCard(card, boardId)}
     * @param message
     * @param cards
     */
    void onCardWriteComplete(String message, List<Card> cards);

    /**
     * Request failure message will be passed from this callback
     * @param message
     */
    void onDataRequestFailed(String message);
}
