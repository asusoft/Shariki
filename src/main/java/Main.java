import BallsGame.Game;
import UI.FieldWidget;

import javax.swing.*;
import java.awt.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {
        private Game game = new Game();

        public GamePanel() throws HeadlessException {
            setTitle("Шарики");
            JPanel content = (JPanel)this.getContentPane();
            content.setLayout(new FlowLayout());
            content.add(new FieldWidget());

            this.pack();
            this.setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setVisible(true);
        }
    }
}
