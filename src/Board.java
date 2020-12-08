import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
  // TODO: Implement a way for the player to win

  // Memberikan lebar dan tinggi board
  private final static int BOARDWIDTH = 600;
  private final static int BOARDHEIGHT = 600;

  // Memberikan ukuran pixel
  private final static int PIXELSIZE = 6;

  // Mencari total pixel dari luas board/luas pixel
  private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT) / (PIXELSIZE * PIXELSIZE);

  // Memberikan kondisi awal kalau game sedang berlangsung
  private boolean inGame = true;

  private boolean isFirstLaunch = true;

  // Timer untuk mengukur tick dari game
  private Timer timer;

  // Set speed game snake, semakin kecil angka semakin cepat snake jalan
  private static int speed = 50;

  // Instansiasi snake
  private Snake snake = new Snake();

  // Instansiasi food
  private Food food = new Food();

  // Instansiasi score
  private Score score = new Score();

  // Bentuk board
  public Board() {
    // setBounds(90,75,800,800);
    addKeyListener(new Keys());
    setBackground(Color.BLACK);
    setFocusable(true);

    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

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

      // Food
      g.setColor(Color.RED);
      g.fillRect(food.getFoodX(), food.getFoodY(), PIXELSIZE, PIXELSIZE);

      // Snake
      for (int i = 0; i < snake.getJoints(); i++) {
        // Snake head
        if (i == 0) {
          g.setColor(Color.GREEN);
          g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
          // Tubuh Snake
        } else {
          g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
        }
      }

      // Mensinkronkan graphics
      Toolkit.getDefaultToolkit().sync();
    } else {
      endGame(g);
    }
  }

  void initializeGame() {
    // Reset score
    score.resetScore();

    // Memberikan jumlah tubuh snake
    snake.setJoints(3); // set our snake's initial size

    // Membuat tubuh snake
    for (int i = 0; i < snake.getJoints(); i++) {
      snake.setSnakeX(BOARDWIDTH / 2);
      snake.setSnakeY(BOARDHEIGHT / 2);
    }

    // Mulai snake bergerak ke kanan
    snake.setMovingRight(true);

    // Membuat makanan untuk pertama kali (Saat Memulai Game)
    food.createFood();

    // Set timer untuk mengukur tick dalam game
    timer = new Timer(speed, this);
    timer.start();
  }

  // Method untuk mengambil total pixel yang ada
  public static int getAllDots() {
    return TOTALPIXELS;
  }

  void checkFoodCollisions() {

    if ((proximity(snake.getSnakeX(0), food.getFoodX(), 2)) && (proximity(snake.getSnakeY(0), food.getFoodY(), 2))) {

      System.out.println("intersection");
      // Menambahkan ukuran snake +1
      snake.setJoints(snake.getJoints() + 1);
      // menambah score
      score.addScore();
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

  // Method untuk mengambil ukuran pixel
  public static int getDotSize() {
    return PIXELSIZE;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (inGame == true) {

      checkFoodCollisions();
      checkCollisions();
      snake.move();

      System.out.println("(" + snake.getSnakeX(0) + "," + snake.getSnakeY(0) + ") (" + food.getFoodX() + ","
          + food.getFoodY() + ") " + score.getScore());
    }
    // Repaint or 'render' our screen
    repaint();
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

  void endGame(Graphics g) {

    // Create a message telling the player the game is over
    String message = "Game over !";
    String scoreMsg = "Score : " + score.getScore();

    // Create a new font instance
    Font font = new Font("Times New Roman", Font.BOLD, 14);
    FontMetrics metrics = getFontMetrics(font);

    // Set the color of the text to red, and set the font
    g.setColor(Color.red);
    g.setFont(font);

    // Draw the message to the board
    g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, BOARDHEIGHT / 2);
    g.drawString(scoreMsg, (BOARDWIDTH - metrics.stringWidth(scoreMsg)) / 2, (BOARDHEIGHT / 5 * 3));

    System.out.println("Game Ended");
  }

  private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
  }
  
}