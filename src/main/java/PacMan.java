import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacMan extends JPanel implements ActionListener, KeyListener {

  class Block {
    int x;
    int y;

    int height;
    int width;

    Image image;

    int startX;
    int startY;

    char direction = 'U'; // U = Up, D = Down, L = Left, R = Right
    int velocityX = 0;
    int velocityY = 0;

    Block(int x, int y, int height, int width, Image image) {
      this.x = x;
      this.y = y;

      this.height = height;
      this.width = width;

      this.image = image;

      this.startX = x;
      this.startY = y;
    }

    private void updateDirection(char direction) {
      this.direction = direction;
      updateVelocity();
    }

    private void updateVelocity() {

      final int displacement = tileSize / 4;

      if (this.direction == 'U') {
        this.velocityX = 0;
        this.velocityY = -displacement;
        return;
      }

      if (this.direction == 'D') {
        this.velocityX = 0;
        this.velocityY = displacement;
        return;
      }

      if (this.direction == 'L') {
        this.velocityX = -displacement;
        this.velocityY = 0;
        return;
      }

      if (this.direction == 'R') {
        this.velocityX = displacement;
        this.velocityY = 0;
        return;
      }
    }

    public void move() {
      this.x += this.velocityX;
      this.y += this.velocityY;

      if (_isCollidedWithGhost()) {
        this.x -= this.velocityX;
        this.y -= this.velocityY;

        _stop();

        return;
      }

      if (this.x < 0) {
        this.x = boardWidth - tileSize;
      }

      if (this.x >= boardWidth) {
        this.x = 0;
      }

      if (this.y < 0) {
        this.y = boardHeight - tileSize;
      }

      if (this.y >= boardHeight) {
        this.y = 0;
      }
    }

    private void _stop() {
      this.velocityX = 0;
      this.velocityY = 0;
    }

    private boolean _isCollidedWithWall() {
      for (Block wall : walls) {
        if (checkCollisionWithAnotherBlock(wall))
          return true;
      }

      return false;
    }

    private boolean _isCollidedWithGhost() {
      for (Block ghost : ghosts) {
        if (checkCollisionWithAnotherBlock(ghost))
          return true;
      }

      return false;
    }

    private boolean checkCollisionWithAnotherBlock(Block block) {
      return this.x < block.x + block.width &&
          this.x + this.width > block.x &&
          this.y < block.y + block.height &&
          this.y + this.height > block.y;
    }
  }

  final private int rowCount = 21;
  final private int columnCount = 19;
  final private int tileSize = 32;
  final private int boardWidth = columnCount * tileSize;
  final private int boardHeight = rowCount * tileSize;

  private Image wallImage;

  private Image blueGhostImage;
  private Image redGhostImage;
  private Image pinkGhostImage;
  private Image orangeGhostImage;
  private Image scaredGhostImage;

  private Image pacmanUpImage;
  private Image pacmanDownImage;
  private Image pacmanLeftImage;
  private Image pacmanRightImage;

  HashSet<Block> walls;
  HashSet<Block> foods;
  HashSet<Block> ghosts;
  Block pacman;

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

  PacMan() {
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    setBackground(Color.BLACK); // Set the background color of the JPanel to black
    addKeyListener(this); // Add the KeyListener to the JPanel
    setFocusable(true); // Make the JPanel focusable to receive key events

    loadImages();
    loadMap();

    gameLoop = new Timer(50, this); // 50 milliseconds (20 frames per second)
    gameLoop.start(); // Start the game loop
  }

  private void loadImages() {

    wallImage = new ImageIcon(getClass().getResource("/images/wall/wall.png")).getImage();

    blueGhostImage = new ImageIcon(getClass().getResource("/images/ghosts/blue_ghost.png")).getImage();
    redGhostImage = new ImageIcon(getClass().getResource("/images/ghosts/red_ghost.png")).getImage();
    pinkGhostImage = new ImageIcon(getClass().getResource("/images/ghosts/pink_ghost.png")).getImage();
    orangeGhostImage = new ImageIcon(getClass().getResource("/images/ghosts/orange_ghost.png")).getImage();
    // scaredGhostImage = new
    // ImageIcon(getClass().getResource("/images/ghosts/scared_ghost.png")).getImage();

    pacmanUpImage = new ImageIcon(getClass().getResource("/images/pacman/pacman_up.png")).getImage();
    pacmanDownImage = new ImageIcon(getClass().getResource("/images/pacman/pacman_down.png")).getImage();
    pacmanLeftImage = new ImageIcon(getClass().getResource("/images/pacman/pacman_left.png")).getImage();
    pacmanRightImage = new ImageIcon(getClass().getResource("/images/pacman/pacman_right.png")).getImage();

    // powerFoodImage = new
    // ImageIcon(getClass().getResource("/images/point/power_food.png")).getImage();
  }

  public void loadMap() {
    walls = new HashSet<>();
    foods = new HashSet<>();
    ghosts = new HashSet<>();

    final Map<String, Image> ghostImages = Map.of(
        "b", blueGhostImage,
        "r", redGhostImage,
        "p", pinkGhostImage,
        "o", orangeGhostImage);

    for (int row = 0; row < rowCount; row++) {
      for (int col = 0; col < columnCount; col++) {
        String rowTile = tileMap[row];
        char tile = rowTile.charAt(col);

        int x = col * tileSize;
        int y = row * tileSize;

        switch (tile) {
          case 'X':
            Block wall = new Block(x, y, tileSize, tileSize, wallImage);
            walls.add(wall);
            break;
          case 'P':
            pacman = new Block(x, y, tileSize, tileSize, pacmanRightImage);
            break;
          case 'b':
          case 'r':
          case 'p':
          case 'o':
            Image ghostImage = ghostImages.get(String.valueOf(tile));
            Block ghost = new Block(x, y, tileSize, tileSize, ghostImage);
            ghosts.add(ghost);
            break;
          case ' ':
            Block food = new Block(x + 14, y + 14, 4, 4, null);
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

    g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height,
        null);

    for (Block wall : walls) {
      g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
    }

    for (Block ghost : ghosts) {
      g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
    }

    for (Block food : foods) {
      g.setColor(Color.WHITE);
      g.fillOval(food.x, food.y, food.width, food.height);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pacman.move();
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
    movePacman(e.getKeyCode());
  }

  public void movePacman(int keyCode) {

    final Map<Integer, String> directionsMap = Map.of(
        KeyEvent.VK_UP, "U",
        KeyEvent.VK_DOWN, "D",
        KeyEvent.VK_LEFT, "L",
        KeyEvent.VK_RIGHT, "R",
        KeyEvent.VK_W, "U",
        KeyEvent.VK_S, "D",
        KeyEvent.VK_A, "L",
        KeyEvent.VK_D, "R");

    if (!directionsMap.containsKey(keyCode)) {
      System.out.println("Invalid key pressed. Please use arrow keys to move PacMan.");
      return;
    }

    String direction = directionsMap.get(keyCode);
    pacman.updateDirection(direction.charAt(0));
  }
}
