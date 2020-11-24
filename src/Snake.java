public class Snake {

    // Stores the joints / body part locations for our snake
    private final int[] x = new int[Boardgame.getAllDots()];
    private final int[] y = new int[Boardgame.getAllDots()];

    private int joints = 0; // Stores # of dots / joints the snake has (starts
                            // with 3)

    public int getSnakeX(int index) {
        return x[index];
    }

    public int getSnakeY(int index) {
        return y[index];
    }

    public void setSnakeX(int i) {
        x[0] = i;
    }

    public void setSnakeY(int i) {
        y[0] = i;
    }

    public int getJoints() {
        return joints;
    }
    
    public void setJoints(int j) {
        joints = j;
    }
}