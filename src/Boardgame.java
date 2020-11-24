import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Boardgame extends JPanel {
// TODO: Implement a way for the player to win

// Holds height and width of the window
private final static int BOARDWIDTH = 800;
private final static int BOARDHEIGHT =800;

// Used to represent pixel size of food & our snake's joints
private final static int PIXELSIZE = 25;

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end prematurely.
// We don't more because there would be no way to let the player win.
public Boardgame() {

    setBackground(Color.BLACK);
    setFocusable(true);

    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));
}
public void paint (Graphics bg){
    bg.setColor(Color.BLACK);
    bg.fillRect(90,75,800,800);
}    
}

