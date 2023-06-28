package cs3500.pa02;

/**
 * The QuestionData class represents a single question with its corresponding answer and difficulty
 * level.
 */
public class QuestionData {
  public String question;
  public String answer;
  public boolean isEasy;

  /**
   * Constructs a QuestionData object with the specified question, answer, and difficulty level.
   *
   * @param question the question text
   * @param answer   the answer to the question
   * @param isEasy   the difficulty level of the question (true for easy, false for hard)
   */
  public QuestionData(String question, String answer, boolean isEasy) {
    this.question = question;
    this.answer = answer;
    this.isEasy = isEasy;
  }

  /**
   * Sets the difficulty level of the question.
   *
   * @param changeTo the new difficulty level (true for easy, false for hard)
   */
  public void setIsEasy(boolean changeTo) {
    this.isEasy = changeTo;
  }
}
