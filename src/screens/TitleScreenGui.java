package screens;

import constants.CommonConstants;
import database.Category;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {
    private JComboBox categoriesMenu;
    private JTextField numOfQuestionsTextField;

    public TitleScreenGui() {
        super("Title Screen");

        setSize(400, 565);

        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel titleLabel = new JLabel("Quiz Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 46));
        titleLabel.setBounds(0, 20, 390, 43);
        titleLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        JLabel chooseCategoryLabel = new JLabel("Choose a Category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategoryLabel.setBounds(0, 90, 400, 43);
        chooseCategoryLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        chooseCategoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(chooseCategoryLabel);

        ArrayList<String> categoryList = JDBC.getCategories();
        categoriesMenu = new JComboBox(categoryList.toArray());
        categoriesMenu.setBounds(20, 120, 337, 45);
        categoriesMenu.setForeground(CommonConstants.DARK_BLUE);
        add(categoriesMenu);

        JLabel numOfQuestionsLabel = new JLabel("Numbers of Questions: ");
        numOfQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsLabel.setBounds(20, 190, 200, 20);
        numOfQuestionsLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        numOfQuestionsLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(numOfQuestionsLabel);

        numOfQuestionsTextField = new JTextField("10");
        numOfQuestionsTextField.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsTextField.setBounds(240, 190, 98, 26);
        numOfQuestionsTextField.setForeground(CommonConstants.DARK_BLUE);
        add(numOfQuestionsTextField);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBounds(65, 290, 262, 45);
        startButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        startButton.setForeground(CommonConstants.LIGHT_BLUE);
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()){
                    Category category = JDBC.getCategory(categoriesMenu.getSelectedItem().toString());

                    if (category == null) return;

                    int numOfQuesions = Integer.parseInt(numOfQuestionsTextField.getText());

                    QuizGameScreenGui quizGameScreenGui = new QuizGameScreenGui(category, numOfQuesions);
                    quizGameScreenGui.setLocationRelativeTo(TitleScreenGui.this);

                    TitleScreenGui.this.dispose();
                    quizGameScreenGui.setVisible(true);

                }
            }
        });
        add(startButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setBounds(65, 350, 262, 45);
        exitButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        exitButton.setForeground(CommonConstants.LIGHT_BLUE);
        exitButton.setFocusable(false);
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TitleScreenGui.this.dispose();
            }
        });
        add(exitButton);

        JButton createQuestionButton = new JButton("Create a Question");
        createQuestionButton.setFont(new Font("Arial", Font.BOLD, 20));
        createQuestionButton.setBounds(65, 410, 262, 45);
        createQuestionButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        createQuestionButton.setForeground(CommonConstants.LIGHT_BLUE);
        createQuestionButton.setFocusable(false);
        createQuestionButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create question screen gui
                CreateQuestionScreen createQuestionScreen = new CreateQuestionScreen();
                createQuestionScreen.setLocationRelativeTo(TitleScreenGui.this);

                // dispose of this title screen
                TitleScreenGui.this.dispose();

                // display "Create a Question Screen" gui
                createQuestionScreen.setVisible(true);
            }
        });
        add(createQuestionButton);
    }

    // true - valid input
    // false - invalid input
    private boolean validateInput(){
        // num of questions can not be empty
        if (numOfQuestionsTextField.getText().replaceAll(" ", "").length() <= 0) return false;

        if (categoriesMenu.getSelectedItem() == null) return false;

        return true;

    }
}
