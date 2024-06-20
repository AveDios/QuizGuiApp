import database.Category;
import screens.CreateQuestionScreen;
import screens.QuizGameScreenGui;
import screens.TitleScreenGui;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TitleScreenGui().setVisible(true);


            }
        });
    }
}
