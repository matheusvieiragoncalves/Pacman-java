import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.Timer;

import objects.food.Food;
import objects.wall.Wall;

public class Game extends JPanel implements ActionListener, KeyListener {

  final private int rowCount = 21;
  final private int columnCount = 19;
  final private int tileSize = 32;
  final private int boardWidth = columnCount * tileSize;
  final private int boardHeight = rowCount * tileSize;

  HashSet<Wall> walls;
  HashSet<Food> foods;

  final private String[] tileMap = {
      "XXXXXXXXXXXXXXXXXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X                 X",
      "X XX X XXXXX X XX X",
      "X    X       X    X",
      "XXXX XXXX XXXX XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXbXX X XXXX",
      "O       opr       O",
      "XXXX X XXXXX X XXXX",
      "OOOX X       X XOOO",
      "XXXX X XXXXX X XXXX",
      "X        X        X",
      "X XX XXX X XXX XX X",
      "X  X     P     X  X",
      "XX X X XXXXX X X XX",
      "X    X   X   X    X",
      "X XXXXXX X XXXXXX X",
      "X                 X",
      "XXXXXXXXXXXXXXXXXXX",
  };

  Timer gameLoop;

  int score = 0;
  int lives = 3; // Number of lives the player has
  boolean gameOver = false; // Flag to indicate if the game is over

  Game() {
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    setBackground(Color.BLACK); // Set the background color of the JPanel to black
    addKeyListener(this); // Add the KeyListener to the JPanel
    setFocusable(true); // Make the JPanel focusable to receive key events

    loadMap();

    gameLoop = new Timer(50, this); // 50 milliseconds (20 frames per second)
    gameLoop.start(); // Start the game loop
  }

  public void loadMap() {
    walls = new HashSet<>();
    foods = new HashSet<>();

    for (int row = 0; row < rowCount; row++) {
      for (int col = 0; col < columnCount; col++) {
        String rowTile = tileMap[row];
        char tile = rowTile.charAt(col);

        int x = col * tileSize;
        int y = row * tileSize;

        switch (tile) {
          case 'X':
            Wall wall = new Wall(x, y, tileSize, tileSize);
            walls.add(wall);
            break;
          case 'P':
            // pacman = new Block(x, y, tileSize, tileSize, pacmanRightImage,
            // BlockTypeEnum.PACMAN);
            break;
          case 'b':
          case 'r':
          case 'p':
          case 'o':
            // Image ghostImage = ghostImages.get(String.valueOf(tile));
            // Block ghost = new Block(x, y, tileSize, tileSize, ghostImage,
            // BlockTypeEnum.GHOST);
            // ghosts.add(ghost);
            break;
          case ' ':
            Food food = new Food(x, y);
            foods.add(food);
            break;
          default:
            break;
        }
      }
    }

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  public void draw(Graphics g) {
    _drawWalls(g);
    _drawFoods(g);
  }

  private void _drawWalls(Graphics g) {
    for (Wall wall : walls) {
      g.drawImage(wall.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
    }
  }

  private void _drawFoods(Graphics g) {

    for (Food food : foods) {
      g.setColor(food.getColor());
      g.fillOval(food.getX(), food.getY(), food.getWidth(), food.getHeight());
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

}
