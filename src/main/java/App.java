
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {

        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("PacMan");
        frame.setSize(boardWidth, boardHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To exit the application when the window is closed

        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setResizable(false); // Prevent the window from being resized

        PacMan pacManGame = new PacMan();
        pacManGame.requestFocus(); // Request focus for the PacMan game panel to receive key events

        frame.add(pacManGame);
        frame.pack(); // Adjust the frame size to fit the preferred size of its components
        frame.setVisible(true); // Make the window visible
    }
}
