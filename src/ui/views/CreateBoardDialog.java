package ui.views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import resource.Dimens;
import resource.Strings;

/**
 * Dialog screen for displaying the create board functionality, allow you to enter the board name, and board description
 */
public class CreateBoardDialog extends JDialog {

    // Board name text field
    private JTextField boardNameField;
    // Board description text field
    private JTextArea descriptionTextArea;
    // Listener class for returning the result to landing screen
    private ResultPostListener resultPost;

    public CreateBoardDialog() {
        super();
        
        // Dialog screen configuration
        setTitle(Strings.CREATE_BOARD_TITLE);
        setSize(getPreferredSize());
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Form of the board name and description input
        JPanel form = new JPanel();
        form.setPreferredSize(new Dimension(Dimens.CREATE_BOARD_DIALOG_WIDTH, Dimens.CREATE_BOARD_DIALOG_HEIGHT));
        form.setLayout(null);
        
        // Board name label
        JLabel boardNameLabel = new JLabel(Strings.CREATE_BOARD_NAME);
        boardNameLabel.setBounds(20, 10, 460, 20);

        // Board name text field for input
        boardNameField = new JTextField();
        boardNameField.setBounds(20, 30, 460, 30);
        
        // Board name error displaying label
        JLabel invalidBoardNameError = new JLabel();
        invalidBoardNameError.setForeground(Color.red);
        invalidBoardNameError.setBounds(20, 60, 460, 20);

        // Description label
        JLabel boardDescriptionLabel = new JLabel(Strings.CREATE_BOARD_DESCRIPTION);
        boardDescriptionLabel.setBounds(20, 80, 460, 20);

        // Description text field for input
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setBounds(20, 110, 460, 230);

        // Description error displaying label
        JLabel invalidBoardDescriptionError = new JLabel();
        invalidBoardDescriptionError.setForeground(Color.red);
        invalidBoardDescriptionError.setBounds(20, 340, 460, 20);

        // Cancel button for EAT 5 START
        JButton cancelButton = new JButton(Strings.CANCEL_BUTTON);
        cancelButton.setBounds(270, 360, 100, 30);
        cancelButton.addActionListener((e) -> dispose());

        // Create button to create new board
        JButton createButton = new JButton(Strings.CREATE_BUTTON);
        createButton.setBounds(380, 360, 100, 30);

        // Well well well finaly some action
        createButton.addActionListener((e) -> {

            // Create the existing text
            invalidBoardNameError.setText("");
            invalidBoardDescriptionError.setText("");

            // Validated If you give the listener, otherwise entered stuff won't save
            if (resultPost != null) {

                // Check if board name is empty
                if (!isValid(boardNameField.getText())) {
                    invalidBoardNameError.setText(Strings.INVALID_BOARD_NAME);
                    return;
                    //Check if entered board name doesn't reach the maximum character
                } else if (!isValidNumberOfCharacter(boardNameField.getText(), 50)) {
                    invalidBoardNameError.setText(Strings.BOARD_NAME_MAXIMUM_CHARACTER_REACHED);
                    return;
                    // Check if description is entered it doen't reached the maximum character
                } if (isValid(descriptionTextArea.getText()) && !isValidNumberOfCharacter(descriptionTextArea.getText(), 200)) {
                    invalidBoardDescriptionError.setText(Strings.DESCRIPTION_MAXIMUM_CHARACTER_REACHED);
                    return;
                }

                // You can ask why maximum character when we can do infinite, well I'm bored

                // Post the result in the listener
                resultPost.onPost(boardNameField.getText(), descriptionTextArea.getText());
            }
            // Don't forgot to dispose the dialog, otherwise it will hang on the screen.
            dispose();
        });

        // Add the view in the order how it should show
        form.add(boardNameLabel);
        form.add(boardNameField);
        form.add(invalidBoardNameError);
        form.add(boardDescriptionLabel);
        form.add(descriptionTextArea);
        form.add(invalidBoardDescriptionError);
        form.add(cancelButton);
        form.add(createButton);
        
        // assign the form to content pane
        setContentPane(form);

        // Pack will resize the dialog with Preferred size
        pack();
    }

    /**
     * Method to check whether the entered text is empty
     * @param text
     * @return
     */
    private boolean isValid(String text) {
        return text != null && text.length() > 0;
    }
    
    /**
     * Method to check whether the entered text is reached maximum character
     * @param text
     * @param max
     * @return
     */
    private boolean isValidNumberOfCharacter(String text, int max) {
        return text.length() <= max;
    }

    /**
     * Method to assign listener
     * @param resultPost
     */
    public void setResultPostListener(ResultPostListener resultPost) {
        this.resultPost = resultPost;
    }

    /**
     * Interface for the result listener
     */
    public interface ResultPostListener {
        void onPost(String boardName, String description);
    }
}
