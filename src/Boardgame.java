import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Boardgame extends JPanel {
// TODO: Implement a way for the player to win

// Holds height and width of the window
private final static int BOARDWIDTH = 800;
private final static int BOARDHEIGHT = 800;

// Used to represent pixel size of food & our snake's joints
private final static int PIXELSIZE = 25;

private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT) / (PIXELSIZE * PIXELSIZE);

// Check to see if the game is running
private boolean inGame = true;

// Timer used to record tick times
private Timer timer;

private static int speed = 100;

// Instances of our snake & food so we can use their methods
private Snake snake = new Snake();

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end prematurely.
// We don't more because there would be no way to let the player win.
public Boardgame() {
    // setBounds(90,75,800,800);
    setBackground(Color.BLACK);
    setFocusable(true);

    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

    initializeGame();
}

// public void paint (Graphics bg){
//     bg.setColor(Color.BLACK);
//     bg.fillRect(90,75,800,800);
// }

// Used to paint our components to the screen
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
}

// Draw our Snake & Food (Called on repaint()).
void draw(Graphics g) {
    // Only draw if the game is running / the snake is alive
    if (inGame == true) {
        // Draw our snake.
        for (int i = 0; i < snake.getJoints(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.orange);
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
                // Body of snake
            } else {
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i), PIXELSIZE, PIXELSIZE);
            }
        }

        // Sync our graphics together
        Toolkit.getDefaultToolkit().sync();
    }
    else {
    }
}

void initializeGame() {
    snake.setJoints(3); // set our snake's initial size

    // Create our snake's body
    for (int i = 0; i < snake.getJoints(); i++) {
        snake.setSnakeX(BOARDWIDTH / 2);
        snake.setSnakeY(BOARDHEIGHT / 2);
    }
    // Start off our snake moving right
    // set the timer to record our game's speed / make the game move
    // timer = new Timer(speed, this);
    // timer.start();
}

// Used to check collisions with snake's self and board edges
void checkCollisions() {

    // If the snake hits its' own joints..
    for (int i = snake.getJoints(); i > 0; i--) {

        // Snake cant intersect with itself if it's not larger than 5
        if ((i > 5)
                && (snake.getSnakeX(0) == snake.getSnakeX(i)
                && (snake.getSnakeY(0) == snake.getSnakeY(i)))) {
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

private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
}

public static int getAllDots() {
    return TOTALPIXELS;
}

public static int getDotSize() {
    return PIXELSIZE;
}

}