import javax.swing.JFrame;
import java.awt.EventQueue;

public class Main extends JFrame {

    Main() {

        add(new Board());
        // setBounds(460,50,1000,980);
        // setBackground(Color.GRAY);
        setTitle("Snake");
        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        // Creates a new thread so our GUI can process itself
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Main();
                frame.setVisible(true);
            }
        });
    }
}
