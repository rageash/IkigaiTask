package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import callback.ViewUpdate;
import config.AppConfig;
import model.Board;
import model.Card;
import model.ListData;
import repository.DataRepo;
import resource.Dimens;
import resource.Strings;
import service.DataService;
import ui.views.MultilineLabel;

/**
 * Landing screen for the application. 
 * This will container the all created board, and create board option button.
 * 
 * On creating an new board, screen will navigate to the next screen
 */
public class LandingPage extends JPanel {
    
    private final static int BOARD_CARD_WIDTH = 240;
    private final static int BOARD_CARD_HEIGHT = 170;
    private final static int SPACING = 10;

    private final static int CREATE_BUTTON_WIDTH = 150;
    private final static int CREATE_BUTTON_HEIGHT = 50;
    private final static int CREATE_BUTTON_RIGHT_PADDING = 30;
    private final static int CREATE_BUTTON_BOTTOM_PADDING = 20;

    private AppConfig.BuildConfig appConfig;
    private DataService dataService;

    private int layoutType = 0;
    private int occupiedSpace;

    private JLabel title;
    private JLabel typo;
    private JPanel boardPanel;
    private JScrollPane scrollPane;
    private JButton button;
    private JLayeredPane layeredPane;

    private int noOfBoards = 0;

    public LandingPage(AppConfig.BuildConfig appConfig) {
        this.appConfig = appConfig;

        this.dataService = new DataService(new DataRepo(appConfig), viewUpdate);

        // Initialize the UI
        initialize();
    }

    private void initialize() {
        // Define the Style for the Landing window
        setSize(appConfig.getWidth(), appConfig.getHeight());
        setBackground(appConfig.getTheme().getLayerZeroColor());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title of the application
        title = new JLabel(appConfig.getTitle());
        Font font = new Font(title.getFont().getName(), Font.BOLD, Dimens.FONT_32);
        title.setFont(font);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(Dimens.DIMEN_20, 0, Dimens.DIMEN_10, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setSize(new Dimension(getWidth(), Dimens.DIMEN_100));
        add(title);

        // Typo for the applciation
        typo = new JLabel(Strings.SLOGAN);
        typo.setHorizontalAlignment(SwingConstants.CENTER);
        typo.setAlignmentX(Component.CENTER_ALIGNMENT);
        typo.setSize(new Dimension(getWidth(), Dimens.DIMEN_50));
        add(typo);

        // Calculated occupied space of the title and type
        occupiedSpace = title.getHeight() + typo.getHeight();

        // Show board list layout based on the layout type
        // Currently only one layout
        switch (layoutType) {
            default:
                floatingButtonLayout();
                break;
        }

        // Fetch the board data
        fetchBoards();
    }

    private void floatingButtonLayout() {
        // Layering used to show the button top of the board
        layeredPane = new JLayeredPane();
        // Position the layered pane below the typo
        layeredPane.setBounds(0, occupiedSpace, getWidth(), getHeight() - occupiedSpace);

        // Board panel used to display the available boards
        boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(layeredPane.getWidth(), layeredPane.getHeight()));
        boardPanel.setBackground(appConfig.getTheme().getLayerOneColor());

        // Board will show in grid format
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        flowLayout.setVgap(SPACING);
        flowLayout.setHgap(SPACING);

        boardPanel.setLayout(flowLayout);
        boardPanel.setOpaque(true);

        // Floating create board button
        button = new JButton(Strings.CREATE_BOARD_BUTTON);
        // Position the button in the bottom right
        button.setBounds(
            getWidth() - CREATE_BUTTON_WIDTH - CREATE_BUTTON_RIGHT_PADDING, 
            getHeight() - occupiedSpace - CREATE_BUTTON_HEIGHT - CREATE_BUTTON_BOTTOM_PADDING, 
            CREATE_BUTTON_WIDTH, 
            CREATE_BUTTON_HEIGHT
        );
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBackground(appConfig.getTheme().getPrimaryButtonColor());
        
        // Scroll pane wraps the boardpanel
        scrollPane = new JScrollPane(boardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        layeredPane.add(scrollPane, Integer.valueOf(0));
        layeredPane.add(button, Integer.valueOf(1));
        add(layeredPane);
    }

    private void fetchBoards() {
        dataService.getBoards();
    }

    private void updateBoardSize() {
        // Get number of row needed to be calculated
        int ratio = boardPanel.getWidth() / (BOARD_CARD_WIDTH + SPACING);

        ratio = ratio == 0 ? 1 : ratio;
        
        int row = noOfBoards/ratio;
        boolean nonFilledBoards = (noOfBoards % ratio == 0) ?  false : true;
        int calculatedHeight = row * (BOARD_CARD_HEIGHT + SPACING) + SPACING + (nonFilledBoards ? (BOARD_CARD_HEIGHT + SPACING) : 0) + 40;

        boardPanel.setPreferredSize(new Dimension(getWidth(), calculatedHeight));
        boardPanel.setBounds(0, 0, getWidth(), calculatedHeight);
        scrollPane.setBounds(0, 0, getWidth(), layeredPane.getHeight());
    }

    private void updateFloatingButton() {
        switch (layoutType) {
            default:
                button.setBounds(
                    layeredPane.getWidth() - CREATE_BUTTON_WIDTH - CREATE_BUTTON_RIGHT_PADDING,
                    layeredPane.getHeight() - CREATE_BUTTON_HEIGHT - CREATE_BUTTON_BOTTOM_PADDING,
                    CREATE_BUTTON_WIDTH,
                    CREATE_BUTTON_HEIGHT
                );
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateBoardSize();
        updateFloatingButton();
    }

    private JPanel constructBoard(Board board) {
        // Board layout
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(BOARD_CARD_WIDTH, BOARD_CARD_HEIGHT));
        jPanel.setBackground(appConfig.getTheme().getLayerTwoColor());
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Name of the board
        MultilineLabel name = new MultilineLabel(board.getBoardName());
        name.setPreferredSize(new Dimension(BOARD_CARD_WIDTH - Dimens.TEXT_PADDING, Dimens.FONT_24 * 2 + Dimens.TEXT_PADDING));
        name.setFont("Default", Dimens.FONT_24, TextAttribute.WEIGHT_BOLD);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.add(name);
        
        // Description of the board
        MultilineLabel description = new MultilineLabel(board.getDescription() + board.getDescription() + board.getCreatedDateTime() + board.getDescription());
        description.setPreferredSize(new Dimension(BOARD_CARD_WIDTH - Dimens.TEXT_PADDING, Dimens.FONT_16 * 4 + Dimens.TEXT_PADDING));
        description.setFont("Default", Dimens.FONT_16, TextAttribute.WEIGHT_LIGHT);
        jPanel.add(description);
        return jPanel;
    }

    private ViewUpdate viewUpdate = new ViewUpdate() {

        @Override
        public void onBoardDataUpdate(List<Board> boards) {
            for (Board board: boards) {
                boardPanel.add(constructBoard(board));
            }
            noOfBoards = boards.size();
            updateBoardSize();
        }

        @Override
        public void onBoardWriteComplete(String message, List<Board> boards) {

        }

        @Override
        public void onDataRequestFailed(String message) {
            
        }

        @Override public void onListDataUpdate(List<ListData> listDatas) {}

        @Override public void onCardDataUpdate(List<Card> cards) {}

        @Override public void onListDataWriteComplete(String message, List<ListData> listDatas) {}

        @Override public void onCardWriteComplete(String message, List<Card> cards) {}
        
    };
}
