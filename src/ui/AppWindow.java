package ui;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import config.AppConfig;
import exception.FileReadWriteException;
import exception.ObjectNotFoundException;
import model.Board;
import model.Card;
import model.ListData;
import repository.DataRepo;

public class AppWindow {
    private AppConfig.BuildConfig buildConfig;

    // Master Frame
    private JFrame frame;

    public AppWindow(AppConfig.BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
        initialize();
    }

    private void initialize() {
        // initialize master ui
        frame = new JFrame(buildConfig.getTitle());
        frame.setSize(buildConfig.getWidth(), buildConfig.getHeight());
        frame.getContentPane().setBackground(buildConfig.getBackgroundColor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        DataRepo dataRepo = new DataRepo(buildConfig);
        
        Board board1 = new Board();
        board1.setId(1);
        board1.setBoardName("test1");
        board1.setDescription("Atleast I created the board");
        board1.setCreatedDateTime(LocalDateTime.now());

        Board board2 = new Board();
        board2.setId(2);
        board2.setBoardName("test2");
        board2.setDescription("Atleast I created the second board");
        board2.setCreatedDateTime(LocalDateTime.now());

        ListData listData1 = new ListData();
        listData1.setId(1);
        listData1.setRow(0);
        listData1.setSummary("list 1");
        
        ListData listData2 = new ListData();
        listData2.setId(2);
        listData2.setRow(0);
        listData2.setSummary("list 2");

        Card card = new Card();
        card.setId(1);
        card.setColumn(0);
        card.setRow(0);
        card.setDescription("New card 1 is created");
        card.setSummary("Added card 1");
        card.setDescription("Added card description");

        Card card2 = new Card();
        card2.setId(2);
        card2.setColumn(1);
        card2.setRow(1);
        card2.setDescription("New card 2 is created");
        card2.setSummary("Added card 2");
        card2.setDescription("Added card2 description");

        try {
            dataRepo.createBoard(Arrays.asList(board1, board2));
            dataRepo.createListData("1", Arrays.asList(listData1));
            dataRepo.createListData("2", Arrays.asList(listData2));
            dataRepo.createCard("1", Arrays.asList(card));
            dataRepo.createCard("1", Arrays.asList(card, card2));
            List<Card> cards = dataRepo.getCards("1");
            for (Card cardd: cards) {
                System.out.println(cardd.getSummary());
            }
        } catch (FileNotFoundException | FileReadWriteException | ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
