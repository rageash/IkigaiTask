package repository;

import java.io.FileNotFoundException;
import java.util.List;

import config.AppConfig;
import datastore.DataStore;
import exception.FileReadWriteException;
import exception.ObjectNotFoundException;
import model.Board;
import model.Card;
import model.ListData;

/**
 * To Add, Update, and Delete board or listdata or card
 * 
 * The file structure will be like
 * 
 * Folders:
 * saves/boards - for storing all boards
 * saves/listdata - for storing all the listdata
 * saves/cards - for storing all the cards
 * 
 * listdata or card file name will be the board id for easily retrieving the data
 */
public class DataRepo {
    
    private DataStore dataStore = null;
    private AppConfig.BuildConfig appConfig = null;

    private static final String FILE_SAVE_PATH = "\\saves\\";

    private static final String BOARD_PATH = FILE_SAVE_PATH + "board";
    private static final String LISTDATA_PATH = FILE_SAVE_PATH + "listdata";
    private static final String CARD_PATH = FILE_SAVE_PATH + "card";

    public DataRepo(AppConfig.BuildConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Create/Update board
     * @throws FileReadWriteException 
     * @throws FileNotFoundException 
     */
    public boolean createBoard(List<Board> board) throws FileNotFoundException, FileReadWriteException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + BOARD_PATH);
        DataStore.setFilePath(appConfig.getDataFile());
        dataStore = DataStore.getInstance();
        return dataStore.writeData(board);
    }

    /**
     * Read board
     * @throws ObjectNotFoundException 
     * @throws FileReadWriteException 
     */
    public List<Board> getBoards() throws FileReadWriteException, ObjectNotFoundException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + BOARD_PATH);
        DataStore.setFilePath(appConfig.getDataFile());
        dataStore = DataStore.getInstance();
        return (List<Board>) dataStore.readData();
    }

    /**
     * Create/Update listdatas
     * @throws FileReadWriteException 
     * @throws FileNotFoundException 
     */
    public boolean createListData(String fileName, List<ListData> listdatas) throws FileNotFoundException, FileReadWriteException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + LISTDATA_PATH);
        DataStore.setFilePath(fileName);
        dataStore = DataStore.getInstance();
        return dataStore.writeData(listdatas);
    }

    /**
     * Read Listdatas
     * @throws ObjectNotFoundException 
     * @throws FileReadWriteException 
     */
    public List<ListData> getListDatas(String filename) throws FileReadWriteException, ObjectNotFoundException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + LISTDATA_PATH);
        DataStore.setFilePath(filename);
        dataStore = DataStore.getInstance();
        return (List<ListData>) dataStore.readData();
    }

    /**
     * Create/Update Card
     * @throws FileReadWriteException 
     * @throws FileNotFoundException 
     */
    public boolean createCard(String fileName, List<Card> cards) throws FileNotFoundException, FileReadWriteException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + CARD_PATH);
        DataStore.setFilePath(fileName);
        dataStore = DataStore.getInstance();
        return dataStore.writeData(cards);
    }

    /**
     * Read Cards
     * @throws ObjectNotFoundException 
     * @throws FileReadWriteException 
     */
    public List<Card> getCards(String filename) throws FileReadWriteException, ObjectNotFoundException {
        DataStore.setDirPath(appConfig.getDataFileLocation() + CARD_PATH);
        DataStore.setFilePath(filename);
        dataStore = DataStore.getInstance();
        return (List<Card>) dataStore.readData();
    }
}
