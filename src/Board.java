import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;

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

  // Bentuk board
  public Board() {
    // setBounds(90,75,800,800);
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

}