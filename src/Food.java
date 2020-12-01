
public class Food {

    private Snake snake = new Snake();
    private int foodX; // Memberikan letak X food
    private int foodY; // Memberikan letak Y food

    // Used to determine random position of food
    private final int RANDOMPOSITION = 40;

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void createFood() {

        // Set our food's x & y position to a random position

        int location = (int) (Math.random() * RANDOMPOSITION);
        foodX = ((location * Board.getDotSize()));

        location = (int) (Math.random() * RANDOMPOSITION);
        foodY = ((location * Board.getDotSize()));

        if ((foodX == snake.getSnakeX(0)) && (foodY == snake.getSnakeY(0))) {
            createFood();
        }
    }
}