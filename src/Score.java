import java.util.*;

public class Score {
  private int score;
  List<Integer> scoreList = new ArrayList<Integer>();

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

  public void saveScore() {
    scoreList.add(this.score);
  }

  public List<Integer> getLeader() {
    Collections.sort(scoreList, Collections.reverseOrder());
    return scoreList;
  }

}
