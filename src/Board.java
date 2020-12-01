import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Board extends JPanel {
  // TODO: Implement a way for the player to win

  // Memberikan lebar dan tinggi board
  private final static int BOARDWIDTH = 800;
  private final static int BOARDHEIGHT = 800;

  // Memberikan ukuran pixel
  private final static int PIXELSIZE = 25;

  // Mencari total pixel dari luas board/luas pixel
  private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT) / (PIXELSIZE * PIXELSIZE);

  // Memberikan kondisi awal kalau game sedang berlangsung
  private boolean inGame = true;

  // Instansiasi snake
  private Snake snake = new Snake();

  // Instansiasi food
  private Food food = new Food();

  // Timer untuk mengukur tick dari game
  private Timer timer;

  // Bentuk board
  public Board() {
    // setBounds(90,75,800,800);
    setBackground(Color.BLACK);
    setFocusable(true);
    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));
    addKeyListener(new Keys());
    initializeGame();
  }

  // public void paint (Graphics bg){
  // bg.setColor(Color.BLACK);
  // bg.fillRect(90,75,800,800);
  // }

  // Method untuk memasukkan componen ke screen
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  // Memasukkan snake
  void draw(Graphics g) {
    if (inGame == true) {
      // Snake
      for (int i = 0; i < snake.getJoints(); i++) {
        // Snake head
        if (i == 0) {
          g.setColor(Color.orange);
          g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
          // Tubuh Snake
        } else {
          g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
        }
      }

      // Mensinkronkan graphics
      Toolkit.getDefaultToolkit().sync();
    } else {
    }
  }

  void initializeGame() {
    // Memberikan jumlah tubuh snake
    snake.setJoints(3); // set our snake's initial size

    // Membuat tubuh snake
    for (int i = 0; i < snake.getJoints(); i++) {
      snake.setSnakeX(BOARDWIDTH / 2);
      snake.setSnakeY(BOARDHEIGHT / 2);
    }
  }

  // Method untuk mengambil total pixel yang ada
  public static int getAllDots() {
    return TOTALPIXELS;
  }

  // Method untuk mengambil ukuran pixel
  public static int getDotSize() {
    return PIXELSIZE;
  }

  private class Keys extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

      int key = e.getKeyCode();

      if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
        snake.setMovingLeft(true);
        snake.setMovingUp(false);
        snake.setMovingDown(false);
      }

      if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
        snake.setMovingRight(true);
        snake.setMovingUp(false);
        snake.setMovingDown(false);
      }

      if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
        snake.setMovingUp(true);
        snake.setMovingRight(false);
        snake.setMovingLeft(false);
      }

      if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
        snake.setMovingDown(true);
        snake.setMovingRight(false);
        snake.setMovingLeft(false);
      }

      if ((key == KeyEvent.VK_SPACE) && (inGame == false)) {
        inGame = true;
        snake.setMovingDown(false);
        snake.setMovingRight(false);
        snake.setMovingLeft(false);
        snake.setMovingUp(false);

        initializeGame();
      }
    }
  }

  void checkFoodCollisions() {

    if ((proximity(snake.getSnakeX(0), food.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), food.getFoodY(), 20))) {

      System.out.println("intersection");
      // Menambahkan ukuran snake +1
      snake.setJoints(snake.getJoints() + 1);
      // membuat makanan baru pada board
      food.createFood();
    }
  }

  void checkCollisions() {

    // If the snake hits its' own joints..
    for (int i = snake.getJoints(); i > 0; i--) {

      // Menurut kondisi dibawah, snake tidak bisa menabrak diri sendiri apa bila
      // ukuran snake < 5
      if ((i > 5) && (snake.getSnakeX(0) == snake.getSnakeX(i) && (snake.getSnakeY(0) == snake.getSnakeY(i)))) {
        inGame = false; // then the game ends
      }
    }

    // If the snake intersects with the board edges..
    if (snake.getSnakeY(0) >= BOARDHEIGHT) {
      inGame = false;
    }

    if (snake.getSnakeY(0) < 0) {
      inGame = false;
    }

    if (snake.getSnakeX(0) >= BOARDWIDTH) {
      inGame = false;
    }

    if (snake.getSnakeX(0) < 0) {
      inGame = false;
    }

    // If the game has ended, then we can stop our timer
    if (!inGame) {
      timer.stop();
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (inGame == true) {

      checkFoodCollisions();
      checkCollisions();
      snake.move();

      System.out
          .println(snake.getSnakeX(0) + " " + snake.getSnakeY(0) + " " + food.getFoodX() + ", " + food.getFoodY());
    }
    // Repaint or 'render' our screen
    repaint();
  }

  private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
  }
}