public class Score {
  private int score;

  public Score() {
    this.score = 0;
  }

  public int getScore() {
    return this.score;
  }

  public void addScore() {
    this.score++;
  }

  public void resetScore() {
    this.score = 0;
  }
}
