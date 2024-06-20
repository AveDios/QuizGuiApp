package screens;

import constants.CommonConstants;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateQuestionScreen extends JFrame {
    private JTextArea questionTextArea;
    private JTextField categoryTextField;
    private JRadioButton[] answerRadioButtons;
    private JTextField[] answerTextField;
    private ButtonGroup buttonGroup;

    public CreateQuestionScreen() {
        super("Create a Question");
        setSize(851, 565);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);
        setResizable(false);

        answerRadioButtons = new JRadioButton[4];
        answerTextField = new JTextField[4];
        buttonGroup = new ButtonGroup();

        addGuiComponent();
    }

    private void addGuiComponent() {
        JLabel titleLabel = new JLabel("Create Your Own Question");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 15, 310, 29);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        JLabel questionLabel = new JLabel("Question");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBounds(50, 80, 93, 20);
        questionLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(questionLabel);

        questionTextArea = new JTextArea();
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        questionTextArea.setBounds(50, 110, 310, 110);
        questionTextArea.setForeground(CommonConstants.DARK_BLUE);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        add(questionTextArea);

        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel.setBounds(50, 250, 80, 20);
        categoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(categoryLabel);

        categoryTextField = new JTextField();
        categoryTextField.setFont(new Font("Arial", Font.BOLD, 16));
        categoryTextField.setBounds(50, 280, 310, 36);
        categoryTextField.setForeground(CommonConstants.DARK_BLUE);
        add(categoryTextField);

        addAnswerComponent();

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBounds(300, 450, 262, 45);
        submitButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        submitButton.setForeground(CommonConstants.LIGHT_BLUE);
        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    String question = questionTextArea.getText();
                    String category = categoryTextField.getText();
                    String[] answers = new String[answerTextField.length];
                    int correctIndex = 0;
                    for (int i = 0; i < answers.length; i++) {
                        answers[i] = answerTextField[i].getText();
                        if (answerRadioButtons[i].isSelected()) {
                            correctIndex = i;

                        }
                    }

                    // update BD
                    if (JDBC.saveQuestionsCategoryAndAnswersToDatabase(question, category, answers, correctIndex)) {
                        JOptionPane.showMessageDialog(CreateQuestionScreen.this,
                                "Successfully Added Question!");

                        // reset fields
                        resetFields();
                    } else {
                        JOptionPane.showMessageDialog(CreateQuestionScreen.this,
                                "Failed to Add Question!");
                    }
                } else {
                    JOptionPane.showMessageDialog(CreateQuestionScreen.this,
                            "Invalid Input!");
                }
            }
        });
        add(submitButton);

        JLabel goBackLabel = new JLabel("Go Back");
        goBackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        goBackLabel.setBounds(300, 500, 262, 20);
        goBackLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        goBackLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        goBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // display title screen
                TitleScreenGui titleScreenGui = new TitleScreenGui();
                titleScreenGui.setLocationRelativeTo(CreateQuestionScreen.this);

                // dispose of the screen
                CreateQuestionScreen.this.dispose();

                //make title screen visible
                titleScreenGui.setVisible(true);
            }
        });
        add(goBackLabel);
    }

    private void addAnswerComponent() {
        int verticalSpacing = 100;

        for (int i = 0; i < 4; i++) {
            JLabel answerLabel = new JLabel("Answer #" + (i + 1));
            answerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            answerLabel.setBounds(470, 60 + (i * verticalSpacing), 93, 20);
            answerLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
            add(answerLabel);

            answerRadioButtons[i] = new JRadioButton();
            answerRadioButtons[i].setBounds(440, 90 + (i * verticalSpacing), 21, 21);
            answerRadioButtons[i].setBackground(null);
            buttonGroup.add(answerRadioButtons[i]);
            add(answerRadioButtons[i]);

            answerTextField[i] = new JTextField();
            answerTextField[i].setBounds(470, 80 + (i * verticalSpacing), 310, 36);
            answerTextField[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerTextField[i].setForeground(CommonConstants.DARK_BLUE);
            add(answerTextField[i]);
        }
        answerRadioButtons[0].setSelected(true);
    }

    // true - valid input
    // false - invalid input
    private boolean validateInput() {
        // make sure that question field is not empty
        if (questionTextArea.getText().replaceAll(" ", "").length() <= 0) return false;

        // make sure that category field is not empty
        if (categoryTextField.getText().replaceAll(" ", "").length() <= 0) return false;

        // make sure all answer fields are not empty
        for (int i = 0; i < answerTextField.length; i++) {
            if (answerTextField[i].getText().replaceAll(" ", "").length() <= 0) return false;
        }
        return true;
    }

    private void resetFields() {
        questionTextArea.setText("");
        categoryTextField.setText("");
        for (int i = 0; i < answerTextField.length; i++) {
            answerTextField[i].setText("");
        }
    }
}
