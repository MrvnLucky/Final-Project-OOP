import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.Graphics2D;
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
  private boolean inGame = false;

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
    if (isFirstLaunch == true) {
      inGame = false;
      timer.stop();
      instructionScreen(g);
    }

    if (inGame == true && isFirstLaunch == false) {

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
      Font font = new Font("Times New Roman", Font.BOLD, 14);
      g.setColor(Color.WHITE);
      g.setFont(font);
      g.drawString(String.valueOf(score.getScore()), 580, 15);
      // Mensinkronkan graphics
      Toolkit.getDefaultToolkit().sync();
    }
    // else if (inGame == false && isFirstLaunch == true) {
    // instructionScreen(g);
    // isFirstLaunch = false;
    // }
    else if (inGame == false && isFirstLaunch == false) {
      endGame(g);
    }
  }

  void initializeGame() {

    // Reset score
    score.resetScore();

    // Memberikan jumlah tubuh snake
    snake.setJoints(5); // set our snake's initial size

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
    inGame = true;
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
        inGame = true;
        isFirstLaunch = false;
      }
    }
  }

  void endGame(Graphics g) {

    String message = "Game over !";
    String yourScoreMsg = "Your score : " + score.getScore();
    String leaderMsg = "Leader Board : ";
    String[] scoreMsg = new String[10];
    String restartMsg = "Press SPACEBAR to play again";
    score.saveScore();

    for (int i = 0; i < score.getLeader().size(); i++) {
      scoreMsg[i] = "Rank " + (i + 1) + " : " + String.valueOf(score.getLeader().get(i));
      if (i == 9) {
        break;
      }
    }

    // Create a new font instance
    Font font = new Font("Times New Roman", Font.BOLD, 20);
    FontMetrics metrics = getFontMetrics(font);
    Font font1 = new Font("Times New Roman", Font.BOLD, 16);
    FontMetrics metrics1 = getFontMetrics(font1);

    g.setColor(Color.RED);
    g.setFont(font);
    g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2, 100);

    g.setColor(Color.GREEN);
    g.setFont(font1);
    g.drawString(leaderMsg, (BOARDWIDTH - metrics1.stringWidth(leaderMsg)) / 2, 200);

    for (int i = 0; i < score.getLeader().size(); i++) {
      g.drawString(scoreMsg[i], (BOARDWIDTH - metrics1.stringWidth(scoreMsg[i])) / 2, 220 + (i * 20));
      if (i == 9) {
        break;
      }
    }

    g.setColor(Color.WHITE);
    g.setFont(font1);
    g.drawString(yourScoreMsg, (BOARDWIDTH - metrics1.stringWidth(yourScoreMsg)) / 2, 500);

    g.setColor(Color.RED);
    g.setFont(font);
    g.drawString(restartMsg, (BOARDWIDTH - metrics.stringWidth(restartMsg)) / 2, 550);

    System.out.println("Game Ended");

  }

  void instructionScreen(Graphics g) {

    // Create a message telling the player the game is over
    Rectangle instrBox = new Rectangle(Board.BOARDWIDTH / 6 - 20, Board.BOARDHEIGHT / 5 + 50, 420, 240);
    String message = "S N E C C";
    String message1 = "Instruction";
    String message2 = "To play the game, you basically press";
    String message3 = "Directional arrow";
    String message4 = "in your keyboard to change";
    String message5 = "Your S n e c c Direction, That is all";
    String arrow = "\u2190     \u2192      \u2191      \u2193";
    String direction = "<Left>      <RIGHT>         <UP>       <DOWN>";
    String message6 = "Press SPACEBAR to Start";

    // Create a new font instance
    Font font0 = new Font("Courier New", Font.BOLD, 30);
    Font font1 = new Font("Arial", Font.BOLD, 18);
    Font font2 = new Font("Courier New", Font.BOLD, 12);
    Font font3 = new Font("Courier New", Font.BOLD, 12);
    Font font4 = new Font("Courier New", Font.BOLD, 12);
    Font font5 = new Font("Courier New", Font.BOLD, 12);
    Font taptostart = new Font("Arial Narrow", Font.ITALIC, 30);
    Font arrowFont = new Font("Sans Serif", Font.BOLD, 30);
    Font directionFont = new Font("Courrier New", Font.BOLD, 12);

    // FontMetrics metrics = getFontMetrics(font);
    // FontMetrics metrics1 = getFontMetrics(font1);
    // FontMetrics metrics2 = getFontMetrics(font2);
    // FontMetrics metrics3 = getFontMetrics(font3);
    // FontMetrics metrics4 = getFontMetrics(font4);

    Graphics2D g2d = (Graphics2D) g;
    g2d.draw(instrBox);
    g2d.setColor(Color.green);
    // g2d.SetRectangle(instrBox);
    // Set the color of the text to red, and set the font

    g.setColor(Color.GREEN);

    g.setFont(font0);
    g.setFont(font1);
    g.setFont(font2);
    g.setFont(font3);
    g.setFont(font4);

    // Draw the message to the board
    g.setColor(Color.GREEN);
    g.setFont(font0);
    g.drawString(message, 210, 60);
    g.setColor(Color.GRAY);
    g.setFont(font1);
    g.drawString(message1, 246, 120);
    g.setColor(Color.LIGHT_GRAY);
    g.setFont(font2);
    g.drawString(message2, 150, 180);
    g.setFont(font3);
    g.drawString(message3, 150, 198);
    g.setFont(font4);
    g.drawString(message4, 150, 216);
    g.setFont(font5);
    g.drawString(message5, 150, 234);

    g.setFont(arrowFont);
    g.drawString(arrow, 180, 270);
    g.setFont(directionFont);
    g.drawString(direction, 180, 288);

    g.setColor(Color.RED);
    g.setFont(taptostart);
    g.drawString(message6, 150, 360);

  }

  private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
  }

}